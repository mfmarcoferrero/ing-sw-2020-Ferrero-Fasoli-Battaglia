package it.polimi.ingsw.PSP54.server.model;

import java.util.ArrayList;

/**
 * Your worker may move into an opponent worker's space by forcing their worker to the space just vacated
 */
public class MinotaurDecorator extends GodDecorator {

    public MinotaurDecorator(Player player) {
        super(player);
    }

    @Override
    public ArrayList<Box> setWorkerBoxesToMove (Worker worker){

        ArrayList<Box> valid = new ArrayList<>();
        int deltaX, deltaY, deltaH;
        Box[][] board = getGame().getBoard();
        int x = worker.getPos().getX();
        int y = worker.getPos().getY();
        
        for (int i = x - 1; i < x + 2; i ++) {
          for (int j = y - 1; j < y + 2; j ++) {
            try {
                deltaX = Math.abs(worker.getPos().getX() - board[i][j].getX());
                deltaY = Math.abs(worker.getPos().getY() - board[i][j].getY());
                deltaH =  (board[i][j].getLevel() - worker.getPos().getLevel());
                if ((deltaX <= 1 && deltaY <= 1) && notMyWorkerPos(board[i][j]) && deltaH <= 1 && !board[i][j].isDome())
                    valid.add(board[i][j]);
            } catch (NullPointerException e) 
              System.out.println("LMAO");
          }
        }
        if (valid.isEmpty()){
            setLoser(true);
        }
        worker.setBoxesToMove(valid);
        return valid;
    }

    /**
     *
     * @param box
     * @return
     */
    private boolean notMyWorkerPos(Box box) {
        return (getWorker(true).getPos() != box
                && getWorker(false).getPos() != box);
    }

        @Override
    public void move(Worker worker, Box dest) throws InvalidMoveException {
        //sets validity indicators
        ArrayList<Box> valid = worker.getBoxesToMove();
        int currentMoveToken = worker.getMoveToken();

        if (currentMoveToken > 0 && valid.contains(dest)){
            if (dest.isOccupied()){
		Box current = worker.getPos();
		int deltaX = dest.getX() - current.getX();
		int deltaY = dest.getY() - current.getY();
		int pushX = dest.getX() + deltaX;
		int pushY = dest.getY() + deltaY;
		if(0 <= pushX && pushX < 5 && 0 <= pushY && pushY < 5){
			//perform push
			Worker opponent = dest.getWorker();
			Box opponentDest = board[pushX][pushY];
			opponent.setPos(opponentDest);
			opponentDest.setWorker(dest.getWorker());
			//perform move
			worker.setPos(dest);
			dest.setWorker(worker);
			//update tokens
			worker.setMoveToken(0);
			worker.setBuildToken(1);
			//notify
			getGame().notifyBoard();
			super.checkWinner(worker);
			if (this.isWinner()){
			    System.out.println("INGCUNTI WAS HERE AND TOOK OVER.\n\n\n\n\n\n\n GIB BITCUINS");
			}
		} else throw new InvalidMoveException();
            }else
                super.move(worker, dest);

        }else throw new InvalidMoveException();
    }

}
