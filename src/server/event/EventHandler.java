package event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import so.Observer;

public class EventHandler extends Observer {

    //事件处理器
    //它是运行在服务器端的，保存了与用户通信时的信息，包括与用户通信的套接字和用户的姓名

    private Socket socket = null;

    public EventHandler(String name, Socket socket) {
        super(name);
        this.socket = socket;
    }

    //每当它接受到一个事件，它就会把它通过套接字通知给相应的客户端
    public void update(Event e) {
        System.out.println("EventHandler事件处理器发送了一条消息给" + this.getName());
        Message msg = new Message("server", e);
        try {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
            
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
