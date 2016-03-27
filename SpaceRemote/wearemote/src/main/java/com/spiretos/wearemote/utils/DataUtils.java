package com.spiretos.wearemote.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by spiretos on 27/3/2016.
 */
public class DataUtils
{

    public static byte[] getByteArrayFrom(float value)
    {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
    }
    public static float getFloatFrom(byte[] bytes)
    {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static byte[] getByteArray(String text)
    {
        try
        {
            return text.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return new byte[0];
        }
    }
    public static String getStringFrom(byte[] bytes)
    {
        try
        {
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public static float getFloatFrom(String text)
    {
        try
        {
            return Float.parseFloat(text);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0f;
        }
    }

}
