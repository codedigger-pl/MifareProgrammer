package terminal;

public class TerminalManager {

    private TerminalThread terminalThread = null;

    public TerminalThread getTerminalThread() { return terminalThread; }

    public void startTerminalThread() {
        if (terminalThread == null) {
            terminalThread = new TerminalThread();
            terminalThread.start(); }
    }

    public void stopTerminalThread() {
        if (terminalThread != null) {
            terminalThread.interrupt();
            terminalThread = null; }
    }

}
