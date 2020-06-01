package it.polimi.ingsw.PSP54.utils.messages;

import java.io.Serializable;

public class DeleteMessage extends GameMessage implements Serializable, Cloneable {

    int X1,Y1,X2,Y2,L1,L2;
    public DeleteMessage(Integer virtualViewID,int x1, int y1, int level1, int x2, int y2, int level2) {
        super(virtualViewID);
        X1=x1;
        Y1=y1;
        X2=x2;
        Y2=y2;
        L1=level1;
        L2=level2;
    }

    public int getX1() {
        return X1;
    }

    public int getY1() {
        return Y1;
    }

    public int getX2() {
        return X2;
    }

    public int getY2() {
        return Y2;
    }

    public int getL1() {
        return L1;
    }

    public int getL2() {
        return L2;
    }
}
