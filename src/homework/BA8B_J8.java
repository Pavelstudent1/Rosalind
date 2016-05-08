package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class BA8B_J8 {

	private static class Data {
		int k;
		int m;
		List<float[]> centers;
		List<float[]> points;
	}
	
	public static void main(String[] args) {
		
		File file = new File(System.getProperty("user.dir") + "/files/ba8b_small.txt");
		Data data = loadData(file);
		
		double dis = distortion(data);
		System.out.println(String.format("%.3f", dis));
	}
	
	private static double distortion(Data data) {

		double result = 0.0;
		
		calcEuclidianDistanceStream(
				new double[] {5,5}, 
				new double[] {0,1});
		data.points
			.stream()
			.forEach(p -> System.out.println(p));
		
		
		
		
		return 0;
	}
	
	
	public static double calcEuclidianDistanceStream(double[] point, double[] center){
		double value = 0.0;
		
		
		return value;
	}
	
	
	
	
	
	
	
	
	

	private static Data loadData(File file) {
		Data data = new Data();
		
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scan.nextLine();
		String[] km = scan.nextLine().split(" ");
		data.k = Integer.valueOf(km[0]);
		data.m = Integer.valueOf(km[1]);
		
		data.centers = new ArrayList<>();
		fillFloats(data.k, data.centers, scan);
		
		data.points = new ArrayList<>();
		fillFloats(data.m, data.points, scan);
		
		return data;
	}

	private static void fillFloats(int dimention, List<float[]> data, Scanner scan) {
		
		while(!scan.hasNext("--*|Output")){
			float[] point = new float[dimention];
			for (int i = 0; i < point.length; i++) {
				point[i] = Float.valueOf(scan.next());
			}
			data.add(point);
		}
		scan.next();
	}
}
