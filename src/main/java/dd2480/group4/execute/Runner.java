package dd2480.group4.execute;

import dd2480.group4.storage.Repository;
import dd2480.group4.net.Notification;
import dd2480.group4.net.Notification.Status;

import java.io.IOException;
import java.nio.file.Path;
import dd2480.group4.net.PushEvent;

/**
 * Responsible for running the individual builds.
 */
public class Runner implements Executor {
    /**
     * Builds the request.
     * @param req the request
     */
    @Override
    public void runBuild(PushEvent req) throws IOException, InterruptedException {
        Notification notification = new Notification();
        System.out.println("Creates temporarily directory for github repository");
        Path path = Repository.createDirectory();
        System.out.println("Temporarily directory name: " + path.toString());
        Execute execute = new Execute(path);
        System.out.println("Cloning github repository");
        Repository.cloneGit(path,req.repository.url);
        System.out.println("Cloned github repository name: " + req.repository.name);
        System.out.println("Trying to execute execute.sh");
        execute.runExecuteInstructions();
        System.out.println("Exit value: " + execute.exitValue);
        System.out.println("Stdout: " + execute.stdout);
        System.out.println("Stderr: " + execute.stderr);


        System.out.println("Sends notification status to github");
        if(execute.exitValue == 0){
            notification.setStatus(req, Status.SUCCESS, "The build succeeded");
        }else{
            notification.setStatus(req, Status.FAILURE, "The build failed");
        }

        System.out.println("Deletes temporarily directory ");
        Repository.deleteDirectory(path);

    }
}
