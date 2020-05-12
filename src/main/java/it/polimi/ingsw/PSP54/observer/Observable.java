package it.polimi.ingsw.PSP54.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * A class can extend the Observer interface when it wants to represent an event manager.
 * @param <T> the type of message the implementing class wants to manage.
 */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Called when an observable object wants to add an observer to its set of observers.
     * @param observer an observer to be added.
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Called when an observable object wants to remove an observer to its set of observers.
     * @param observer the observer to be removed.
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Called when the observable object changes state to notify its observers.
     * @param message a message for the observers.
     */
    protected void notify(T message){
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }

    }

}








