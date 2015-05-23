package terminal;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import java.util.ArrayList;
import java.util.List;


public class CardThread extends Thread {
    CardTerminal cardTerminal = null;
    Card card = null;

    List<CardListener> onCardConnectedListeners = new ArrayList<>();
    List<CardListener> onCardDisconnectedListeners = new ArrayList<>();

    public CardThread(CardTerminal terminal) {
        cardTerminal = terminal;
    }

    public Card getCard() { return card; }

    @Override
    public void run() {
        while (true) {

            try {
                Card newCard = cardTerminal.connect("T=1");
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

    void onCardConnected() {
        for(CardListener listener: onCardConnectedListeners) {
            listener.onCardConnected(card);
        }
    }

    void onCardDisconnected() {
        for (CardListener listener: onCardDisconnectedListeners) {
            listener.onCardDisconnected();
        }
    }
}