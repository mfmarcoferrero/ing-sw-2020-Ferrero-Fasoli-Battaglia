package it.polimi.ingsw.PSP54.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Your worker may build an additional time but not on the same space
 */
public class DemeterDecorator extends GodDecorator{

    private Box lastBuilding;

    public DemeterDecorator(Player player) {
        super(player);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToBuild(Worker worker) {

        if (worker.getMoveToken() == 2)
            return super.setWorkerBoxesToBuild(worker);
        else {
            ArrayList<Box> def = super.setWorkerBoxesToBuild(worker);
            Iterator<Box> iterator = def.iterator();
            while (iterator.hasNext()) {

                Box check = iterator.next();
                if (check == getLastBuilding())
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

        worker.setBoxesToBuild(setWorkerBoxesToBuild(worker));
        ArrayList<Box> valid = worker.getBoxesToBuild();
        int currentBuildToken = worker.getBuildToken();

        if (valid.contains(dest)){
            if (dest.getLevel() == 3)
                dest.setDome(true);
            else {
                int currentLevel = dest.getLevel();
                dest.setLevel(currentLevel+1);
            }
            if (currentBuildToken == 2)
                setLastBuilding(dest);

            worker.setBuildToken(currentBuildToken-1);

            }else throw new InvalidBuildingException();
    }

    //getters & setters

    public Box getLastBuilding() {
        return lastBuilding;
    }

    public void setLastBuilding(Box lastBuilding) {
        this.lastBuilding = lastBuilding;
    }
}
