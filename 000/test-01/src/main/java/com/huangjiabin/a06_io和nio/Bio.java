package com.huangjiabin.a06_io和nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Bio implements Runnable
{
    public void run()
    {
        //线程池
        //注意，生产环境不能这么用，具体请参考《java高并发核心编程卷2》
        ExecutorService executor = Executors.newFixedThreadPool(100);
        try
        {
            //服务器监听socket
            ServerSocket serverSocket =
                    new ServerSocket(8080);
           //主线程死循环, 等待新连接到来
            while (!Thread.interrupted())
            {
                Socket socket = serverSocket.accept();
                //接收一个连接后，为socket连接，新建一个专属的处理器对象
                Handler handler = new Handler(socket);
                //创建新线程来handle
                //或者，使用线程池来处理
                new Thread(handler).start();
            }

        } catch (IOException ex)
        { /* 处理异常 */ }
    }

    static class Handler implements Runnable
    {
        final Socket socket;
        Handler(Socket s)
        {
            socket = s;
        }
        public void run()
        {
            //死循环处理读写事件
            boolean ioCompleted=false;
            while (!ioCompleted)
            {
                try
                {
                    byte[] input = new byte[1024];
                    /* 读取数据 */
                    socket.getInputStream().read(input);
                    // 如果读取到结束标志
                    // ioCompleted= true
                    // socket.close();

                    /* 处理业务逻辑，获取处理结果 */
                    byte[] output = null;
                    /* 写入结果 */
                    socket.getOutputStream().write(output);
                } catch (IOException ex)
                { /*处理异常*/ }
            }
        }
    }
}

