package neuralnetworkswithneuroph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;


public class NN {
	private static final File datasetFile = new File(NN.class.getResource("Dataset").getPath());
	private double trainingSize = 0.7;
	private double testSize = 0.3;
	private double momentum;
	
	public DataSet dataset;
	
	private NeuralNetwork neuralNetwork;
	
	public NN(int hiddenLayerNeuron,double learningRate, double threshold, int epoch) throws Exception
	{
		if(trainingSize > 0.9) {
			throw new Exception("Training dataset size must be under 0.9");
		}
		
	}
	
	public void useMomentum(double momentum) {
		this.momentum = momentum;
	}
	
	public void CreateDataset() throws FileNotFoundException {
		Scanner file = new Scanner(this.datasetFile);
		dataset = new DataSet(3,1);
		while(file.hasNextDouble()) {
			double[] inputs = new double[3];
			for(int i = 0; i<3; i++) {
				inputs[i] = file.nextDouble();
			}
			dataset.add(new DataSetRow(inputs,new double[] {file.nextDouble()}));
		}
		
	}
	
	public DataSet[] TrainTestSplit()
	{
		List<Integer> testRowNumbers = new ArrayList<Integer>();
		
		Random random = new Random();
		
		for(int i=0;i<300;i++) {
			int rnd = random.nextInt(1000);
			System.out.println(rnd);
			if(testRowNumbers.contains(rnd) == false)
				testRowNumbers.add(rnd);
			else {
				while(testRowNumbers.contains(rnd) == true) {
					rnd = random.nextInt(1000);
					if(testRowNumbers.contains(rnd) == false) {
						testRowNumbers.add(rnd);					
						break;
					}
				}
			}
		}
		
		DataSet test = new DataSet(3,1);
		DataSet train = new DataSet(3,1);
		
		
		for(int i = 0; i<1000; i++) {
			if(testRowNumbers.contains(i)) 
				test.add(dataset.get(i));
			else
				train.add(dataset.get(i));
			
		}

		return new DataSet[] {train, test};
		
	}
	
	
}
