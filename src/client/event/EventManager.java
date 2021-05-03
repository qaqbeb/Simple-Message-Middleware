package event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventManager {
    private static List<Topic> topicList = new ArrayList<>();
    private static EventManager instance = new EventManager();
    private static Queue<Event> messageQueue = new LinkedList<>();

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

        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while(!messageQueue.isEmpty()){
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
