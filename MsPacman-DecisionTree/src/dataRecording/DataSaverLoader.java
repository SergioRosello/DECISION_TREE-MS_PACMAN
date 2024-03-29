package dataRecording;

import pacman.game.util.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class uses the IO class in the PacMan framework to do the actual saving/loading of
 * training data.
 * @author andershh
 *
 */
public class DataSaverLoader {
	
	private static String FileName = "trainingData.txt";
	
	public static void SavePacManData(DataTuple data)
	{
		IO.saveFile(FileName, data.getSaveString(), true);
	}
	
	public static ArrayList<DataTuple> LoadPacManData()
	{
		String data = IO.loadFile(FileName);
		String[] dataLine = data.split("\n");
		DataTuple[] dataTuples = new DataTuple[dataLine.length];
		
		for(int i = 0; i < dataLine.length; i++)
		{
			dataTuples[i] = new DataTuple(dataLine[i]);
		}
		
		return new ArrayList<DataTuple>(Arrays.asList(dataTuples));
	}
}
