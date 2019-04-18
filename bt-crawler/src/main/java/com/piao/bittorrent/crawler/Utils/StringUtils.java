package com.piao.bittorrent.crawler.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StringUtils {

    /**
     * split by length , the last element's length may less than the given length
     * @param src
     * @param splitLength
     */
    public static List<String> splitByLength(String src, int splitLength){
        List<String> results = new ArrayList<>();

        for (int b = 0, e = 0; b < src.length() ; b = e){
            e = (b + splitLength) < src.length() ? b + splitLength : src.length();
            results.add(src.substring(b, e));
        }
        return results;
    }

}
