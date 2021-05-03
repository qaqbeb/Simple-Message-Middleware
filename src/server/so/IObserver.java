package so;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class IObserver implements InvalidationListener{

    @Override
    public void invalidated(Observable observable) {
        observable.addListener(this);
    }
    
}
