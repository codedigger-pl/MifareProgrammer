package gui;

import javax.smartcardio.*;
import javax.swing.*;

import terminal.*;


public class MainWindow implements TerminalListener, CardListener {
    private JPanel panel1;
    private JLabel terminalName;
    private JLabel cardName;

    public static MainWindow main() {
        MainWindow mainWindow = new MainWindow();

        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(mainWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        return mainWindow;
    }

    @Override
    public void onTerminalAppears(TerminalThread sender, CardTerminal terminal) {
        terminalName.setText(terminal.getName());
        CardThread cardThread = sender.getCardThread();
        cardThread.addListener(CardAction.onCardConnected, this);
        cardThread.addListener(CardAction.onCardDisconnected, this);
    }

    @Override
    public void onTerminalDisappears() {
        terminalName.setText("Brak czytnika");
    }

    @Override
    public void onCardConnected(CardThread sender, Card card) {
        cardName.setText("Karta obecna");
        ACR122U acr122U = new ACR122U(card);
        System.out.println(acr122U.getFirmwareVersion());
    }

    @Override
    public void onCardDisconnected() { cardName.setText("Brak karty"); }
}
