package com.piao.bittorrent.crawler.krpc.model.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    /**
     * 20位16进制nodeId
     */
    private String nodeId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     *
     * @param bytes 26 bit hex byte
     */
    public Node(byte[] bytes){

    }
}
