package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.utils.choices.PlayerChoice;

import java.util.ArrayList;
import java.util.List;

public class PlayerChoicesManager implements Observable<PlayerChoice> {

    private final List<Observer<PlayerChoice>> observers = new ArrayList<>();

    /**
     * Called when an observable object wants to add an observer to its set of observers.
     *
     * @param observer an observer to be added.
     */
    @Override
    public void addObserver(Observer<PlayerChoice> observer) {
        synchronized (observers){
            observers.add(observer);
        }
    }

    /**
     * Called when an observable object wants to remove an observer to its set of observers.
     *
     * @param observer the observer to be removed.
     */
    @Override
    public void removeObserver(Observer<PlayerChoice> observer) {
        synchronized (observers){
            observers.remove(observer);
        }
    }

    /**
     * Called when the observable object changes state to notify its observers.
     *
     * @param message a message for the observers.
     */
    @Override
    public void notify(PlayerChoice message) {
        synchronized (observers){
            for (Observer<PlayerChoice> observer: observers){
                //TODO: instanceof switch body
            }
        }
    }
}
