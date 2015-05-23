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
//        CardChannel cardChannel = card.getBasicChannel();
//
//        try {
//            ResponseAPDU answer = null;
////            answer = cardChannel.transmit(new CommandAPDU(0x00, 0xA4, 0x04, 0x00, aid));
////            System.out.println("answer: " + answer.toString());
//
//            // Send test command
//            int cla = 0xff;
//            int ins = 0xff;
//            int p1 = 0x48;
//            int p2 = 0b00000000; //led state control
//            int lc = 0x04;
//            byte[] data = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
//            answer = cardChannel.transmit(new CommandAPDU(cla, ins, p1, p2, data));
//            System.out.println("answer: " + answer.toString());
//            byte r[] = answer.getData();
//            for (int i=0; i<r.length; i++)
//                System.out.print((char)r[i]);
//            System.out.println();
//
////             Disconnect the card
////            card.disconnect(false);
//        } catch (CardException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onCardDisconnected() { cardName.setText("Brak karty"); }
}
