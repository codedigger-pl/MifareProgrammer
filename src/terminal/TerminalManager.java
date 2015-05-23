package terminal;

public class TerminalManager {

    private TerminalThread terminalThread = null;

    public TerminalThread getTerminalThread() { return terminalThread; }

    public void startTerminalThread() {
        if (terminalThread == null) {
//            System.out.println("Starting terminal thread");
            terminalThread = new TerminalThread();
            terminalThread.start();
        }
    }

    public void stopTerminalThread() {
        if (terminalThread != null) {
//            System.out.println("Stopping terminal thread");
            terminalThread.interrupt();
            terminalThread = null;
        }
    }

}
