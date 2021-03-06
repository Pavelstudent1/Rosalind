package homework;

import java.io.File;
import java.io.FileNotFoundException;
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
		
		File file = new File(System.getProperty("user.dir") + "/files/ba8b_big.txt");
		Data data = loadData(file);
		
		double dis = distortion(data);
		System.out.println(String.format("%.3f", dis));
	}

	public static double distortion(Data data) {
		
		double result = 0.0;
		
		for (float[] point : data.points) {
			double d = minDistance(point, data.centers);
			result += Math.pow(d, 2);
		}
		
		result *= (1.0 / data.points.size());
		
		return result;
	}
	
	public static double minDistance(float[] point, List<float[]> centers) {
		double min = Double.MAX_VALUE;
		
		for (float[] center : centers) {
			double value = calcEuclidianDistance(point, center);
			
			if (value < min){
				min = value;
			}
		}
		
		return min;
	}

	public static double calcEuclidianDistance(float[] point, float[] center) {
		double value = 0.0;
		
		for (int i = 0; i < center.length; i++) {
			value += Math.pow((point[i] - center[i]), 2);
		}
		value = Math.sqrt(value);
		
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
		
		scan.close();
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
