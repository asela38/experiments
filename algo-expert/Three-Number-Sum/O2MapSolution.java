import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;

class Program {
  public static ArrayList<Integer[]> threeNumberSum(int[] array, int targetSum) {
  	int size = array.length;
		
		// Holder to retrun data
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		
		// If data is pre sorted then no need to sort again
		Arrays.sort(array);
		
		// To stop searching same sums we can store all combinations
		HashMap<Integer, List<Integer[]>> cache = new HashMap<>();
		for(int i = 0; i < size - 1 ; i++) {
			for(int j = i + 1; j < size; j++) {
				int first = array[i];
				int second = array[j];
				cache.compute( first + second , (k,v) -> {
					List<Integer[]> il = v;
					if(il == null) {
						il = new ArrayList<>();
					}
					il.add(new Integer[]{first, second});
					return il;
				});
			}
		}
		
		HashSet<String> set = new HashSet<String>();
		for(int i = 0 ; i < size; i++ ) {
			Integer current = array[i];
			List<Integer[]> values = cache.get(targetSum - current);
			if(values != null) {
				for(Integer[] value : values) {
					if(value != null) {
						if(value[0] != current && value[1] != current) {
							Integer[] result;

							if(current < value[0])
								result = new Integer[]{current, value[0], value[1]};
							else if(current < value[1])
								result = new Integer[]{value[0], current, value[1]};
							else
								result = new Integer[]{value[0], value[1], current};

							String hash = String.format("%d|%d|%d", result[0], result[1], result[2]);
							if(!set.contains(hash)) {
								set.add(hash);
								list.add(result);
							}		
						}
					}
				}
			}
		}
		list.forEach(k -> System.out.println(Arrays.toString(k)));
		
		Collections.sort(list, (a,b) -> {
			  for(int i = 0; i < 3; i++) {
					int result = Integer.compare(a[i], b[i]);
					if(result != 0) {
						return result;
					}
				}
			  return 0;
		});
		return list;
  }
}


