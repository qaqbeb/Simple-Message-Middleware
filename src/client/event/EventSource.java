package event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EventSource {

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
