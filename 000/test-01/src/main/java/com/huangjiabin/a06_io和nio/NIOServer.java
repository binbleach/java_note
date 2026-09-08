package com.huangjiabin.a06_io和nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
public class NIOServer {
   private Selector selector;
   private ByteBuffer buffer = ByteBuffer.allocate(1024);
   public NIOServer(int port) throws IOException {
       ServerSocketChannel serverChannel = ServerSocketChannel.open();
       serverChannel.configureBlocking(false);
       serverChannel.socket().bind(new InetSocketAddress(port));
       selector = Selector.open();
       serverChannel.register(selector, SelectionKey.OP_ACCEPT);
       System.out.println("服务器启动，监听端口：" + port);
   }
   public void listen() throws IOException {
       while (selector.select() > 0) {  // 第一种写法，selector.select()会阻塞，直到有连接过来
       /*   第二种写法
       while (true) {
           selector.select();
       */
           Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
           while (keys.hasNext()) {
               SelectionKey key = keys.next();
               keys.remove();
               if (key.isAcceptable()) {
                   handleAccept(key);
               } else if (key.isReadable()) {
                   handleRead(key);
               }
           }
       }
   }
   private void handleAccept(SelectionKey key) throws IOException {
       ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
       SocketChannel clientChannel = serverChannel.accept();
       clientChannel.configureBlocking(false);
       clientChannel.register(selector, SelectionKey.OP_READ);
       System.out.println("客户端连接：" + clientChannel.getRemoteAddress());
   }
   private void handleRead(SelectionKey key) throws IOException {
       SocketChannel clientChannel = (SocketChannel) key.channel();
       buffer.clear();
       int bytesRead = clientChannel.read(buffer);
       if (bytesRead > 0) {
           buffer.flip();
           String message = new String(buffer.array(), 0, bytesRead);
           System.out.println("收到消息：" + message);
           clientChannel.write(ByteBuffer.wrap(("Echo: " + message).getBytes()));
       } else {
           clientChannel.close();
       }
   }
   public static void main(String[] args) throws IOException {
       new NIOServer(8080).listen();
   }
}
