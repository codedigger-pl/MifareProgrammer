package terminal;

import javax.smartcardio.*;
import java.util.ArrayList;
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
//                    System.out.println("New card detected");
                }
            } catch (CardException e) {
                if (card != null) {
                    card = null;
//                    System.out.println("Card removed");
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
