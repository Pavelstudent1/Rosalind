package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class BA8C {

	private static int iterations = 1;

	private static class Data {
		int k;
		int m;
		List<Point> points;
	}

	private static class Point {
		float[] coord;

		public Point(float[] coord) {
			this.coord = coord;
		}

		public Point(Point p) {
			this.coord = p.coord;
		}
		
		public Point(int demention){
			this.coord = new float[demention];
		}

		@Override
		public int hashCode() {
			return (int) ((coord[0] + coord[coord.length - 1]) * 1000);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (obj == this) return false;

			float[] other = ((Point) obj).coord;
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

	public static void main(String[] args) {

		File file = new File(System.getProperty("user.dir") + "/files/ba8c_big.txt");
		Data data = loadData(file);

		Map<Point, List<Point>> clusters = new HashMap<>();
		createAndChooseCenters(data.points, data.k, clusters);
		
		Set<Point> prev = null;
		do {
			centersToClusters(data.points, clusters);
			prev = clusters.keySet();
			clusters = clustersToCenters(clusters);
			print(clusters);

		} while (isCentersChange(prev, clusters.keySet()));
		
		System.out.println("END");
	}

	private static boolean isCentersChange(Set<Point> prev, Set<Point> cur) {

		for (Point point : prev) {
			if (!cur.contains(point))
				return true;
		}

		return false;
	}

	private static Map<Point, List<Point>> clustersToCenters(Map<Point, List<Point>> clusters) {

		Map<Point, List<Point>> newMap = new HashMap<>();
		
		for (List<Point> cluster : clusters.values()) {
			Point center = calcCenterOfGravity(cluster);
			newMap.put(center, new ArrayList<>());
		}
		
		return newMap;
	}

	private static Point calcCenterOfGravity(List<Point> cluster) {
		
		int demention = cluster.get(0).coord.length;
		Point gravity = new Point(demention);

		for (int i = 0; i < demention; i++) {
			for (Point p : cluster) {			//calculate sum by i coordinate 
				gravity.coord[i] += p.coord[i];
			}
			gravity.coord[i] /= cluster.size();	//calculate avg. by i coordinate
		}
		
		return gravity;
	}

	private static void centersToClusters(List<Point> points, Map<Point, List<Point>> clusters) {

		for (Point p : points) {
			double minDist = Double.MAX_VALUE;
			Point minPoint = null;
			for (Point c : clusters.keySet()) {
				double dist = BA8B.calcEuclidianDistance(p.coord, c.coord);

				if (dist < minDist) {
					minDist = dist;
					minPoint = c;
				}
			}

			clusters.get(minPoint).add(new Point(p));
		}

	}

	private static void print(Map<Point, List<Point>> clusters) {

		System.out.println("Iteration=" + iterations++);
		int i = 0;
		for (Point p : clusters.keySet()) {
			System.out.println("Center #" + i + " " + p);
			++i;
		}
		System.out.println("-------------------------");
	}

	private static void createAndChooseCenters(
			List<Point> points, int nCenters, Map<Point, List<Point>> centers) {

		for (int i = 0; i < nCenters; i++) {
			centers.put(points.get(i), new ArrayList<>());
		}
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

		data.points = new ArrayList<>();
		while (!scan.hasNext("\\s|Output")) {
			String[] line = scan.nextLine().split(" ");
			float[] f = new float[data.m];
			for (int i = 0; i < data.m; i++) {
				f[i] = Float.valueOf(line[i]);
			}
			data.points.add(new Point(f));
		}

		scan.close();
		return data;
	}

}
