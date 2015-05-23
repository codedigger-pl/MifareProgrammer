package gui;

import javax.smartcardio.CardTerminal;
import javax.swing.*;
import terminal.TerminalListener;


public class MainWindow implements TerminalListener {
    private JPanel panel1;
    private JLabel terminalName;

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
    public void onTerminalAppears(CardTerminal terminal) {
        terminalName.setText(terminal.getName());
    }

    @Override
    public void onTerminalDisappears() {
        terminalName.setText("Brak czytnika");
    }

}
