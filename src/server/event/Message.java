package event;

import java.io.Serializable;

//消息作为客户端和服务器通信的介质，它要实现Serializable方法，使其能够序列化为流，以字节流的形式在套接字间传递
public class Message implements Serializable{
    public String msgType = null;
    private Object msg = null;
    private String sender = null;

    //如果待封装的对象类型为event.EventType，要注明该条消息的类型使订阅register
    public Message(String sender, Object o) {
        this.sender = sender;
        this.msgType = o.getClass().getName();
        if (msgType.equals("event.EventType")) {
            msgType = "register";
        }
        this.msg = o;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
