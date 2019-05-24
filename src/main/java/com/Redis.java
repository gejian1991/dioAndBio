package com;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Redis {
    public static void main(String[] args) {
        List<SocketChannel> list = new ArrayList();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9091));
            //设置为非阻塞
            ssc.configureBlocking(false);
            while (true){
                SocketChannel socketChannel = ssc.accept();
                if(socketChannel==null){
                    Thread.sleep(1000);
                    System.out.println("没人连接");
                    for (SocketChannel channel : list) {
                        int k =channel.read(byteBuffer);
                        System.out.println(k);
                        if(k!=0){
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array()));
                        }
                    }
                }else{
                    //将read方法设置为非阻塞
                    socketChannel.configureBlocking(false);

                    list.add(socketChannel);
                    //得到套接字，循环所有的套接字，通过套接字获取数据
                    for (SocketChannel channel : list) {
                        int k =channel.read(byteBuffer);
                        System.out.println(k+"=======================================");
                        if(k!=0){
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array()));
                        }
                    }
//
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //阻塞ServerSocket一定是阻塞的
        /*try {
            byte[] bytes = new byte[1024];
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(9876));
            System.out.println("等待连接");
            //阻塞
            Socket accept = serverSocket.accept();
            System.out.println("连接成功");
            //read 阻塞
            System.out.println("等待读取数据");
            accept.getInputStream().read(bytes);
            System.out.println("读到数据"+new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
