package it.polimi.ingsw.PSP54.observer;


public interface Observable<T> {


    void addObserver(Observer<T> observer);

    void removeObserver(Observer<T> observer);

    void notify(T message);

}