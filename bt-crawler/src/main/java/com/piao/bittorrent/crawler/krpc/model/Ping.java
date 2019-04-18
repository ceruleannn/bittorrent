package com.piao.bittorrent.crawler.krpc.model;

import com.piao.bittorrent.crawler.bencode.model.BList;
import com.piao.bittorrent.crawler.bencode.model.BString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
public class Ping {

    private Ping(){
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request implements KRPCObject{
        private String id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response implements KRPCObject{
        private String id;
    }
}
