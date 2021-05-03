package so;

import java.util.ArrayList;
import java.util.List;

public class Subject extends ISubject {
    private List<Observer> listenerList = new ArrayList<>();


    public void attach(Observer o) {
        this.addListener(o);
    }

    public void detach(Observer o) {
        this.removeListener(o);
    }

    public void notifyObservers(String str) {
        for (Observer o : listenerList) {
            o.update(str);
        }
    }
}
