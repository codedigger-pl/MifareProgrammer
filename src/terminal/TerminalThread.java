package terminal;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.util.ArrayList;
import java.util.List;


public class TerminalThread extends Thread {
    CardTerminal terminal = null;
    CardThread cardThread = null;

    List<TerminalListener> onTerminalConnectedListeners = new ArrayList<>();
    List<TerminalListener> onTerminalDisconnectedListeners = new ArrayList<>();

    @Override
    public void run() {
        TerminalFactory factory = TerminalFactory.getDefault();

        while (true) {

            try {
                List<CardTerminal> readedTerminals = factory.terminals().list();
                CardTerminal readedTerminal = readedTerminals.get(0);
                if (terminal != readedTerminal) {
                    terminal = readedTerminal;
//                    System.out.println("New terminal present: " + terminal);
                    onTerminalAppears();
                    startCardThread();
                }
            } catch (CardException e) {
                stopCardThread();
                if (terminal != null) {
                    onTerminalDisappears();
                    terminal = null;
//                    System.out.println("Terminal removed");
                }
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                stopCardThread();
                break;
            }
        }
    }

    void startCardThread() {
        if (cardThread == null) {
//            System.out.println("Starting card thread");
            cardThread = new CardThread(terminal);
            cardThread.start();
        }
    }

    void stopCardThread() {
        if (cardThread != null) {
//            System.out.println("Stopping card thread");
            cardThread.interrupt();
            cardThread = null;
        }
    }

    public void addListener(TerminalAction action, TerminalListener listener) {
        switch (action) {
            case onTerminalConnected:
                onTerminalConnectedListeners.add(listener);
                break;
            case onTerminalDisconnected:
                onTerminalDisconnectedListeners.add(listener);
                break;
        }
    }

    void onTerminalAppears() {
        for (TerminalListener listener: onTerminalConnectedListeners) {
            listener.onTerminalAppears(terminal);
        }
    }

    void onTerminalDisappears() {
        for (TerminalListener listener: onTerminalDisconnectedListeners) {
            listener.onTerminalDisappears();
        }
    }
}