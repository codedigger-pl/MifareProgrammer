package terminal;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import java.util.ArrayList;
import java.util.List;


/**
 * class CardThread
 * This class supports card connection. When new card is connected, sends valid signal to listeners
 * added at addListener with onCardConnected value. When card is removed, sends valid signal to
 * listeners added at addListener with onCardDisconnected value.
 */
public class CardThread extends Thread {
    CardTerminal cardTerminal = null;
    Card card = null;

    List<CardListener> onCardConnectedListeners = new ArrayList<>();
    List<CardListener> onCardDisconnectedListeners = new ArrayList<>();

    /**
     * Class constructor
     *
     * @param terminal  in which terminal we are expecting card
     */
    public CardThread(CardTerminal terminal) {
        cardTerminal = terminal;
    }

    public Card getCard() { return card; }

    /**
     * run
     * Main thread function. Checks every one second if card state does not changed.
     */
    @Override
    public void run() {
        while (true) {

            try {
                Card newCard = cardTerminal.connect("*");
                if (card != newCard) {
                    card = newCard;
                    onCardConnected();
                }
            } catch (CardException e) {
                if (card != null) {
                    card = null;
                    onCardDisconnected();
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * addListener
     * Add listeners to correct list according to selected action
     * @param action    what action should listeners listen
     * @param listener  correct listener
     */
    public void addListener(CardAction action, CardListener listener) {
        switch (action) {
            case onCardConnected:
                onCardConnectedListeners.add(listener);
                break;
            case onCardDisconnected:
                onCardDisconnectedListeners.add(listener);
                break;
        }
    }

    /**
     * onCardConnected
     * Send new card connection signal to all listeners
     */
    private void onCardConnected() {
        for(CardListener listener: onCardConnectedListeners) {
            listener.onCardConnected(this, card); }
    }

    /**
     * onCardDisconnected
     * Send card disconnected signal to all listeners
     */
    private void onCardDisconnected() {
        for (CardListener listener: onCardDisconnectedListeners) {
            listener.onCardDisconnected(); }
    }
}