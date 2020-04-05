package it.polimi.ingsw.PSP54.model;

/**
 * Classe operaio
 */
public class Worker {
    protected Player owner;
    protected Box pos;
    protected boolean male;

    public Worker(Player owner, Box position){
        this.owner = owner;
        this.pos= position;
    }

    /**
     * Metodo per posizionare l'operaio
     * @param dest casella di destinazione
     */
    public void setPos (Box dest) throws invalidMoveException {
            if (!dest.isOccupied()) {
                pos.setWorker(null);
                this.pos = dest;
                dest.setWorker(this);
            }
            else throw new invalidMoveException();
    }

    public Box getPos() {
        return pos;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
