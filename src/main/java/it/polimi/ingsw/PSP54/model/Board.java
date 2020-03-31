package it.polimi.ingsw.PSP54.model;

/**
 * Classe tabella di gioco
 */
public class Board {
    public Box[][] boxes = new Box[8][8];
    private int i,j;

    /**
     * Costruttore istanzia le caselle della tabella di gioco
     */
    public Board() {
        for (i = 0; i < 8; i++){
            for (j = 0; j < 8; j++){
                boxes[i][j] = new Box();
            }
        }
    }
}
