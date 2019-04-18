package com.piao.bittorrent.crawler.krpc.model;

import com.piao.bittorrent.crawler.bencode.model.BList;
import com.piao.bittorrent.crawler.bencode.model.BString;
import com.piao.bittorrent.crawler.krpc.model.basic.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
public class FindNode{

    private FindNode(){
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request implements KRPCObject{
        private String id;
        private String target;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response implements KRPCObject{
        private String id;
        private List<Node> nodes;
    }
}
