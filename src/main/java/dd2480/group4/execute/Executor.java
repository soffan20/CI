package dd2480.group4.execute;

import dd2480.group4.net.BuildRequest;

import java.io.IOException;

public interface Executor {

    void runBuild(BuildRequest req) throws IOException, InterruptedException;
}
