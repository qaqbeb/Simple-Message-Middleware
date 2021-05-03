package event;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{

    //事件类

    public String sourceName = "null";      //事件发布者的名称
    public Date time = null;                //事件发生的事件
    public String content = null;           //事件的内容，形式为字符串
    public EventType type = EventType.NORMAL;       //事件的类型，参见EventType，默认为NORMAL

    public Event() {
        time = new Date();
    }

    public Event(String content) {
        time = new Date();
        this.content = content;
    }

    Event(String sourceName, String content) {
        time = new Date();
        this.sourceName = sourceName;
        this.content = content;
    }

    Event(String sourceName, String content, EventType type) {
        time = new Date();
        this.sourceName = sourceName;
        this.content = content;
        this.type = type;
    }

}
