package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import event.EventManager;

public class Server {

    static Set<Socket> socketSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        int count = 0;
        System.out.println("服务器启动，等待客户端的连接。。。");
        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();
            socketSet.add(socket);
            ++count;

            //接受到来自客户端的套接字，建立的一个新的线程接收处理之
            Thread serverHandleThread = new Thread(new ServerHandleThread(socket, EventManager.getMessageQueue()));

            serverHandleThread.setPriority(4);
            serverHandleThread.start();
            System.out.println("上线的客户端有" + count + "个！");
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("当前客户端的IP地址是：" + inetAddress.getHostAddress());

        }
    }
}
