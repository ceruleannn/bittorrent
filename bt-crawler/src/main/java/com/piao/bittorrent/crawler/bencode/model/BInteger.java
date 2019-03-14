package com.piao.bittorrent.crawler.bencode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BInteger extends BObject{
    private Integer value;
}
