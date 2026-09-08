package com.huangjiabin.a06_io和nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
public class NIOClient {
   public static void main(String[] args) throws IOException {
       SocketChannel clientChannel = SocketChannel.open();
       clientChannel.configureBlocking(false);
       clientChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
       while (!clientChannel.finishConnect()) {
           System.out.println("正在连接服务器...");
       }
       ByteBuffer buffer = ByteBuffer.allocate(1024);
       buffer.put("Hello, Server!".getBytes());
       buffer.flip();
       clientChannel.write(buffer);
       buffer.clear();
       int bytesRead = clientChannel.read(buffer);
       if (bytesRead > 0) {
           buffer.flip();
           System.out.println("收到服务器响应：" + new String(buffer.array(), 0, bytesRead));
       }
       clientChannel.close();
   }
}
