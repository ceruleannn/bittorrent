package com.piao.bittorrent.crawler.bencode;

import com.piao.bittorrent.crawler.Utils.CodeUtils;
import com.piao.bittorrent.crawler.common.BParseException;
import com.piao.bittorrent.crawler.bencode.model.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;

public class Bencode {

    private static char INTEGER_TOKEN = 'i';
    private static char LIST_TOKEN = 'l';
    private static char DICTIONARY_TOKEN = 'd';
    private static char COLON = ':';
    private static char END_TOKEN = 'e';

    public static byte[] encode(BObject bObject) {
        return encodeAny(bObject).getBytes(StandardCharsets.UTF_8);
    }

    private static String encodeAny(BObject bObject){
        if (bObject instanceof BString){
            return encodeBString((BString) bObject);
        }
        else if (bObject instanceof BInteger){
            return encodeBInteger((BInteger) bObject);
        }
        else if (bObject instanceof BList){
            return encodeBList((BList) bObject);
        }
        else if (bObject instanceof BDictionary){
            return encodeBDictionary((BDictionary) bObject);
        }else {
            throw new Error("invalid BDictionary");
        }
    }

    private static String encodeBString(BString bString){
        return new StringBuilder()
                .append(bString.getString().length())
                .append(COLON)
                .append(bString.getString())
                .toString();
    }
    private static String encodeBInteger(BInteger bInteger){
        return new StringBuilder()
                .append(INTEGER_TOKEN)
                .append(bInteger.getInteger())
                .append(END_TOKEN)
                .toString();
    }
    private static String encodeBList(BList bList){
        StringBuilder sb = new StringBuilder();
        sb.append(LIST_TOKEN);
        bList.getList().stream().map(Bencode::encodeAny).forEach(sb::append);
        sb.append(END_TOKEN);
        return sb.toString();
    }

    private static String encodeBDictionary(BDictionary dictionary){
        Function<Map.Entry<BString, BObject>, String> function = entry -> {
            return encodeAny(entry.getKey()) + encodeAny(entry.getValue());
        };

        StringBuilder sb = new StringBuilder();
        sb.append(DICTIONARY_TOKEN);
        dictionary.getDictionary().entrySet().stream().map(function).forEach(sb::append);
        sb.append(END_TOKEN);
        return sb.toString();
    }

    public static BDictionary decode(byte[] bytes) throws BParseException{

        RawChars rawChars = new RawChars(bytes);
        try {
            return (BDictionary) decodeAny(rawChars);
        } catch (Exception e) {
            throw new BParseException("error bencode format");
        }
    }

    private static BObject decodeAny(RawChars rawChars) throws BParseException{

        BObject object = null;
        if (Character.isDigit(rawChars.chars[rawChars.pos])){
            object = decodeBString(rawChars);
        }
        else if (rawChars.chars[rawChars.pos] == INTEGER_TOKEN){
            rawChars.pos++;
            object = decodeBInteger(rawChars);
        }

        else if (rawChars.chars[rawChars.pos] == LIST_TOKEN){
            rawChars.pos++;
            object = decodeBList(rawChars);
        }
        else if (rawChars.chars[rawChars.pos] == DICTIONARY_TOKEN){
            rawChars.pos++;
            object = decodeBDictionary(rawChars);
        }
        else {
            throw new BParseException();
        }

        return object;
    }

    private static BString decodeBString(RawChars rawChars){

        char[] numChars = findUtilToken(rawChars,COLON);
        int num = Integer.parseInt(String.valueOf(numChars));

        char[] contents = new char[num];
        System.arraycopy(rawChars.chars, rawChars.pos, contents, 0, num);

        rawChars.pos += num;
        return new BString(String.valueOf(contents));
    }

    private static BInteger decodeBInteger(RawChars rawChars){
        char[] num = findUtilToken(rawChars, END_TOKEN);
        return new BInteger(Integer.parseInt(String.valueOf(num)));
    }

    private static BList decodeBList(RawChars rawChars) throws BParseException{
        BList bList = new BList();

        while (rawChars.chars[rawChars.pos] != END_TOKEN){
            BObject bObject = decodeAny(rawChars);
            bList.getList().add(bObject);
        }
        rawChars.pos++;
        return bList;
    }

    private static BDictionary decodeBDictionary(RawChars rawChars) throws BParseException{
        BDictionary bDictionary = new BDictionary();

        BString key = null;
        while (rawChars.chars[rawChars.pos] != END_TOKEN){
            BObject bObject = decodeAny(rawChars);
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

    /**
     * rawChars pos will move to token pos + 1
     * @param rawChars
     * @param token
     * @return
     */
    private static char[] findUtilToken(RawChars rawChars, char token){
        int tokenPos = rawChars.pos;
        while (rawChars.chars[tokenPos] != token){
            tokenPos++;
        }

        char[] content = new char[tokenPos - rawChars.pos];
        System.arraycopy(rawChars.chars, rawChars.pos, content, 0, tokenPos - rawChars.pos);
        rawChars.pos = ++tokenPos;

        return content;
    }
}

class RawChars{

    char[] chars;
    int pos = 0;

    RawChars(byte[] bytes){
        chars = CodeUtils.bytes2chars(bytes);
    }

    RawChars(String string){
        chars = string.toCharArray();
    }
}