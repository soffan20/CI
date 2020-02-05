package dd2480.group4.storage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class Repository implements RepositoryHandler{

    public static Path createDirectory() throws IOException {

        // return temporary directory with prefix "soffan20.ci."
        return Files.createTempDirectory("soffan20.ci.");
    }

    public static void cloneGit(Path path, String repo) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Cloning the directory into the created temporary directory
        processBuilder.command("bash", "-c", "git clone "+repo+" "+path.toString());

        processBuilder.start();

        processBuilder.command("bash", "-c", "git submodule update --init --recursive");

        processBuilder.start();

    }

    public static void deleteDirectory(Path path) throws IOException {
        // delete the temporary directory
        FileUtils.deleteDirectory(new File(path.toString()));

    }
}
