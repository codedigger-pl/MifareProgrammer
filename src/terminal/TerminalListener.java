package terminal;

import javax.smartcardio.CardTerminal;


public interface TerminalListener {
    void onTerminalAppears(TerminalThread thread, CardTerminal terminal);
    void onTerminalDisappears();
}
