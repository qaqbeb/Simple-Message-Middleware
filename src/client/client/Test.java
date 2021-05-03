package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import event.Event;

public class Test {
    // 测试消息中间件的最大处理能力


    private Register register = null;
    private Productor productor = null;
    private Consumer consumer = null;
    String name = null;

    Test(String name) {
        this.name = name;
        register = new Register(name);
        productor = new Productor(name);
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Thread initConsumer(Socket socket) {
        Thread t = null;
        consumer = new Consumer(this.name, socket, t);
        return t;
    }

    public boolean sendEventProcedure(Socket socket) {
        return this.productor.sendEventProcedure(socket);
    }

    public boolean registerProcedure(Socket socket) {
        return this.register.registerProcedure(socket);
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1", 8088);

        Test c = new Test("qaqbeb");
        Thread t = c.initConsumer(socket);

        Scanner sc = new Scanner(System.in);
        c.register.registerProcedure(socket);

        for (int i = 0; i < 1000; i++) {
            Event e = new Event("第" + i + "件事件");
            c.productor.sendToManager(socket, e);
        }

        System.out.println();

        t.interrupt();
        sc.close();
    }

}
