package terminal;

import javax.smartcardio.CardTerminal;


public interface TerminalListener {
    void onTerminalAppears(CardTerminal terminal);
    void onTerminalDisappears();
}
