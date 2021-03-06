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
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;


public class NN {
	private static final File datasetFile = new File(NN.class.getResource("Dataset").getPath());
	private double trainingSize = 0.7;
	private double testSize = 0.3;
	
	private int hiddenLayerNeuronSize;
	private double learningRate;
	private double threshold;
	private int epoch;
	
	private DataSet dataset;
	private DataSet trainDataset;
	private DataSet testDataset;

	private MultiLayerPerceptron multiLayerPerceptron;
	private MomentumBackpropagation backProp;
	
	private boolean useMomentum = false;
	
	public NN() {
		
	}
	public NN(int hiddenLayerNeuron,double learningRate, double threshold, int epoch) throws Exception
	{
		if(trainingSize > 0.9) {
			throw new Exception("Training dataset size must be under 0.9");
		}
		
		this.epoch = epoch;
		this.threshold = threshold;
		this.learningRate = learningRate;
		this.hiddenLayerNeuronSize = hiddenLayerNeuron;
		this.backProp = new MomentumBackpropagation();
		
		this.backProp.setLearningRate(this.learningRate);
		this.backProp.setMaxError(this.threshold);
		this.backProp.setMaxIterations(this.epoch);
	}
	
	public NN useMomentum(double momentum) {
		this.backProp.setMomentum(momentum);
		return this;
	}
	
	public void CreateDataset() throws FileNotFoundException {
		Scanner file = new Scanner(this.datasetFile);
				
		dataset = new DataSet(3,3);
		while(file.hasNext()) {
			double[] inputs = new double[3];
			double[] outputs = new double[3];
			
			for(int i = 0; i<3; i++) {
				inputs[i] = Double.parseDouble(file.next());
			}
			for(int i = 0; i<3; i++) {
				outputs[i] = Double.parseDouble(file.next());
			}
			
			dataset.add(new DataSetRow(inputs,outputs));
		}
		file.close();
		
		DataSet[] datasets = TrainTestSplit();
		trainDataset = datasets[0];
		testDataset = datasets[1];
		
	}
	
	private DataSet[] TrainTestSplit()
	{
		List<Integer> testRowNumbers = new ArrayList<Integer>();
		
		Random random = new Random();
		
		for(int i=0;i<300;i++) {
			int rnd = random.nextInt(1000);

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
		
		DataSet test = new DataSet(3,3);
		DataSet train = new DataSet(3,3);
		
		
		for(int i = 0; i<1000; i++) {
			if(testRowNumbers.contains(i)) 
				test.add(dataset.get(i));
			else
				train.add(dataset.get(i));
			
		}

		return new DataSet[] {train, test};
		
	}
	
	public void TrainNeuralNetwork()
	{
		this.multiLayerPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,3,this.hiddenLayerNeuronSize,3);
		this.multiLayerPerceptron.setLearningRule(this.backProp);
		this.multiLayerPerceptron.learn(this.trainDataset);
		this.multiLayerPerceptron.save("trained_network.nnet");
		
		System.out.println("Training is done !");
	}
	public double GetTrainingError()
	{
		return this.backProp.getTotalNetworkError();
	}
	
	private double MSE(double[] predicted, double[] target)
	{
		double singleError = 0;
		for(int i = 0;i<3;i++) {
			singleError += Math.pow((predicted[i] - target[i]), 2);
		}
			

		return singleError;
	}
	
	public double GetTestError() 
	{
		NeuralNetwork nn = NeuralNetwork.createFromFile("trained_network.nnet");
		
		double totalError = 0;

		for(DataSetRow row : this.testDataset.getRows()) {
			nn.setInput(row.getInput());
			nn.calculate();
			totalError += MSE(nn.getOutput(),row.getDesiredOutput());
		}
		
		return totalError/this.testDataset.size();
	}
	public double[] TestNeuralNetwork(double x, double y, double z) {
		NeuralNetwork nn = NeuralNetwork.createFromFile("trained_network.nnet");

		nn.setInput(x,y,z);
		nn.calculate();
		return nn.getOutput();
	}
	
	
}
