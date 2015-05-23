package terminal;

import javax.smartcardio.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


enum ACR122U_ERRORS {
    NO_ERRORS                   (0x00,  "No error"),
    TIME_OUT                    (0x01,  "Time Out, the target has not answered"),
    CRC_ERROR                   (0x02,  "A CRC error has been detected by the contactless UART"),
    PARITY_ERROR                (0x03,  "A Parity error has been detected by the contactless UART"),
    MIFARE_COLLISION            (0x04,  "During a MIFARE anti-collision/select operation, an erroneous Bit Count has " +
                                        "been detected"),
    MIFARE_FRAMING_ERROR        (0x05,  "Framing error during MIFARE operation"),
    BIT_COLLISION               (0x06,  "An abnormal bit-collision has been detected during bit wise anti-collision " +
                                        "at 106kbps"),
    BUFFER_SIZE_INSUFFICIENT    (0x07,  "Communication buffer size insufficient"),
    BUFFER_OVERFLOW             (0x08,  "RF Buffer overflow has been detected by the contactless UART (bit " +
                                        "BufferOvflof the register CL_ERROR)"),
    RF_FIELD_TIME_OUT           (0x0A,  "In active communication mode, the RF field has not been switched on in time " +
                                        "by the counterpart (as defined in NFCIP-1 standard)"),
    RF_PROTOCOL_ERROR           (0x0B,  "RF Protocol error (cf. reference [4], description of the CL_ERROR register)"),
    TEMPERATURE_ERROR           (0x0D,  "Temperature error: the internal temperature sensor has detected overheating, " +
                                        "and therefore has automatically switched off the antenna drivers"),
    INTERNAL_BUFFER_OVERFLOW    (0x0e,  "Internal buffer overflow"),
    INVALID_PARAMETER           (0x10,  "Invalid parameter (range, format, ...)"),
    DEP_ERROR_1                 (0x12,  "DEP Protocol: The chip configured in target mode does not support the" +
                                        "command received from the initiator (the command received is not one of the" +
                                        "following: ATR_REQ, WUP_REQ, PSL_REQ, DEP_REQ, DSL_REQ, RLS_REQ)"),
    DEP_ERROR_2                 (0x13,  "DEP Protocol / MIFARE / ISO/IEC 14443-4: The data format does not match to" +
                                        "the specification. Depending on the RF protocol used, it can be:\n" +
                                        "• Bad length of RF received frame,\n" +
                                        "• Incorrect value of PCB or PFB,\n" +
                                        "• Invalid or unexpected RF received frame,\n" +
                                        "• NAD or DID incoherence."),
    AUTHENTICATION_ERROR        (0x14,  "MIFARE: Authentication error"),
    UID_BYTE_WRONG              (0x23,  "ISO/IEC 14443-3: UID Check byte is wrong"),
    DEP_ERROR_3                 (0x25,  "DEP Protocol: Invalid device state, the system is in a state which does not " +
                                        "allow the operation"),
    OPERATION_NOT_ALLOWED       (0x26,  "Operation not allowed in this configuration (host controller interface)"),
    BAD_COMMAND                 (0x27,  "This command is not acceptable due to the current context of the chip " +
                                        "(Initiator vs. Target, unknown target number, Target not in the good " +
                                        "state, ...)"),
    CHIP_RELEASED               (0x29,  "The chip configured as target has been released by its initiator"),
    BAD_CARD_ID                 (0x2A,  "ISO/IEC 14443-3B only: the ID of the card does not match, meaning that the " +
                                        "expected card has been exchanged with another one."),
    CARD_DISAPPEARED            (0x2B,  "ISO/IEC 14443-3B only: the card previously activated has disappeared."),
    DEP_ERROR_4                 (0x2C,  "Mismatch between the NFCID3 initiator and the NFCID3 target in DEP " +
                                        "212/424 kbps passive."),
    OVER_CURRENT                (0x2D,  "An over-current event has been detected"),
    DEP_ERROR_5                 (0x2E,  "NAD missing in DEP frame");

    private final int errorCode;
    private final String errorDescription;

    ACR122U_ERRORS(int code, String description) {
        this.errorCode = code;
        this.errorDescription = description;
    }

    public int getErrorCode() { return errorCode; }
    public String getErrorDescription() { return errorDescription; }
}


class DataFrame {
    private byte CLS;
    private byte INS;
    private byte P1;
    private byte P2;
    private byte LE;
    private List<Byte> DATA;

    public DataFrame() {
    }

    public byte getCLS() { return CLS; }
    public byte getINS() { return INS; }
    public byte getP1() { return P1; }
    public byte getP2() { return P2; }
    public byte getLE() { return LE; }
    public byte[] getData() {
        if (DATA == null) {
            byte[] resp = new byte[1];
            resp[0] = 0;
            return resp;
        }
        byte[] resp = new byte[DATA.size()];
        for (int i=0; i<resp.length; i++) {
            resp[i] = DATA.get(i);
        }
        return resp;
    }

    public void setCLS(byte cls) { CLS = cls; }
    public void setCLS(int cls) { CLS = (byte)cls; }

    public void setINS(byte ins) { INS = ins; }
    public void setINS(int ins) { INS = (byte)ins; }

    public void setP1(byte p1) { P1 = p1; }
    public void setP1(int p1) { P1 = (byte)p1; }

    public void setP2(byte p2) { P2 = p2; }
    public void setP2(int p2) { P2 = (byte)p2; }

    public void setLE(byte le) { LE = le; }
    public void setLE(int le) { LE = (byte)le; }

    public void setData(byte[] data) {
        DATA = new ArrayList<>();
        for (byte d : data) {
            DATA.add(d);
        }
    }
    public void setData(int[] data) {
        DATA = new ArrayList<>();
        for (int d : data) {
            DATA.add((byte)d);
        }
    }
    public void setData(Collection data) {
    }

    public byte[] toBytes() {
        List<Byte> data = new ArrayList<>();
        data.add(CLS);
        data.add(INS);
        data.add(P1);
        data.add(P2);
        if (DATA != null) data.addAll(DATA);

        byte[] resp = new byte[data.size()];
        for (int i=0; i<resp.length; i++) {
            resp[i] = data.get(i);
        }

        return resp;
    }

}


public class ACR122U {
    private Card card;

    public ACR122U(Card card) {
        this.card = card;
    }

    public String getFirmwareVersion() {
        try {
            DataFrame frame = new DataFrame();
            frame.setCLS(0xFF);
            frame.setINS(0x00);
            frame.setP1(0x48);
            frame.setP2(0x00);
            frame.setLE(0x00);
            ResponseAPDU answer = sendDataFrame(frame);
            return new String(answer.getBytes());
        } catch (CardException e) {
            return "";
        }
    }

    private ResponseAPDU sendDataFrame(DataFrame dataFrame) throws CardException {

        CardChannel cardChannel = card.getBasicChannel();
        CommandAPDU command = new CommandAPDU(
                dataFrame.getCLS(),
                dataFrame.getINS(),
                dataFrame.getP1(),
                dataFrame.getP2(),
                dataFrame.getData()
        );
        return cardChannel.transmit(command);
    }
}
