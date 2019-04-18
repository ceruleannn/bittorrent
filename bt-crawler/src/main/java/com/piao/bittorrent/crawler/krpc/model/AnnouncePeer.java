package com.piao.bittorrent.crawler.krpc.model;

import com.piao.bittorrent.crawler.bencode.model.BInteger;
import com.piao.bittorrent.crawler.bencode.model.BString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
public class AnnouncePeer {

    private AnnouncePeer(){
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class Request implements KRPCObject{
        String id;
        String infohash;
        Integer implied_port;
        Integer port;
        String token;
    }

    public static class Response implements KRPCObject{

    }
}
