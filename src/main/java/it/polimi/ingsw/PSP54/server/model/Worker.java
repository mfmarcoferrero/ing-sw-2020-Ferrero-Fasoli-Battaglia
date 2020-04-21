package it.polimi.ingsw.PSP54.server.model;

/**
 * Classe operaio
 */
public class Worker {
    private int workerID;
    private Player owner;
    private boolean male;
    private String color;
    public Box pos;
    private int i,j;

    public Worker(Player player,String color, int wID){
        this.owner = player;
        this.color = color;
        this.workerID = wID;
    }

    /**
     * Metodo per verificare se il worker è bloccato nel movimento
     * @return
     */
    public boolean canWorkerMove() {
        for (int i = 0 ; i < Box.BOARD_SIZE ; i++){
            for (int j = 0; j < Box.BOARD_SIZE; j++){
                if (owner.power.validMove(pos,owner.game.board[i][j])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Metodo per verificare se il worker può costruire
     * @return
     */
    public boolean canWorkerBuild() {
        for (int i = 0 ; i < Box.BOARD_SIZE ; i++){
            for (int j = 0; j < Box.BOARD_SIZE; j++){
                if (owner.power.validBuilding(pos,owner.game.board[i][j],false)){
                    return true;
                }
            }
        }
        return false;
    }

    public String getColor() {
        return color;
    }

    public boolean isMale() {
        return male;
    }

    public void setPos(Box pos) {
        this.pos = pos;
    }

    @Override
    public String toString(){
        return "ID: " + workerID + " PLAYER: " + owner.getPlayerName();
    }
}

