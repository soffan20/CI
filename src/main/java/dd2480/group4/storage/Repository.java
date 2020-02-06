package dd2480.group4.storage;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


/**
 * A class for handling cloning of repositories.
 */
public class Repository implements RepositoryHandler {


    /**
     * Creates a temporary directory and returns the path to it.
     *
     * @return the path to the directory
     * @throws IOException if it fails to create the repository
     */

    public Path createDirectory() throws IOException {

        // return temporary directory with prefix "soffan20.ci."
        return Files.createTempDirectory("soffan20.ci.");
    }


    /**
     * Clones a repository to the given path.
     *
     * @param path the location where the repositories is cloned to.
     * @param repo the http-address to the repo to be cloned.
     * @throws IOException          if it fails to write to the location.
     * @throws InterruptedException if it fails to sleep.
     */

    public void cloneGit(Path path, String repo) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Cloning the directory into the created temporary directory
        Process p = processBuilder.command("bash", "-c", "git clone --progress " + repo + " " + path.toString()).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        builder.append(System.getProperty("line.separator"));

        p.waitFor(5, TimeUnit.SECONDS);

        p = processBuilder.command("bash", "-c", "git submodule update --init --recursive").directory(path.toFile()).start();

        reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        while ( (line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        p.waitFor(5, TimeUnit.SECONDS);

        String result = builder.toString();

        System.out.println(result);

    }

    /**
     * Deletes the directory at the given path.
     *
     * @param path the path to the directory which is to be deleted.
     * @throws IOException if if is not allowed to delete teh directory.
     */
    public void deleteDirectory(Path path) throws IOException {
        // delete the temporary directory
        FileUtils.deleteDirectory(new File(path.toString()));

    }
}
