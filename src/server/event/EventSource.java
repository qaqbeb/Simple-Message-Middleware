package event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EventSource {
    // 事件的发送者
    // 它可以发布一个事件，并通过已有的套接字将事件转为消息Message传递给消息中心

    private String name = "null";

    public EventSource() {

    }

    public EventSource(String name) {
        this.name = name;
    }

    public Event CreateEvent(String content) {
        Event e = new Event(name, content);
        return e;
    }

    public Event CreateEvent(String content, EventType et) {
        Event e = new Event(name, content, et);
        return e;
    }

    //通过套接字发送事件
    public boolean sendToManager(Socket socket, Event e) {
        try {

            Message msg = new Message(this.name, e);
            // 向服务器发送信息
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(msg);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return true;
    }

}
