package it.polimi.ingsw.PSP54.utils.choices;

/**
 * Represent the choice of player's name and age.
 */
public class PlayerCredentials extends PlayerChoice {

    private String name;
    int age;

    public PlayerCredentials(String name, int age) {
        this.name = name;
        this.age = age;
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

}
