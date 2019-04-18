package com.piao.bittorrent.crawler.krpc.core;

import com.piao.bittorrent.crawler.Utils.StringUtils;
import com.piao.bittorrent.crawler.bencode.Bencode;
import com.piao.bittorrent.crawler.bencode.model.BDictionary;
import com.piao.bittorrent.crawler.bencode.model.BInteger;
import com.piao.bittorrent.crawler.bencode.model.BList;
import com.piao.bittorrent.crawler.bencode.model.BString;
import com.piao.bittorrent.crawler.common.BParseException;
import com.piao.bittorrent.crawler.krpc.model.*;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public class KRPCProcessor {

    public KRPCObject process(byte[] bytes) throws BParseException {

        BDictionary raw = Bencode.decode(bytes);
        BString t = raw.getBString("t");

        BString y = raw.getBString("y");

        if (Objects.equals(y.getString(), "q")){

            BString q = raw.getBString("q");
            BDictionary a = raw.getBDictionary("a");


            switch (q.getString()){
                case "ping":
                    BString pingId = a.getBString("id");
                    return new Ping.Request(pingId.getString());
                case "find_node":
                    BString findNodeId = a.getBString("id");
                    BString target = a.getBString("target");
                    return new FindNode.Request(findNodeId.getString(), target.getString());
                case "get_peers":
                    BString getPeersId = a.getBString("id");
                    BString getPeerInfohash = a.getBString("infohash");
                    return new GetPeers.Request(getPeersId.getString(), getPeerInfohash.getString());
                case "announce_peer":
                    BString id = a.getBString("id");
                    BInteger implied_port = a.getBInteger("implied_port");
                    BString infohash = a.getBString("infohash");
                    BInteger port = a.getBInteger("port");
                    BString token = a.getBString("token");
                    return new AnnouncePeer.Request(id.getString(), infohash.getString(), implied_port.getInteger(), port.getInteger(), token.getString());
                default:
                    throw new BParseException("invalid q parameter : " + y.getString());
            }

        } else if (Objects.equals(y.getString(), "r")) {
            BDictionary r = raw.getBDictionary("r");
            BString id = r.getBString("id");

            // nodes string , 每一条数据26位 按id(20位) ip(4位) port(2位)顺序排列
            String nodes = r.getBString("nodes").getString();
            if (nodes.length() % 26 != 0){
                throw new BParseException("invalid nodes length : " + y.getString());
            }
            List<String> nodesEle = StringUtils.splitByLength(nodes, 26);

            BString token = raw.getBString("token");

            //TODO how to identify the response type?
            return null;
        } else {
            throw new BParseException("invalid y parameter : " + y.getString());
        }

    }
}
