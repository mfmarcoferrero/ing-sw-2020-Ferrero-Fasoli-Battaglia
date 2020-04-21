package it.polimi.ingsw.PSP54.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;

public class StandardPlayerTest {

    private Game game;
    Vector<Player> players;
    Player player_1, player_2, player_3;
    Box[][] board;
    Worker currentWorker;
    int x;
    int y;


    @Before
    public void setUp() {
        //initialize game
        game = new Game();
        board = game.getBoard();
        //initialize players
        game.newPlayer("1");
        game.newPlayer("2");
        game.newPlayer("3");
        players = game.getPlayers();
        player_1 = players.get(0);
        player_2 = players.get(1);
        player_3 = players.get(2);
        players.get(0).setGame(game);
    }

    @After
    public void tearDown() {
        game = null;
    }

    //setWorkerBoxesToMove
    @Test
    public void setWorkerBoxesToMove_EmptyBoardsCenter_AllAdjacentBoxes() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = players.get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result );
    }

    @Test
    public void setWorkerBoxesToMove_SomeFirstLevelsBoardCenter_AllAdjacentBoxes() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = players.get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][1].setLevel(1);
        board[1][3].setLevel(1);
        board[2][3].setLevel(1);
        board[3][2].setLevel(1);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = players.get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_SomeUpperLevelsBoardCenter_AdjacentBoxesExceptUpperLevels () {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][1].setLevel(3);
        board[1][3].setLevel(2);
        board[2][3].setLevel(3);
        board[3][2].setLevel(2);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][2]);
        expected.add(board[2][1]);
        expected.add(board[3][1]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_SomeOccupiedBoxesAndUpperLevelsBoardCenter_AdjacentBoxesExceptOccupiedOrUpperLevels () {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes levels
        board[1][2].setDome(true);
        board[2][1].setLevel(3);
        board[3][1].setLevel(3);
        board[3][1].setDome(true);
        board[3][3].setWorker(game.getPlayers().get(0).choseWorker(false));

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][3]);
        expected.add(board[2][3]);
        expected.add(board[3][2]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_FromSecondLevelBoardCenter_AllAdjacentBoxes () {

        //initialize worker and sets position
        x = 2;
        y = 2;
        board[2][2].setLevel(2);
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToMove_EmptyBoardsEdge_CornerBoxes() {

        //initialize worker and sets position
        x = 4;
        y = 4;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[3][3]);
        expected.add(board[3][4]);
        expected.add(board[4][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToMove(currentWorker);

        assertEquals(expected, result);
    }

    //setWorkerBoxesToBuild

    @Test
    public void setWorkerBoxesToBuild_EmptyBoardCenter_AllAdjacentBoxes() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToBuild(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToBuild_UpperLevelsCenter_AllAdjacentBoxes() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes level
        board[1][1].setLevel(3);
        board[1][2].setLevel(3);
        board[1][3].setLevel(3);
        board[2][1].setLevel(3);
        board[2][3].setLevel(3);
        board[3][1].setLevel(3);
        board[3][2].setLevel(3);
        board[3][3].setLevel(3);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][1]);
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);

        ArrayList<Box> result = game.getPlayers().get(0).setWorkerBoxesToBuild(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToBuild_SomeDomed_AllAdjacentExceptDomed() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes level
        board[1][1].setDome(true);
        board[1][3].setDome(true);
        board[3][1].setDome(true);
        board[3][3].setDome(true);

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][2]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][2]);

        ArrayList<Box> result = player_1.setWorkerBoxesToBuild(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void setWorkerBoxesToBuild_OneOccupied_AllAdjacentExceptOccupied() {

        //initialize worker and sets position
        x = 2;
        y = 2;
        currentWorker = game.getPlayers().get(0).choseWorker(true);
        currentWorker.setPos(game.getBoard()[x][y]);
        game.getBoard()[x][y].setWorker(currentWorker);

        //set boxes occupation
        //TODO
        board[1][1].setWorker(game.getPlayers().get(0).choseWorker(false));

        //generate expected result
        ArrayList<Box> expected = new ArrayList<>();
        expected.add(board[1][2]);
        expected.add(board[1][3]);
        expected.add(board[2][1]);
        expected.add(board[2][3]);
        expected.add(board[3][1]);
        expected.add(board[3][2]);
        expected.add(board[3][3]);


        ArrayList<Box> result = player_1.setWorkerBoxesToBuild(currentWorker);

        assertEquals(expected, result);
    }

    @Test
    public void move_EmptyBoardCenter_NorthWest() throws InvalidMoveException {

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);
        currentWorker.setBoxesToMove(player_1.setWorkerBoxesToMove(currentWorker));

        //invoke move() method
        player_1.move(currentWorker, board[1][1]);

        //set result
        Box result = board [1][1];

        assertEquals(currentWorker.getPos(), result);
        assertEquals(result.getWorker(), currentWorker);

    }

    @Test
    public void move_GroundLevelCenter_NorthWestOneUp() throws InvalidMoveException {

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);

        //set box levels
        board[1][1].setLevel(1);

        //set available boxes
        currentWorker.setBoxesToMove(player_1.setWorkerBoxesToMove(currentWorker));

        //invoke move() method
        player_1.move(currentWorker, board[1][1]);

        //set result
        Box result = board [1][1];

        assertEquals(currentWorker.getPos(), result);
        assertEquals(result.getWorker(), currentWorker);

    }

    @Test
    public void move_GroundLevelCenter_UpperLevelThrowsException() {

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);

        //set box levels
        board[1][1].setLevel(2);

        //set available boxes
        currentWorker.setBoxesToMove(player_1.setWorkerBoxesToMove(currentWorker));

        Exception thrown = null;

        //invoke move() method
        try {
            player_1.move(currentWorker, board[1][1]);
        }catch (InvalidMoveException e){
            thrown = e;
        }

        //set result
        Box result = board [2][2];

        assertEquals(currentWorker.getPos(), result);
        assertEquals(result.getWorker(), currentWorker);

        assertNotNull(thrown);

    }

    @Test
    public void move_GroundLevelCenter_OccupiedThrowException() {

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);

        //set box levels
        board[1][1].setWorker(player_2.choseWorker(false));

        //set available boxes
        currentWorker.setBoxesToMove(player_1.setWorkerBoxesToMove(currentWorker));

        Exception thrown = null;

        //invoke move() method
        try {
            player_1.move(currentWorker, board[1][1]);
        }catch (InvalidMoveException e){
            thrown = e;
        }

        //set result
        Box expected = board [2][2];

        assertEquals(currentWorker.getPos(), expected);
        assertEquals(expected.getWorker(), currentWorker);

        assertNotNull(thrown);

    }

    @Test
    public void build_EmptyBoardCenter_SouthEastLevelUp() throws InvalidBuildingException{

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setBuildToken(1);
        currentWorker.setMoveToken(0);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);


        //set available boxes
        currentWorker.setBoxesToBuild(player_1.setWorkerBoxesToMove(currentWorker));

        //invoke build() method
        player_1.build(currentWorker, board[3][3]);

        assertEquals(1, board[3][3].getLevel());

    }

    @Test
    public void build_EmptyBoardCenter_SouthEastDomed() throws InvalidBuildingException{

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setBuildToken(1);
        currentWorker.setMoveToken(0);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);

        //set box levels
        board[3][3].setLevel(3);

        //set available boxes
        currentWorker.setBoxesToBuild(player_1.setWorkerBoxesToBuild(currentWorker));

        //invoke build() method
        player_1.build(currentWorker, board[3][3]);

        assertEquals(3, board[3][3].getLevel());
        assertTrue(board[3][3].isDome());

    }

    @Test
    public void build_EmptyBoardCenter_AlreadyDomedThrowException() {

        //set workers initial position
        currentWorker = player_1.turnInit(true);
        currentWorker.setBuildToken(1);
        currentWorker.setMoveToken(0);
        currentWorker.setPos(board[2][2]);
        board[2][2].setWorker(currentWorker);

        //set box levels
        board[3][3].setLevel(3);
        board[3][3].setDome(true);

        //set available boxes
        currentWorker.setBoxesToBuild(player_1.setWorkerBoxesToBuild(currentWorker));

        Exception thrown = null;

        //invoke build() method
        try {
            player_1.build(currentWorker, board[3][3]);
        } catch (InvalidBuildingException e) {
            thrown = e;
        }

        assertTrue(board[3][3].isDome());
        assertNotNull(thrown);

    }

}