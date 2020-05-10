package it.polimi.ingsw.PSP54.observer;

import it.polimi.ingsw.PSP54.utils.PlayerAction;

import java.util.ArrayList;
import java.util.List;

public class PlayerActionsManager implements Observable<PlayerAction>{

    private final List<Observer<PlayerAction>> observers = new ArrayList<>();

    /**
     * Called when an observable object wants to add an observer to its set of observers.
     *
     * @param observer an observer to be added.
     */
    @Override
    public void addObserver(Observer<PlayerAction> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Called when an observable object wants to remove an observer to its set of observers.
     *
     * @param observer the observer to be removed.
     */
    @Override
    public void removeObserver(Observer<PlayerAction> observer) {
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
    public void notify(PlayerAction message) {
        synchronized (observers){
            for (Observer<PlayerAction> observer : observers){
                /*TODO: instanceof switch body
                    try to use only PlayerAction class instead of a class for each choice:
                    (message.getChoice() instanceof PlayerCredentials) [...]
                    (message.getChoice() instanceof CardChoice) [...]
                    [...]

                 */
            }
        }
    }
}
