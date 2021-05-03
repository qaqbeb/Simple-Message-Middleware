package event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import so.Observer;

public class EventHandler extends Observer {

    private Socket socket = null;

    public EventHandler(String name, Socket socket) {
        super(name);
        this.socket = socket;
    }

    public void update(Event e) {
        System.out.println("EventHandler事件处理器发送了一条消息给" + this.getName());
        Message msg = new Message("server", e);
        try {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
            oos.close();
            os.close();
            socket.shutdownOutput();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
