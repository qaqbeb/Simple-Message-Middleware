package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import event.EventType;
import event.Message;

public class Register {
    
    //订阅
    //这个类实现客户端的订阅流程，将用户的信息和输入封装成消息，通过套接字与消息中心通告订阅信息

    private String name = null;

    public Register(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean registerProcedure(Socket socket) {
        System.out.println("请选择你要订阅事件的类型：");
        System.out.println("事件类型说明：");
        System.out.println("1.NORMAL(将会订阅所有类型的事件)");
        System.out.println("2.TXT");
        System.out.println("3.EXE");
        System.out.println("4.JPG");
        System.out.println("5.HTML");
        Scanner sc = new Scanner(System.in);
        int type = sc.nextInt();
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
        // return EventManager.register(this, et);
        registerToManager(socket, et);
        return true;
    }

    public boolean registerToManager(Socket socket, EventType et){
        try {

            Message msg = new Message(this.getName(), et);
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
