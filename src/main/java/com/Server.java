package com;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9091));
            ssc.configureBlocking(false);
            Selector selector = Selector.open();
            //注册channe，并且指定感兴趣的事件Accpet
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(selector.keys().size());
            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            ByteBuffer writeBuff= ByteBuffer.allocate(128);
            writeBuff.put("received".getBytes());
            writeBuff.flip();

            while (true){
                int nReady = selector.select();//不管poll ，epoll ，select都是通过这个函数调用
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                //不全。。。。。。。。。。。。。。
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
