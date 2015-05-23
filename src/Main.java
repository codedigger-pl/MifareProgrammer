import terminal.TerminalManager;


public class Main {

    public static void main(String[] args) {

        System.out.println("Starting main program");

        TerminalManager manager = new TerminalManager();
        manager.startTerminalThread();

    }
}
