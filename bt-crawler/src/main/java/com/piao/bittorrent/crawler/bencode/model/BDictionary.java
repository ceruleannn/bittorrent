package com.piao.bittorrent.crawler.bencode.model;

import com.piao.bittorrent.crawler.common.BParseException;
import lombok.Data;

import java.util.HashMap;

/**
 *
 */
@Data
public class BDictionary extends BObject{
    private HashMap<BString, BObject> dictionary = new HashMap<>();

    public BInteger getBInteger(String key) throws BParseException{
        return safeTypeConvert(dictionary.get(key), BInteger.class) ;
    }

    public BList getBList(String key) throws BParseException{
        return safeTypeConvert(dictionary.get(key),BList.class) ;
    }

    public BDictionary getBDictionary(String key) throws BParseException{
        return safeTypeConvert(dictionary.get(key), BDictionary.class) ;
    }

    public BString getBString(String key) throws BParseException {
        return safeTypeConvert(dictionary.get(key), BString.class);
    }

    public BObject put(BString bString, BObject bObject){
        return dictionary.put(bString, bObject);
    }

    private <T> T safeTypeConvert(BObject bObject, Class<T> clazz) throws BParseException {

        if (!bObject.getClass().isAssignableFrom(clazz)){
            throw new BParseException("can not find expect type:" + clazz);
        }
        return (T)bObject;
    }
}
