package com.piao.bittorrent.crawler.bencode.model;


import lombok.Getter;

import java.util.ArrayList;


public class BList extends BObject{

    @Getter
    private ArrayList<BObject> value = new ArrayList<>();

    public void add(BObject bObject){
        value.add(bObject);
    }
}
