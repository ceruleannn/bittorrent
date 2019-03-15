package com.piao.bittorrent.crawler.Utils;

import com.piao.bittorrent.crawler.bencode.config.AppConfig;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public class CodeUtils {

    public static char[] bytes2chars(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = StandardCharsets.UTF_8.decode(bb);
        return cb.array();
    }

    public static byte[] chars2bytes(char[] chars){
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = StandardCharsets.UTF_8.encode(cb);
        return bb.array();
    }
}
