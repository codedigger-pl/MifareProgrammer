package terminal;

import javax.smartcardio.*;
import java.util.List;


class CardThread extends Thread {
    CardTerminal cardTerminal = null;
    Card card = null;

    public CardThread(CardTerminal terminal) {
        cardTerminal = terminal;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Card newCard = cardTerminal.connect("T=1");
                if (card != newCard) {
                    card = newCard;
                    System.out.println("New card detected");
                }
            } catch (CardException e) {
                if (card != null) {
                    card = null;
                    System.out.println("Card removed");
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}


class TerminalThread extends Thread {
    CardTerminal terminal;
    CardThread cardThread = null;

    TerminalThread() {
    }

    @Override
    public void run() {
        TerminalFactory factory = TerminalFactory.getDefault();

        while (true) {

            try {
                List<CardTerminal> readedTerminals = factory.terminals().list();
                CardTerminal readedTerminal = readedTerminals.get(0);
                if (terminal != readedTerminal) {
                    terminal = readedTerminal;
                    System.out.println("New terminal present: " + terminal);
                    startCardThread();
                }
            } catch (CardException e) {
                stopCardThread();
                if (terminal != null) {
                    terminal = null;
                    System.out.println("Terminal removed");
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
            System.out.println("Starting card thread");
            cardThread = new CardThread(terminal);
            cardThread.start();
        }
    }

    void stopCardThread() {
        if (cardThread != null) {
            System.out.println("Stopping card thread");
            cardThread.interrupt();
            cardThread = null;
        }
    }
}


public class TerminalManager {

    TerminalThread terminalThread = null;
    

    public void startTerminalThread() {
        if (terminalThread == null) {
            System.out.println("Starting terminal thread");
            terminalThread = new TerminalThread();
            terminalThread.start();
        }
    }

    public void stopTerminalThread() {
        if (terminalThread != null) {
            System.out.println("Stopping terminal thread");
            terminalThread.interrupt();
            terminalThread = null;
        }
    }

}
