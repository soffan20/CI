package dd2480.group4.execute;

import dd2480.group4.net.BuildRequest;
import dd2480.group4.storage.Repository;
import dd2480.group4.net.Notification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Responsible for running the individual builds.
 */
public class Runner implements Executor {
    @Override
    public void runBuild(BuildRequest req) throws IOException, InterruptedException {
        Notification notification = new Notification();
        System.out.println("Skapar temp directory");
        Path path = Repository.createDirectory();
        System.out.println("temp directory ar: " + path.toString());
        System.out.println("skapar ett execute obj");
        Execute execute = new Execute(path);
        System.out.println("klonar git repo");
        Repository.cloneGit(path,req.repository.url);
        System.out.println("klonade repot:");

        File folder = new File(path.toString());
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        System.out.println("kor funktionen runExecuteInstructions, som forsker hitta execute.sh samt kora filen");
        execute.runExecuteInstructions();
        System.out.println("resultatet fran runExecuteInstructions ");
        System.out.println("exit value: " + execute.exitValue);
        System.out.println("stdout: " + execute.stdout);
        System.out.println("stderr: " + execute.stderr);

        System.out.println("forsoker skicka notification till git");
        if(execute.exitValue == 0){
            notification.setStatus(req, Notification.Status.success, "The build succeeded");
        }else{
            notification.setStatus(req, Notification.Status.failure, "The build fail");
        }

        System.out.println("raderar temp directory");
        Repository.deleteDirectory(path);

    }
}
