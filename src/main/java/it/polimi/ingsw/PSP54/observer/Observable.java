package it.polimi.ingsw.PSP54.observer;

/**
 * A class can implement the Observer interface when it wants to represent an event manager.
 * @param <T> the type of message the implementing class wants to manage.
 */
public interface Observable<T> {

    /**
     * Called when an observable object wants to add an observer to its set of observers.
     * @param observer an observer to be added.
     */
    void addObserver(Observer<T> observer);

    /**
     * Called when an observable object wants to remove an observer to its set of observers.
     * @param observer the observer to be removed.
     */
    void removeObserver(Observer<T> observer);

    /**
     * Called when the observable object changes state to notify its observers.
     * @param message a message for the observers.
     */
    void notify(T message);

}