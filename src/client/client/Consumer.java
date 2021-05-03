package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import event.Event;
import event.Message;

public class Consumer {
    // 消费者
    // 它有一个一直存在的线程，这个线程不停歇地接受来自服务器发送的事件信息，并把它消费

    private String name = null;
    Socket socket = null;
    static int count = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Consumer(String name, Socket socket, Thread t) {
        this.name = name;
        this.socket = socket;

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int mode = 1;
                    InputStream is = socket.getInputStream();
                    while (is != null) {
                        if (mode == 1) {
                            mode++;
                            continue;
                        }
                        ObjectInputStream ois = new ObjectInputStream(is);
                        Message msg = (Message) ois.readObject();
                        Event e = (Event) msg.getMsg();
                        update(e);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    //消费事件
    public void update(Event e) {
        System.out.println("消费者" + this.getName() + "\n消费了一个事件:" + e.content + "\n事件发生的时间为" + e.time);
        count++;
        System.out.println("当前已订阅" + count + "个信息");
    }

}
