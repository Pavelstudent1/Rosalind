package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BA8B {
	
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
		
		for (float[] point : data.points) {
			double d = minDistance(point, data.centers);
			result += Math.pow(d, 2);
		}
		
		result *= (1.0 / data.points.size());
		
		return result;
	}
	
	private static double minDistance(float[] point, List<float[]> centers) {
		double min = Double.MAX_VALUE;
		
		for (float[] center : centers) {
			double value = 0.0;
			for (int i = 0; i < center.length; i++) {
				value += Math.pow((point[i] - center[i]), 2);
			}
			value = Math.sqrt(value);
			
			if (value < min){
				min = value;
			}
		}
		
		return min;
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
		data.k = scan.nextInt();
		data.m = scan.nextInt();
		
		skipToFloat(scan);
		
		data.centers = new ArrayList<>();
		fillFloats(data.k, data.centers, scan);
		
		skipToFloat(scan);
		
		data.points = new ArrayList<>();
		fillFloats(data.m, data.points, scan);
		
		return data;
	}

	private static void skipToFloat(Scanner scan) {
		while(!scan.hasNextFloat()){
			scan.next();
		}
	}

	private static void fillFloats(int dimention, List<float[]> data, Scanner scan) {
		while(scan.hasNextFloat()){
			float[] point = new float[dimention];
			for (int i = 0; i < point.length; i++) {
				point[i] = scan.nextFloat();
			}
			data.add(point);
		}
	}
}
