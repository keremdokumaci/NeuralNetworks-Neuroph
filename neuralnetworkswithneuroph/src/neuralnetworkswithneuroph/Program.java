package neuralnetworkswithneuroph;

import java.util.Locale;
import java.util.Scanner;

import org.neuroph.core.data.DataSet;

public class Program {

	public static void main(String[] args) throws Exception {

		NN nn;
		
		int choose = 0;
		
		Scanner in = new Scanner(System.in);
		in.useLocale(Locale.US);

		int hiddenLayerNeuronSize,epoch = 0;
		double learningRate,threshold,momentum = 0;
		do 
		{
			System.out.println("1 -> E�itim ve test");
			System.out.println("2 -> Tekli test");
			System.out.println("3 -> ��k�� \n\n");
			System.out.print("Se�im -> ");
			choose = in.nextInt();

			switch(choose) {
				case 1:
					System.out.print("Ara katman n�ron say�s�n� giriniz -> ");
					hiddenLayerNeuronSize = in.nextInt();
					System.out.print("��renme katsay�s�n� giriniz -> ");
					learningRate = in.nextDouble();
					System.out.print("Hata de�erini giriniz -> ");
					threshold = in.nextDouble();
					System.out.print("Momentum giriniz (E�er momentum kullan�lmayacak ise -1 girin.) -> ");
					momentum = in.nextDouble();
					System.out.print("Epoch say�s�n� giriniz -> ");
					epoch = in.nextInt();
					if(momentum != -1) 
						nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch).useMomentum(momentum);
					else
						nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch);
					
					nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch).useMomentum(momentum);
					nn.CreateDataset();
					nn.TrainNeuralNetwork();
					System.out.println("Test i�in 3 adet de�er girin.");
					System.out.println("1. de�er -> ");
					double x = in.nextDouble();
					System.out.println("2. de�er -> ");
					double y = in.nextDouble();
					System.out.println("3. de�er -> ");
					double z = in.nextDouble();
					
					double[] result = nn.TestNeuralNetwork(x, y, z);
					System.out.println("Test sonucu -> " + result[0]);
					break;
					
				
				case 2:
					System.out.println("Test i�in 3 adet de�er girin.");
					System.out.println("1. de�er -> ");
					double x_test = in.nextDouble();
					System.out.println("2. de�er -> ");
					double y_test = in.nextDouble();
					System.out.println("3. de�er -> ");
					double z_test = in.nextDouble();
					
					nn = new NN();
					double[] result_test = nn.TestNeuralNetwork(x_test,y_test,z_test);
					System.out.println("Test sonucu -> " + result_test[0]);
					break;
				
				case 3:
					break;
					
				default:
					break;
					
			}
		}
		while(choose != 3);
		
	}

}
