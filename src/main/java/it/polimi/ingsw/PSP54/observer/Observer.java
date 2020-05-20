package it.polimi.ingsw.PSP54.observer;

/**
 * A class can implement the Observer interface when it wants to be informed of changes in observable objects.
 * @param <T> the type of message that implementing class wants to observe.
 */
public interface Observer<T> {

    /**
     *  Called whenever the observed object is changed.
     * @param message an argument passed to the notify method.
     */
    void update(T message);

}
