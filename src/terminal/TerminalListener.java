package terminal;

import javax.smartcardio.CardTerminal;

/**
 * interface TerminalListener
 * Interface for TerminalListener
 */
public interface TerminalListener {
    void onTerminalAppears(TerminalThread sender, CardTerminal terminal);
    void onTerminalDisappears();
}
