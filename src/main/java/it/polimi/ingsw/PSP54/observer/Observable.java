package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.utils.*;
import it.polimi.ingsw.PSP54.server.model.Player;
import it.polimi.ingsw.PSP54.server.model.Box;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(T message) throws Exception {
        synchronized (observers) {
            for(Observer<T> observer : observers) {
                if (message instanceof Move) {
                    observer.update((Move) message);
                }
                if (message instanceof Build) {
                    observer.update((Build) message);
                }
                if (message instanceof PlayerMessage) {
                    observer.update((PlayerMessage) message);
                }
                if (message instanceof Box[][]) {
                    observer.update((Box[][]) message);
                }
                if (message instanceof String) {
                    observer.update((String) message);
                }
            }
        }
    }

}