package dd2480.group4.storage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * A class for handling cloning of repositories.
 */
public class Repository implements RepositoryHandler {

    /**
     * Creates a temporary directory and returns the path to it.
     * @return the path to the directory
     * @throws IOException if it fails to create the repository
     */
    public static Path createDirectory() throws IOException {

        // return temporary directory with prefix "soffan20.ci."
        return Files.createTempDirectory("soffan20.ci.");
    }

    /**
     * Clones a repository to the given path.
     * @param path the location where the repositories is cloned to.
     * @param repo the http-address to the repo to be cloned.
     * @throws IOException if it fails to write to the location.
     * @throws InterruptedException if it fails to sleep.
     */
    public static void cloneGit(Path path, String repo) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Cloning the directory into the created temporary directory
        processBuilder.command("bash", "-c", "git clone " + repo + " " + path.toString())
                .start()
                .waitFor(5, TimeUnit.SECONDS);;

        processBuilder.command("bash", "-c", "git submodule update --init --recursive")
                .start()
                .waitFor(5, TimeUnit.SECONDS);;
    }

    /**
     * Deletes the directory at the given path.
     * @param path the path to the directory which is to be deleted.
     * @throws IOException if if is not allowed to delete teh directory.
     */
    public static void deleteDirectory(Path path) throws IOException {
        // delete the temporary directory
        FileUtils.deleteDirectory(new File(path.toString()));

    }
}
