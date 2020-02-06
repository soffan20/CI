package dd2480.group4.execute;

import dd2480.group4.net.Notification;
import dd2480.group4.net.Http;
import dd2480.group4.net.PushEvent;
import dd2480.group4.storage.Repository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunnerTest {

    public PushEvent getPushEvent() {
        PushEvent pe = new PushEvent();
        pe.repository = new PushEvent.Repository();
        pe.repository.name = "Repo-name";
        pe.repository.url = "URL";
        pe.repository.owner = new PushEvent.Owner();
        pe.repository.owner.name = "Repo-owner";
        pe.hashId = "2";
        pe.pusher = new PushEvent.Pusher();
        pe.pusher.name = "Pusher-name";
        pe.pusher.email = "a@mail.com";

        return pe;
    }


    @Test
    public void EmptyRepository() throws IOException, InterruptedException {
        //GIVEN

        var repository = new Repository() {

            @Override
            public void cloneGit(Path path, String repo) throws IOException, InterruptedException {
            }

        };
        Notification notification = new Notification(new Http());
        PushEvent pe = getPushEvent();
        Path path = repository.createDirectory();
        repository.cloneGit(path, "noGitRepo");
        Runner runner = new Runner(repository);

        //WHEN
        runner.runBuild(pe, notification);

        //THEN
        assertEquals(-1, runner.exitValue, "Exit value should be -1 since execute.sh doesnt exist");
    }

    @Test
    public void PassRepository() throws IOException, InterruptedException {
        //GIVEN
        var repository = new Repository() {

            @Override
            public void cloneGit(Path path, String repo) throws IOException, InterruptedException {
                File execute = new File(Files.write(path.resolve("execute.sh"), "#!/bin/bash \n exit 0\n".getBytes()).toString());
                execute.setExecutable(true);

            }

        };

        Runner runner = new Runner(repository);
        repository.createDirectory();
        Notification notification = new Notification(new Http());
        PushEvent pe = getPushEvent();


        //WHEN
        runner.runBuild(pe, notification);

        //THEN
        assertEquals(0, runner.exitValue, "Exit value should be 0 since execute.sh exists and should run");
    }

    @Test
    public void FailRepository() throws IOException, InterruptedException {
        //GIVEN
        var repository = new Repository() {

            @Override
            public void cloneGit(Path path, String repo) throws IOException, InterruptedException {
                File execute = new File(Files.write(path.resolve("execute.sh"), "#!/bin/bash \n exit 1\n".getBytes()).toString());
                execute.setExecutable(true);

            }
        };

        Runner runner = new Runner(repository);
        repository.createDirectory();
        Notification notification = new Notification(new Http());
        PushEvent pe = getPushEvent();

        //WHEN
        runner.runBuild(pe, notification);

        //THEN
        assertEquals(1, runner.exitValue, "Exit value should be 1");
    }

}

