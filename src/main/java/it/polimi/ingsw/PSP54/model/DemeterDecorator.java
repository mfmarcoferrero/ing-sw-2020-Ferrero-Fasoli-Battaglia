package it.polimi.ingsw.PSP54.model;

public class DemeterDecorator extends GodDecorator{

    private Box lastBuild;

    public DemeterDecorator(Player player) {
        super(player);
    }

    /*your worker may build an additional time but not on the same space

    PSEUDOCODE:

    move:

        super.move()
        setBuildToken(2)

    setValidBoxesToBuild:

        if(buildToken == 2)
            super.setValidBoxesToBuild
        else
            super - lastBuild

    build:

        if (buildToken == 2)
            setLastBuild


     */

    //only for debug purpose
    @Override
    public void printPower() {
        System.out.println("Demeter");

    }
}
