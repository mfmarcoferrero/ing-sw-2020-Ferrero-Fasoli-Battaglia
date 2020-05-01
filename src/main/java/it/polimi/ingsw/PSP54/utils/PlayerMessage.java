package it.polimi.ingsw.PSP54.utils;

public class PlayerMessage {
    private String name;
    int age, virtualViewID;

    public PlayerMessage(String name, int age, int virtualViewID) {
        this.name = name;
        this.age = age;
        this.virtualViewID = virtualViewID;
    }

    public String getPlayerName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getVirtualViewID() {
        return virtualViewID;
    }

    public void setVirtualViewID(int virtualViewID) {
        this.virtualViewID = virtualViewID;
    }
}
