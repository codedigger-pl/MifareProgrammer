import terminal.*;
import gui.MainWindow;


public class Main {

    public static void main(String[] args) {

        System.out.println("Starting main program");

        TerminalManager manager = new TerminalManager();

        MainWindow mainWindow = MainWindow.main();

        manager.startTerminalThread();
        TerminalThread terminalThread = manager.getTerminalThread();

        terminalThread.addListener(TerminalAction.onTerminalConnected, mainWindow);
        terminalThread.addListener(TerminalAction.onTerminalDisconnected, mainWindow);
    }
}
