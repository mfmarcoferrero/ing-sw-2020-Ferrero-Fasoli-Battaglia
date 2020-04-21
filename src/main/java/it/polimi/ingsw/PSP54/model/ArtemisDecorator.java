package it.polimi.ingsw.PSP54.model;
//TODO: Test
public class ArtemisDecorator extends GodDecorator{


    public ArtemisDecorator(Player player) {
        super(player);
    }

    /*your worker MAY move an additional time but NOT BACK to its initial space

    PSEUDOCODE:

    set moveToken to 2
    set buildToken to 1

    move:

        if (moveToken == 2)
            perform move
            moveToken --
        else if (moveToken == 1)
            perform move
            moveToken --
        setBoxToMove & setBoxToBuild

    build:

        if (moveToken = 2 || buildToken <= 0)
            exception
        else if(moveToken = 1 && buildToken > 0)
            perform build
            moveToken--; buildToken--;

     */

    //TODO: Implement & JavaDoc
    @Override
    public Worker turnInit(Boolean male) {
        return super.turnInit(male);
    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
    }

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        super.build(worker, dest);
    }

    //only for debug purpose

    @Override
    public void printPower() {
        System.out.println("Artemis");

    }
}
