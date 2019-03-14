package com.piao.bittorrent.crawler.bencode;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.piao.bittorrent.crawler.Utils.CodeUtils;
import com.piao.bittorrent.crawler.bencode.common.BParseException;
import com.piao.bittorrent.crawler.bencode.model.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Stack;

/**
 *  d1:ad2:id20:abcdefghij0123456789e1:q4:ping1:t3:tx11:y1:qe
 *  {"t":"tx1", "y":"q", "q":"ping", "a":{"id":"abcdefghij0123456789"}}
 */
public class BParser {


    public static char INTEGER_TOKEN = 'i';
    public static char LIST_TOKEN = 'l';
    public static char DICTIONARY_TOKEN = 'd';
    public static char COLON = ':';
    public static char END_TOKEN = 'e';

    public static void main(String[] args) {
        String s = "d1:ad2:id20:abcdefghij0123456789e1:q4:ping1:t3:tx11:y1:qe";
        try {
            BDictionary bDictionary = decode(s.getBytes());
            bDictionary.getValue().forEach((k, v) -> System.out.println( k + "-" + v));
        } catch (BParseException e) {
            e.printStackTrace();
        }
    }

    public static BDictionary decode(byte[] bytes) throws BParseException{

        RawChars rawChars = new RawChars(bytes);
        try {
            return (BDictionary) parseAny(rawChars);
        } catch (Exception e) {
            throw new BParseException("error bencode format");
        }
    }

    private static BObject parseAny(RawChars rawChars) throws BParseException{
        System.out.println("any +" + rawChars.chars[rawChars.pos]);

        BObject object = null;
        if (Character.isDigit(rawChars.chars[rawChars.pos])){
            object = parseBString(rawChars);
        }
        else if (rawChars.chars[rawChars.pos] == INTEGER_TOKEN){
            rawChars.pos++;
            object = parseBInteger(rawChars);
        }

        else if (rawChars.chars[rawChars.pos] == LIST_TOKEN){
            rawChars.pos++;
            object = parseBList(rawChars);
        }
        else if (rawChars.chars[rawChars.pos] == DICTIONARY_TOKEN){
            rawChars.pos++;
            object = parseBDictionary(rawChars);
        }
        else {
            throw new BParseException();
        }

        return object;
    }

    private static BString parseBString(RawChars rawChars){
        int j = rawChars.pos;
        while (rawChars.chars[j] != COLON){
            j++;
        }
        char[] numChars = new char[j - rawChars.pos];
        System.arraycopy(rawChars.chars, rawChars.pos, numChars, 0, j - rawChars.pos);
        int num = Integer.parseInt(String.valueOf(numChars));

        char[] contents = new char[num];
        System.arraycopy(rawChars.chars, j + 1, contents, 0, num);

        rawChars.pos += (j - rawChars.pos + 1 + num);
        return new BString(String.valueOf(contents));
    }

    private static BInteger parseBInteger(RawChars rawChars){
        int numPos = rawChars.pos;
        while (rawChars.chars[rawChars.pos] != END_TOKEN){
            numPos++;
        }

        char[] num = new char[numPos - rawChars.pos];
        System.arraycopy(rawChars.chars, rawChars.pos, num, 0, numPos - rawChars.pos);

        rawChars.pos = numPos;
        return new BInteger(Integer.parseInt(String.valueOf(num)));
    }

    private static BList parseBList(RawChars rawChars) throws BParseException{
        BList bList = new BList();

        while (rawChars.chars[rawChars.pos] != END_TOKEN){
            BObject bObject = parseAny(rawChars);
            bList.add(bObject);
        }
        rawChars.pos++;
        return bList;
    }

    private static BDictionary parseBDictionary(RawChars rawChars) throws BParseException{
        BDictionary bDictionary = new BDictionary();

        BString key = null;
        while (rawChars.chars[rawChars.pos] != END_TOKEN){
            BObject bObject = parseAny(rawChars);
            if (key == null){
                if (bObject instanceof BString){
                    key = (BString)bObject;
                }else {
                    throw new BParseException();
                }
            }else {
                bDictionary.put(key, bObject);
                key = null;
            }
        }

        rawChars.pos++;
        return bDictionary;
    }
}

class RawChars{

    char[] chars;
    int pos = 0;

    RawChars(byte[] bytes){
        chars = CodeUtils.getChars(bytes);
    }

    RawChars(String string){
        chars = string.toCharArray();
    }
}