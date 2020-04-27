package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;
import java.util.Iterator;
//TODO: JavaDoc
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

    @Override
    public ArrayList<Box> setWorkerBoxesToBuild(Worker worker) {

        if (worker.getMoveToken() == 2)
            return super.setWorkerBoxesToBuild(worker);
        else {
            ArrayList<Box> def = super.setWorkerBoxesToBuild(worker);
            Iterator<Box> iterator = def.iterator();
            while (iterator.hasNext()) {

                Box check = iterator.next();
                if (check.getLevel() == worker.getPos().getLevel() + 1)
                    iterator.remove();
            }

            return def;
        }

    }

    @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        super.move(worker, dest);
        worker.setBuildToken(2);
    }

    @Override
    public void build(Worker worker, Box dest) throws InvalidBuildingException {
        if (worker.getBuildToken()==2) {
            setLastBuild(dest);
            super.build(worker, dest);
        }else{
            ArrayList<Box> def = super.setWorkerBoxesToBuild(worker);
            Iterator<Box> iterator = def.iterator();
            while (iterator.hasNext()) {

                Box check = iterator.next();
                if (check == getLastBuild())
                    iterator.remove();
            }
        }
    }

    //getters & setters

    public Box getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Box lastBuild) {
        this.lastBuild = lastBuild;
    }

    //only for debug purpose
    @Override
    public void printPower() {
        System.out.println("Demeter");

    }
}
