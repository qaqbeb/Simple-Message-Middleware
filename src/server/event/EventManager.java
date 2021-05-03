package event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventManager {

    //这个类中的构造方法中启动了一个一直运行的线程，这个线程循环地判断消息队列中是否有未处理的事件
    //并将事件或者订阅请求分发到各个话题topic下

    private static List<Topic> topicList = new ArrayList<>(); // topic的列表
    private static EventManager instance = new EventManager(); // 该类的唯一实例
    private static Queue<Event> messageQueue = new LinkedList<>(); // 消息队列，存放暂未处理的消息

    public static Queue<Event> getMessageQueue() {
        return messageQueue;
    }

    public static void setMessageQueue(Queue<Event> messageQueue) {
        EventManager.messageQueue = messageQueue;
    }

    private EventManager() {
        for (EventType e : EventType.values()) {
            Topic tp = new Topic(e);
            topicList.add(tp);
        }

        // 这个线程循环地查看消息队列中有无未处理的消息
        // 如果消息队列不为空，则取出第一个事件处理
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (!messageQueue.isEmpty()) {
                        // 处理事件
                        receiveEvent(messageQueue.poll());
                    }
                }
            }
        });

        t.start();
    }

    public EventManager getInstance() {
        return instance;
    }

    // 处理事件
    // 把事件交给相应的topic，如果topic的类型与该事件类型相同，则使topic里增加该事件
    public static boolean receiveEvent(Event e) {
        boolean re = false;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Topic tp : topicList) {
                    if (e.type == tp.getEventType()) {
                        tp.addEvent(e);
                    }
                }
            }
        });
        t.start();
        re = true;
        return re;
    }

    // 注册
    // 获取一个EventHandler，判断他选择订阅的事件类型，找到相应事件类型的topic，将其添加到topic的观察者列表中
    public static boolean register(EventHandler listener, EventType et) {
        if (et == EventType.NORMAL) {
            for (Topic tp : topicList) {
                tp.attach(listener);
            }
            return true;
        } else {
            for (Topic tp : topicList) {
                if (tp.getEventType() == et) {
                    tp.attach(listener);
                    return true;
                }
            }
        }
        return false;
    }

}
