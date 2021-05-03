package event;

import java.io.Serializable;

public class Message implements Serializable{
    public String msgType = null;
    private Object msg = null;
    private String sender = null;

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
