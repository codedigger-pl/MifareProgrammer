package terminal;

enum ACR_122U_ERRORS {
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

    ACR_122U_ERRORS(int code, String description) {
        this.errorCode = code;
        this.errorDescription = description;
    }

    public int getErrorCode() { return errorCode; }
    public String getErrorDescription() { return errorDescription; }
}


public class ACR122U {

}
