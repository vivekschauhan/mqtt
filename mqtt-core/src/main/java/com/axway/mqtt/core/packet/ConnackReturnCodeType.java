package com.axway.mqtt.core.packet;

/**
 * Created by vchauhan on 9/14/17.
 */
public enum ConnackReturnCodeType
{
    ACCEPTED(0),
    REFUSED_UNACCEPTABLE_PROTOCOL_VERSION(1),
    REFUSED_UNACCEPTABLE_IDENTIFIER_REJECTED(2),
    REFUSED_UNACCEPTABLE_SERVER_UNAVAILABLE(3),
    REFUSED_UNACCEPTABLE_BAD_USER_PASS(4),
    REFUSED_NOT_AUTHORIZED(5);

    private int returnCode;

    ConnackReturnCodeType(int returnCode)
    {
        this.returnCode = returnCode;
    }

    public int getValue()
    {
        return returnCode;
    }

}
