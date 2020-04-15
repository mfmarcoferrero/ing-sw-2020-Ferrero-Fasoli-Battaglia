package it.polimi.ingsw.PSP54.model;

public  class Athena extends God {
    public Athena(Player player) {
        super(player);
    }

    /**
     * Athena non modifica la modalità di costruzione
     * @param source
     * @param dest
     * @param setDome
     * @return
     */
    @Override
    public boolean validBuilding(Box source,Box dest,boolean setDome) {
        return normalValidBuilding(source,dest);
    }

    /**
     * Athena modifica il boolean canMoveUp di tutti gli altri player se il suo worker sale di livello
     * @param source
     * @param dest
     * @return
     */
    @Override
    public boolean validMove(Box source, Box dest) {
        int deltaLevelUp = dest.level - source.level;
        if (normalValidMove(source,dest)){
            if (deltaLevelUp == 1) {
                for (Player p : player.game.players) {
                    if (p.getGodID() != Player.ATHENA){
                        p.power.setCanMoveUp(false);
                    }
                }
            }
            else
                for (Player p : player.game.players) {
                    if (p.getGodID() != Player.ATHENA){
                        p.power.setCanMoveUp(true);
                    }
                }
            return true;
        }
        else
            return false;
    }
}
