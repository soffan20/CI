package dd2480.group4.execute;

import dd2480.group4.net.Notification.Status;
import dd2480.group4.net.NotificationInterface;
import dd2480.group4.net.PushEvent;
import dd2480.group4.storage.RepositoryHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Responsible for running the individual builds.
 */
public class Runner implements Executor {

    RepositoryHandler repository;
    int exitValue;

    public Runner(RepositoryHandler repository) {
        this.repository = repository;
    }

    /**
     * Runs bash script execute.sh
     *
     * @param path path to temporarily directory
     */
    public static int runExecuteInstructions(Path path) throws IOException, InterruptedException {
        Path executePath = path.resolve("execute.sh");
        ProcessBuilder pb = new ProcessBuilder();
        System.out.println("Searching for file: " + executePath.toString());
        if (Files.exists(executePath)) {
            System.out.println("Found file: " + executePath.toString());
            pb.command("bash", "-c", executePath.toString());
            Process process = pb.start();
            process.waitFor(5, TimeUnit.SECONDS);
            System.out.println("stdout: " + IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8));
            System.out.println("stderr: " + IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8));

            return process.exitValue();
        } else {
            System.out.println("Failed to find file: " + executePath.toString());
            return -1;
        }
    }

    /**
     * Builds the request.
     *
     * @param req the request
     */
    @Override
    public void runBuild(PushEvent req, NotificationInterface notification) throws IOException, InterruptedException {
        System.out.println("Creates temporarily directory for github repository");
        Path path = repository.createDirectory();
        System.out.println("Temporarily directory name: " + path.toString());
        System.out.println("Cloning github repository");
        repository.cloneGit(path, req.repository.url, req.hashId);
        System.out.println("Cloned github repository name: " + req.repository.name);
        System.out.println("Trying to execute execute.sh");
        exitValue = runExecuteInstructions(path);
        System.out.println("Sends notification status to github");
        if (exitValue == 0) {
            notification.setStatus(req, Status.SUCCESS, "The build succeeded");
        } else {
            notification.setStatus(req, Status.FAILURE, "The build failed");
        }

        System.out.println("Deletes temporarily directory ");
        repository.deleteDirectory(path);

    }
}
