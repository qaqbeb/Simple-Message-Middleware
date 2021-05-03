package so;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class ISubject implements Observable {

    List<InvalidationListener> listenerList = new ArrayList<>();

    @Override
    public void addListener(InvalidationListener listener) {
        listenerList.remove(listener);
        listenerList.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerList.remove(listener);
    }

}