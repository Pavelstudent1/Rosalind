package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class BA8B_J8 {

	private static class Data {
		int k;
		int m;
		List<Point> points;
		List<Point> centers;
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
				data.points.get(0), 
				data.centers.get(0));
		data.points
			.stream()
			.forEach(p -> System.out.println(p));
		
		
		
		
		return 0;
	}
	
	
	public static double calcEuclidianDistanceStream(Point p, Point p2){
		double value = 0.0;
		
		DoubleStream d = Arrays.stream(p.coord);
		DoubleStream d2 = Arrays.stream(p2.coord);
//		.forEach(d -> d + Arrays.stream(p2));
		
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
		while(!scan.hasNext("[\\--]+")){
			String[] line = scan.nextLine().split(" ");
			double[] f = new double[data.m];
			for (int i = 0; i < data.m; i++) {
				f[i] = Float.valueOf(line[i]);
			}
			data.centers.add(new Point(f));
		}
		
		scan.nextLine();
		data.points = new ArrayList<>();
		while (!scan.hasNext("\\s|Output")) {
			String[] line = scan.nextLine().split(" ");
			double[] f = new double[data.m];
			for (int i = 0; i < data.m; i++) {
				f[i] = Float.valueOf(line[i]);
			}
			data.points.add(new Point(f));
		}

		scan.close();
		return data;
	}
	
	private static class Point {
		double[] coord;

		public Point(double[] coord) {
			this.coord = coord;
		}

		public Point(Point p) {
			this.coord = p.coord;
		}
		
		public Point(int demention){
			this.coord = new double[demention];
		}

		@Override
		public int hashCode() {
			return (int) ((coord[0] + coord[coord.length - 1]) * 1000);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (obj == this) return false;

			double[] other = ((Point) obj).coord;
			for (int i = 0; i < other.length; i++) {
				if (!String.format("%.3f", coord[i]).equals(String.format("%.3f", other[i]))) {
					return false;
				}
			}

			return true;
		}

		@Override
		public String toString() {
			return Arrays.toString(this.coord);
		}
	}
}
