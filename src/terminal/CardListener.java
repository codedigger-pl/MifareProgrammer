package terminal;

import javax.smartcardio.Card;


/**
 * interface CardListener
 * Interface for CardListener
 */
public interface CardListener {
    void onCardConnected(CardThread sender, Card card);
    void onCardDisconnected();
}
