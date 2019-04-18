package com.piao.bittorrent.crawler.Utils;

import org.springframework.util.StringUtils;
import sun.applet.Main;
import sun.net.util.IPAddressUtil;

import java.util.Arrays;

public class IPAddressUtils {


    /**
     * @param bytes 4 bytes ip
     */
    public static String bytes2IP (byte[] bytes){

        if (bytes.length != 4){
            //TODO
        }

        //  & 0xFF mask for convert byte to unsign int;
        //  正数不变, 负数从256开始减 : -23 = 233
        return String.join("."
                , Integer.toString(bytes[0] & 0xff)
                , Integer.toString(bytes[1] & 0xff)
                , Integer.toString(bytes[2] & 0xff)
                , Integer.toString(bytes[3] & 0xff));
    }

    public static byte[] IP2Bytes (String ip){
        if (StringUtils.isEmpty(ip)) {
            //TODO
        }
        String[] ips = ip.split("\\.");
        if (ips.length != 4) {
            //TODO
        }
        return new byte[]{
                Integer.valueOf( ips[0]).byteValue(),
                Integer.valueOf( ips[1]).byteValue(),
                Integer.valueOf( ips[2]).byteValue(),
                Integer.valueOf( ips[3]).byteValue(),
        };
    }

    /**
     * @param bytes 2 bytes port
     */
    public static int bytes2Port(byte[] bytes){
        if (bytes.length != 2){
            //TODO
        }

        //return (bytes[0] & 0xff) * 256 + (bytes[1] & 0xff);

        //左移8位
        return ((bytes[0] & 0xff) << 8)  | (bytes[1] & 0xff);
    }

    /**
     *
     * @param port int port between 1 ~ 65535
     * @return 2 bytes port
     */
    public static byte[] port2Bytes(int port){

        return new byte[]{(byte) (port >> 8), (byte) (port & 0xff)};
    }

    public static void main(String[] args) {
        System.out.println(-23 & 0xff );
        System.out.println(bytes2Port(new byte[]{26,-23}));
        System.out.println(Arrays.toString(port2Bytes(6889)));
    }

}
