package event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import so.Subject;

public class Topic extends Subject {
    private List<EventHandler> listenerList = new ArrayList<>();
    private EventType eventType = null;
    private Set<Event> eventSet = new HashSet<>();

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

    public void attach(EventHandler listener) {
        this.listenerList.remove(listener);
        this.listenerList.add(listener);
        for (Event e : eventSet) {
            listener.update(e);
        }
    }

    public synchronized boolean addEvent(Event e) {
        if (e.type == eventType || e.type == EventType.NORMAL) {
            eventSet.add(e);
            notifyObservers(e);
            return true;
        }
        return false;
    }

    public synchronized void notifyObservers(Event e) {
        for (EventHandler listener : listenerList) {
            listener.update(e);
        }
    }
}
