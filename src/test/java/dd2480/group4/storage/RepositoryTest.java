package dd2480.group4.storage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RepositoryTest {

    @Test
    void directoryTest() throws IOException, InterruptedException {

        //GIVEN
        String repo = "https://github.com/soffan20/DummyTestRepo.git";
        Repository repository = new Repository();

        //WHEN
        var path = repository.createDirectory(); //create directory
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(path); //create directory stream for the path
        repository.cloneGit(path, repo); //clone the git repo

        //THEN
        assertTrue(Files.exists(path), "should be true if directory was created successfully");
        assertTrue(dirStream.iterator().hasNext(), "should be true if git repo was cloned successfully");
        assertTrue(Files.exists(path.resolve("execute.sh")), "should be true if git repo was cloned successfully");

        //WHEN
        repository.deleteDirectory(path);

        //THEN
        assertFalse(Files.exists(path), "should be false if directory was deleted successfully");

    }

}
