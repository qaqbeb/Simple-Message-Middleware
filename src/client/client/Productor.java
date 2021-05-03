package client;

import java.net.Socket;
import java.util.Scanner;

import event.Event;
import event.EventSource;
import event.EventType;

public class Productor extends EventSource {

    // 事件产生者，相比EventSource，它只是把发消息整合为了一个流程

    Productor(String name) {
        super(name);
    }

    public boolean sendEventProcedure(Socket socket) {
        Event e = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入事件的类型");
        System.out.println("事件类型说明(默认为NORMAL)：");
        System.out.println("1.NORMAL");
        System.out.println("2.TXT");
        System.out.println("3.EXE");
        System.out.println("4.JPG");
        System.out.println("5.HTML");
        int type = Integer.parseInt(sc.nextLine());
        EventType et = EventType.NORMAL;
        switch (type) {
        case 1:
            break;
        case 2:
            et = EventType.TXT;
            break;
        case 3:
            et = EventType.EXE;
            break;
        case 4:
            et = EventType.JPG;
            break;
        case 5:
            et = EventType.HTML;
            break;
        default:
            break;
        }
        System.out.println("请输入事件的内容（一行）：");
        String content = sc.nextLine();
        e = this.CreateEvent(content, et);
        boolean re = this.sendToManager(socket, e);
        if (re) {
            System.out.println("成功发布事件");
        } else {
            System.out.println("发布事件失败");
        }
        return re;
    }
}
