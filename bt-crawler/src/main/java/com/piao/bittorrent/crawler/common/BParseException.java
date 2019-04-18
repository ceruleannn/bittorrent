package com.piao.bittorrent.crawler.common;

import lombok.NoArgsConstructor;

/**
 *
 */
@NoArgsConstructor
public class BParseException extends Exception{

    public BParseException(String msg){
        super(msg);
    }

    public BParseException(String msg, Exception e){
        super(msg, e);
    }
}

