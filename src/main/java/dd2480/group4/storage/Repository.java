package dd2480.group4.storage;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class Repository implements RepositoryHandler {

    public static Path createDirectory() throws IOException {

        // return temporary directory with prefix "soffan20.ci."
        return Files.createTempDirectory("soffan20.ci.");
    }

    public static void cloneGit(Path path, String repo) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Cloning the directory into the created temporary directory
        Process p = processBuilder.command("bash", "-c", "git clone --progress " + repo + " " + path.toString()).start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        StringBuilder builder = new StringBuilder();
        String line;

        while ( (line = reader.readLine()) != null) {
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

    public static void deleteDirectory(Path path) throws IOException {
        // delete the temporary directory
        FileUtils.deleteDirectory(new File(path.toString()));

    }
}
