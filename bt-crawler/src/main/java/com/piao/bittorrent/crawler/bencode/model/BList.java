package com.piao.bittorrent.crawler.bencode.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@NoArgsConstructor
public class BList extends BObject{

    @Getter
    private ArrayList<BObject> list = new ArrayList<>();

}
