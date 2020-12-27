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
			System.out.println("1 -> Eðitim ve test");
			System.out.println("2 -> Tekli test");
			System.out.println("3 -> Çýkýþ \n\n");
			System.out.print("Seçim -> ");
			choose = in.nextInt();

			switch(choose) {
				case 1:
					System.out.print("Ara katman nöron sayýsýný giriniz -> ");
					hiddenLayerNeuronSize = in.nextInt();
					System.out.print("Öðrenme katsayýsýný giriniz -> ");
					learningRate = in.nextDouble();
					System.out.print("Hata deðerini giriniz -> ");
					threshold = in.nextDouble();
					System.out.print("Momentum giriniz (Eðer momentum kullanýlmayacak ise -1 girin.) -> ");
					momentum = in.nextDouble();
					System.out.print("Epoch sayýsýný giriniz -> ");
					epoch = in.nextInt();
					if(momentum != -1) 
						nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch).useMomentum(momentum);
					else
						nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch);
					
					nn = new NN(hiddenLayerNeuronSize,learningRate,threshold,epoch).useMomentum(momentum);
					nn.CreateDataset();
					nn.TrainNeuralNetwork();
					System.out.println("Test için 3 adet deðer girin.");
					System.out.println("1. deðer -> ");
					double x = in.nextDouble();
					System.out.println("2. deðer -> ");
					double y = in.nextDouble();
					System.out.println("3. deðer -> ");
					double z = in.nextDouble();
					
					double[] result = nn.TestNeuralNetwork(x, y, z);
					System.out.println("Test sonucu -> " + result[0]);
					break;
					
				
				case 2:
					System.out.println("Test için 3 adet deðer girin.");
					System.out.println("1. deðer -> ");
					double x_test = in.nextDouble();
					System.out.println("2. deðer -> ");
					double y_test = in.nextDouble();
					System.out.println("3. deðer -> ");
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
