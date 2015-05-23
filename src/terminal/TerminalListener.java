package terminal;

import javax.smartcardio.CardTerminal;


public interface TerminalListener {
    void onTerminalAppears(TerminalThread sender, CardTerminal terminal);
    void onTerminalDisappears();
}
