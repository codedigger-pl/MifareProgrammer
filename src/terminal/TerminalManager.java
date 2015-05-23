package terminal;


/**
 * class TerminalManager
 * Class describes terminal management.
 */
public class TerminalManager {

    private TerminalThread terminalThread = null;

    public TerminalThread getTerminalThread() { return terminalThread; }

    /**
     * startTerminalThread
     * Starts main loop, which waits for terminals connection
     */
    public void startTerminalThread() {
        if (terminalThread == null) {
            terminalThread = new TerminalThread();
            terminalThread.start(); }
    }

    /**
     * stopTerminalThread
     * Stops main loop
     */
    public void stopTerminalThread() {
        if (terminalThread != null) {
            terminalThread.interrupt();
            terminalThread = null; }
    }

}
