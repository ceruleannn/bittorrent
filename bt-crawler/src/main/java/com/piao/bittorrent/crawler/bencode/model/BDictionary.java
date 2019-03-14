package com.piao.bittorrent.crawler.bencode.model;

import com.piao.bittorrent.crawler.bencode.common.BParseException;
import lombok.Data;

import java.util.HashMap;

/**
 *
 */
@Data
public class BDictionary extends BObject{
    private HashMap<String, BObject> value = new HashMap<>();

    public BInteger getBInteger(String key){
        return safeTypeConvert(value.get(key), BInteger.class) ;
    }

    public BList getBList(String key){
        return safeTypeConvert(value.get(key),BList.class) ;
    }

    public BDictionary getBDictionary(String key){
        return safeTypeConvert(value.get(key), BDictionary.class) ;
    }

    public BString getBString(String key){
        return safeTypeConvert(value.get(key), BString.class);
    }

    public BObject put(BString bString, BObject bObject){
        return value.put(bString.getValue(), bObject);
    }

    private <T> T safeTypeConvert(BObject bObject, Class<T> clazz) {

        if (!bObject.getClass().isAssignableFrom(clazz)){
            return null;
        }
        return (T)bObject;
    }
}
