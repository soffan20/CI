package dd2480.group4.execute;

import dd2480.group4.net.pushEvent;

/**
 * Interface for running a build.
 */
public interface Executor {
    /**
     * Runs the build
     * @param req the request to be built.
     */
    void runBuild(pushEvent req);
}
