package dataRecording;

import pacman.controllers.*;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

/**
 * This controller, collects data from any controller.
 */
public class DataCollectorController extends Controller<MOVE>{

	private Controller<MOVE> controller;

	public DataCollectorController(Controller<MOVE> input){
		this.controller = input;
	}

	@Override
	public MOVE getMove(Game game, long dueTime) {
		MOVE move = this.controller.getMove(game, dueTime);

		DataTuple data = new DataTuple(game, move);

		DataSaverLoader.SavePacManData(data);
		return move;
	}

}



/*
 * Clase original (por si en algún momento es necesario)
 *
/**
 * The DataCollectorHumanController class is used to collect training data from playing PacMan.
 * Data about game state and what MOVE chosen is saved every time getMove is called.
 * @author andershh
 *
 *
public class DataCollectorController extends HumanController{

	public DataCollectorController(KeyBoardInput input){
		super(input);
	}

	@Override
	public MOVE getMove(Game game, long dueTime) {
		MOVE move = super.getMove(game, dueTime);

		DataTuple data = new DataTuple(game, move);

		DataSaverLoader.SavePacManData(data);
		return move;
	}

}

*/