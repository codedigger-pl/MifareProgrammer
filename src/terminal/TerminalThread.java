package terminal;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.util.ArrayList;
import java.util.List;


public class TerminalThread extends Thread {
    private CardTerminal terminal = null;
    private CardThread cardThread = null;

    List<TerminalListener> onTerminalConnectedListeners = new ArrayList<>();
    List<TerminalListener> onTerminalDisconnectedListeners = new ArrayList<>();

    public CardThread getCardThread() { return cardThread; }
    public CardTerminal getTerminal() { return terminal; }

    @Override
    public void run() {
        TerminalFactory factory = TerminalFactory.getDefault();

        while (true) {

            try {
                List<CardTerminal> readedTerminals = factory.terminals().list();
                CardTerminal readedTerminal = readedTerminals.get(0);
                if (terminal != readedTerminal) {
                    terminal = readedTerminal;
                    startCardThread();
                    onTerminalAppears();
                }
            } catch (CardException e) {
                stopCardThread();
                if (terminal != null) {
                    onTerminalDisappears();
                    terminal = null;
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

    private void startCardThread() {
        if (cardThread == null) {
            cardThread = new CardThread(terminal);
            cardThread.start();
        }
    }

    private void stopCardThread() {
        if (cardThread != null) {
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

    private void onTerminalAppears() {
        for (TerminalListener listener: onTerminalConnectedListeners) {
            listener.onTerminalAppears(this, terminal);
        }
    }

    private void onTerminalDisappears() {
        for (TerminalListener listener: onTerminalDisconnectedListeners) {
            listener.onTerminalDisappears();
        }
    }
}