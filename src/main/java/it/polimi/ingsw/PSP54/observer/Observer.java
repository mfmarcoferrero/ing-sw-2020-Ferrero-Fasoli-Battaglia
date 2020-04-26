package it.polimi.ingsw.PSP54.observer;

public interface Observer<T> {

    void update(T message) throws Exception;

}
