package terminal;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.util.ArrayList;
import java.util.List;


/**
 * TerminalThread
 * This class supports terminal connection. When new terminal is connected, sends valid signal to listeners
 * added at addListener with onTerminalConnected value. When card is removed, sends valid signal to
 * listeners added at addListener with onTerminalDisconnected value.
 */
public class TerminalThread extends Thread {
    private CardTerminal terminal = null;
    private CardThread cardThread = null;

    List<TerminalListener> onTerminalConnectedListeners = new ArrayList<>();
    List<TerminalListener> onTerminalDisconnectedListeners = new ArrayList<>();

    public CardThread getCardThread() { return cardThread; }
    public CardTerminal getTerminal() { return terminal; }

    /**
     * run
     * Main thread function. Checks every three seconds if terminal state does not changed.
     */
    @Override
    public void run() {
        TerminalFactory factory = TerminalFactory.getDefault();

        while (true) {

            try {
                List<CardTerminal> readTerminals = factory.terminals().list();
                CardTerminal readTerminal = readTerminals.get(0);
                if (terminal != readTerminal) {
                    terminal = readTerminal;
                    startCardThread();
                    onTerminalConnected(); }
            } catch (CardException e) {
                stopCardThread();
                if (terminal != null) {
                    onTerminalDisconnected();
                    terminal = null; }
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                stopCardThread();
                break;
            }
        }
    }

    /**
     * startCardThread
     * Starting card thread (waiting for card)
     */
    private void startCardThread() {
        if (cardThread == null) {
            cardThread = new CardThread(terminal);
            cardThread.start(); }
    }

    /**
     * stopCardThread
     * Stopping card thread
     */
    private void stopCardThread() {
        if (cardThread != null) {
            cardThread.interrupt();
            cardThread = null; }
    }

    /**
     * addListener
     * Add listeners to correct list according to selected action
     * @param action    what action should listeners listen
     * @param listener  correct listener
     */
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

    /**
     * onTerminalConnected
     * Send new terminal connection signal to all listeners
     */
    private void onTerminalConnected() {
        for (TerminalListener listener: onTerminalConnectedListeners) {
            listener.onTerminalAppears(this, terminal); }
    }

    /**
     * onTerminalDisconected
     * Send terminal disconnected signal to all listeners
     */
    private void onTerminalDisconnected() {
        for (TerminalListener listener: onTerminalDisconnectedListeners) {
            listener.onTerminalDisappears(); }
    }
}