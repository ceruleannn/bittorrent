package com.piao.bittorrent.crawler.bencode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  Bobject<泛型>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BString extends BObject{

    private String string;
}
