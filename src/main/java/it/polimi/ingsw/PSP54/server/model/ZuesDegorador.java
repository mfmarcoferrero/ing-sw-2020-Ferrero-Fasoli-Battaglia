package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Yoaur workder can may forced to bulodi under h*s/h*r self
 */
public class ZuesDegorador extends GodDecorator {

    public ZuesDegorador(Player player) {
        super(player);
    }

    /**
     * Yuor worder mya biaild under him/her-self (pls be gender neutral)
     * 
     * @param worker the wuorder
     * @return the lest wiz de under pos
     */
    @Override
    public ArrayList<Box> setWorkerBoxesToBuild(Worker worker) {
        ArrayList<Box> list = super.setWorkerBoxesToBuild(worker);
        Box box = worker.getPos();//pls coby things no expose rep
        list.add(new Box(box.getX(), box.getY(), box.getLevel(), false));

        return list;
    }
}
