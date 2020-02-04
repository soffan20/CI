package dd2480.group4.storage;

import dd2480.group4.storage.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class RepositoryTest {

    @Test
    void directoryTest() throws IOException {

        //GIVEN
        String repo = "git@github.com:soffan20/DummyTestRepo.git";

        //WHEN
        var path = Repository.createDirectory(); //create directory
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(path); //create directory stream for the path
        Repository.cloneGit(path,repo); //clone the git repo

        //THEN
        assertTrue(Files.exists(path), "should be true if directory was created successfully");
        assertTrue(dirStream.iterator().hasNext(), "should be true if git repo was cloned successfully");

        //WHEN
        Repository.deleteDirectory(path);

        //THEN
        assertFalse(Files.exists(path), "should be false if directory was deleted successfully");

    }
}
