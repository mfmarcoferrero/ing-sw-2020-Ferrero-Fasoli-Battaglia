package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.utils.*;
import it.polimi.ingsw.PSP54.server.model.Box;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(T message){
        synchronized (observers) {
            for(Observer observer : observers) {
                if (message instanceof GameMessage) {
                    observer.update((GameMessage) message);
                }
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
                if (message instanceof CardsToDisplay){
                    observer.update((CardsToDisplay) message);
                }
                if (message instanceof Choice){
                    observer.update((Choice) message);
                }
            }
        }
    }

}