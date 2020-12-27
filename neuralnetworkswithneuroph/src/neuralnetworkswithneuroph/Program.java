package neuralnetworkswithneuroph;

import org.neuroph.core.data.DataSet;

public class Program {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		NN nn = new NN(5,4,3,2);
		nn.CreateDataset();
		DataSet[] train_test_dataset = new DataSet[2];
		train_test_dataset = nn.TrainTestSplit();
		System.out.println("Train datasaet -> " + train_test_dataset[1].getItems().size());
	}

}
