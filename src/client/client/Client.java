package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
    // 用户类

    // 它包含一个订阅模块，消息产生模块和消费者模块，具体各个模块功能参照其各自的注释

    private Register register = null;
    private Productor productor = null;
    private Consumer consumer = null;
    String name = null;

    Client(String name) {
        this.name = name;
        register = new Register(name);
        productor = new Productor(name);
    }

    public Consumer getConsumer() {
        return consumer;
    }

    // 初始化其消费者线程，并获取到这个线程，随main方法结束终止该线程
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

        Client c = new Client("qaqbeb");
        Thread t = c.initConsumer(socket);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("现在是" + c.name + "的行动阶段， 请选择");
            System.out.println("1.发布事件\n2.订阅频道\n3.退出行动");
            int op = sc.nextInt();
            if (op == 1) {
                c.sendEventProcedure(socket);
            } else if (op == 2) {
                c.registerProcedure(socket);
            } else if (op == 3) {
                System.out.println("现在退出了" + c.name + "的行动阶段");
                break;
            } else {
                System.out.println("输入错误");
            }
        }

        t.interrupt();
        sc.close();
    }

}
