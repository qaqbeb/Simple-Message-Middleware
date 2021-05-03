package event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import so.Subject;

public class Topic extends Subject {
    // 话题，包括一个订阅者列表，事件列表和它指定的事件类型
    private List<EventHandler> listenerList = new ArrayList<>();
    private EventType eventType = null;
    private Set<Event> eventSet = new HashSet<>();

    // check方法代表一次检查过程，对于已经发生了30秒的事件，就将它从事件列表中删除
    private void check() {
        Date now = new Date();
        Set<Event> tempSet = new HashSet<>();
        for (Event e : eventSet) {
            Date d = e.time;
            long sub = now.getTime() - d.getTime();
            if (sub > 30 * 1000) {
                tempSet.add(e);
                System.out.println(e.content + "已经被删除");
            }
        }
        eventSet.removeAll(tempSet);
    }

    // 构造函数里创建了一个一直存在的线程，它每隔一段事件就调用一次检查方法
    public Topic(EventType et) {
        this.eventType = et;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(10 * 1000);
                        check();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public EventType getEventType() {
        return eventType;
    }

    // 将订阅者加到自己的订阅列表，并使它能获取以下当前话题下的所有事件
    public void attach(EventHandler listener) {
        this.listenerList.remove(listener);
        this.listenerList.add(listener);
        for (Event e : eventSet) {
            listener.update(e);
        }
    }

    // 增加一个事件，使所有该话题下的订阅者都接收到这个事件
    public synchronized boolean addEvent(Event e) {
        if (e.type == eventType || e.type == EventType.NORMAL) {
            eventSet.add(e);
            notifyObservers(e);
            return true;
        }
        return false;
    }

    // 通知所有订阅者事件e的发生
    public synchronized void notifyObservers(Event e) {
        for (EventHandler listener : listenerList) {
            listener.update(e);
        }
    }
}
