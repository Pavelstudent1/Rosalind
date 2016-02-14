package homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class BA4H {

	public static void main(String[] args) {

		int[] input = new int[] { 0, 137, 186, 323 };
		int[] input2 = new int[] { 465, 473, 998, 257, 0, 385, 664, 707, 147,
				929, 87, 450, 748, 938, 998, 768, 234, 722, 851, 113, 700, 957,
				265, 284, 250, 137, 317, 801, 128, 820, 321, 612, 956, 434,
				534, 621, 651, 129, 421, 337, 216, 699, 347, 101, 464, 601, 87,
				563, 738, 635, 386, 972, 620, 851, 948, 200, 156, 571, 551,
				522, 828, 984, 514, 378, 363, 484, 855, 869, 835, 234, 1085,
				764, 230, 885 };

		int[] input3 = new int[] { 0, 113, 114, 128, 129, 227, 242, 242, 257,
				355, 356, 370, 371, 484 };

		spectrumConvolution(input2);
	}

	private static void spectrumConvolution(int[] input) {

		// Выбор реализации Map скажется на выходном результате:
		// если выбрана HashMap, то выходные данные полностью совпадут с тестовыми по порядку вывода
		// если выбрана TreeMap, то значение 1085 будет идти не после 17, а появится в самом конце
		Arrays.sort(input);
		Map<Integer, Integer> multiplicity = new HashMap<Integer, Integer>();
		// Map<Integer, Integer> multiplicity = new TreeMap<>();

		int temp = 0;
		for (int i = 1; i < input.length; i++) {
			for (int j = 0; j < input.length; j++) {

				temp = input[i] - input[j];
				if (temp > 0) {
					Integer value = multiplicity.get(temp);
					if (value == null) {
						multiplicity.put(temp, 1);
					} else {
						multiplicity.put(temp, ++value);
					}
				} else
					break;
			}
		}

		// Convert Map to List and sort it by multiplicity value
		List<Map.Entry<Integer, Integer>> entry = 
				new ArrayList<Map.Entry<Integer, Integer>>(multiplicity.entrySet());
		Collections.sort(entry, new MultiplicityComparator());

		printInMultiplicityOrder(entry);

	}

	private static final class MultiplicityComparator implements
			Comparator<Map.Entry<Integer, Integer>> {
		// Sort order: 1 = ascending, -1 = descending
		int order = -1;

		@Override
		public int compare(Entry<Integer, Integer> o1,
				Entry<Integer, Integer> o2) {

			int first = o1.getValue(), second = o2.getValue();

			if (first > second) return order;
			if (first < second) return -order;

			return 0;
		}
	}

	private static void printInMultiplicityOrder(List<Entry<Integer, Integer>> entry) {

		for (Entry<Integer, Integer> pair : entry) {
			int key = pair.getKey(), value = pair.getValue();

			for (int i = 0; i < value; i++) {
				System.out.print(key + " ");
			}

			System.out.println("= " + value);
		}

		System.out.println();
	}

}
