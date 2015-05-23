package terminal;

import javax.smartcardio.Card;


public interface CardListener {
    void onCardConnected(CardThread sender, Card card);
    void onCardDisconnected();
}
