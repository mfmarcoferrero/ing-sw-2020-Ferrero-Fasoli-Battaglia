package it.polimi.ingsw.PSP54.observer;

/**
 * A class can implement the Observer interface when it wants to be informed of changes in observable objects.
 * @param <T>
 */
public interface Observer<T> {

    /**
     *This method is called whenever the observed object is changed.
     * @param message
     */
    void update(T message);

}
