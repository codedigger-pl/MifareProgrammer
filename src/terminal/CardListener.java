package terminal;

import javax.smartcardio.Card;


public interface CardListener {
    void onCardConnected(Card card);
    void onCardDisconnected();
}
