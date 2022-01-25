import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author : Aaron Saijan
 * 
 * @purpose : Calculate the cheapest cost of traveling from one trading 
 * post to another using a canoe. Also find the the optimal cost of this. 
 * 
 *
 */
public class VirtualCanoeTrip {
	
	public static void main(String[]args) throws FileNotFoundException{
		
		//reading in file
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		
		//Matrix size 
		int size = Integer.parseInt(scan.nextLine());
		
		int [][] priceMatrix = new int [size] [size];
		
		//build cost matrix
		for(int i = 0; i < size - 1; i++) {
			
			for(int row = i + 1; row < size; row++) {
				
				int price = scan.nextInt();
				
				priceMatrix [i] [row] = price;
			}
		}
		
		int [][] optimalMatrix = new int [size][size];
		
		String [][] path = new String [size][size];
		
		//build optimal price matrix 
		for (int diag = 1; diag < size; diag++) {
			
			for (int row = 0, colu = row + diag; colu < size; row++, colu++){
				
				int [] optimalCost = findOptimalCost (row, colu, priceMatrix, optimalMatrix);
				optimalMatrix[row][colu] = optimalCost[0];
				if(optimalCost[1] != -1) {
					
					path[row][colu] = row + " --> " + optimalCost[1] + " --> " + colu;

				}
				else {
					
					path[row][colu] = row + " --> " + colu;
					
				}
			}
		}
		
		//prints results
		print(optimalMatrix, path, size);
		
	}
	
	//Calculates the minimized set of possible routes, cost, and finds
	//which ones is cheapest
	private static int [] findOptimalCost(int start, int stop, int [][] priceMatrix, 
			int [][] optimalMatrix) {
		
		int [] output = {-1, -1};
		int dif = stop - start;
		
		int [] posMatrix = new int [dif];
		if(dif == 1) {
			
			output[0] = priceMatrix[start][stop];
			return output;
		}
		else {
			posMatrix[0] = priceMatrix[start][stop];
			
			for(int i = start + 1, opNum = 1; i < stop; i++, opNum++) {
				posMatrix[opNum] = optimalMatrix[start][i] + optimalMatrix[i][stop];
			}
		}
		
		output[0] = posMatrix[0];
		for(int i = 1; i < dif; i++) {
			if(posMatrix[i] < output[0]) {
				output[0] = posMatrix[i];
				output [1] = i;
			}
		}
				return output;
	}
	
	//Print Method to output results
	public static void print(int [][] optimalMatrix, String [][] path, int size) {
		System.out.println("Optimal Travel Via Canoe\n-----------------");
		System.out.println("Route: \tCost: \tPath: ");
		for(int row = 0; row < size; row++) {
			for(int colu = row + 1; colu < size; colu++) {
			
				System.out.println("|" + row + ", " + colu + "|" + "\t" 
						+ optimalMatrix[row][colu] + "\t"
						+ path[row][colu]);
			}
		}
		
	}
	
}
