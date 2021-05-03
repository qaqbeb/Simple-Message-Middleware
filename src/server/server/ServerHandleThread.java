package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Queue;

import event.Event;
import event.EventHandler;
import event.EventManager;
import event.EventType;
import event.Message;

public class ServerHandleThread implements Runnable {
    
    //它时一个处理线程，在创建时就创建一个新线程，处理用户的请求

    Socket socket = null;
    private Queue<Event> MQ = null;

    public ServerHandleThread(Socket socket, Queue<Event> MQ) {
        super();
        this.MQ = MQ;
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        try {
            InputStream is = socket.getInputStream();
            //如果用户的套接字发送了信息，则判断该条消息的内容，分类后交给事件管理器分发处理
            while (is != null) {
                ObjectInputStream ois = new ObjectInputStream(is);

                Message msg = (Message) ois.readObject();

                System.out.println("客户端发送的对象：" + msg);

                if (msg.msgType.equals("event.Event")) {
                    MQ.offer((Event) msg.getMsg());
                } else if (msg.msgType.equals("register")) {
                    EventHandler EH = new EventHandler(msg.getSender(), socket);
                    EventType et = (EventType) msg.getMsg();
                    EventManager.register(EH, et);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}