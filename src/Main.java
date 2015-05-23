import terminal.TerminalManager;
import gui.MainWindow;

import terminal.TerminalAction;
import terminal.TerminalListener;

import javax.smartcardio.CardTerminal;


class MyTerminalListener implements TerminalListener {

    @Override
    public void onTerminalAppears(CardTerminal terminal) {
        System.out.println("Signal: terminal appears");
    }

    @Override
    public void onTerminalDisappears() {
        System.out.println("Signal: terminal disappears");
    }
}


public class Main {

    public static void main(String[] args) {

        System.out.println("Starting main program");

        TerminalManager manager = new TerminalManager();

        MainWindow mainWindow = MainWindow.main();

        manager.startTerminalThread();

        manager.getTerminalThread().addListener(TerminalAction.onTerminalConnected, mainWindow);
        manager.getTerminalThread().addListener(TerminalAction.onTerminalConnected, mainWindow);



    }
}
