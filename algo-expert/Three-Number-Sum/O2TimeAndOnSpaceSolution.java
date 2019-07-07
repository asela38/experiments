import java.util.ArrayList;
import java.util.Arrays;

class Program {
  public static ArrayList<Integer[]> threeNumberSum(int[] array, int targetSum) {
  	int size = array.length;
		
		// Holder to retrun data
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		
		// If data is pre sorted then no need to sort again
		Arrays.sort(array);
		
		for (
			int left = 0, mid = 1, right = size - 1 ; 
			left < size - 2 ; 
			left++, mid = left + 1, right = size -1
		) {
			while(true) {
				int sum = sum(array, left, mid, right);
		//		print(array, left, mid, right);
				if(sum == targetSum) {
					list.add(triplet(array, left, mid, right));
					mid++;
					right--;
				} else if ( sum > targetSum ) {
					right--;
				} else if (sum < targetSum ) {
					mid++;
				}
				if(mid >= right) break;
			}
		}
		
	//	list.forEach(k -> System.out.println(Arrays.toString(k)));					
		return list;
  }
	
	private static int sum(int[] array, int i, int j, int k) {
		return array[i] + array[j] + array[k];
	}
	
	private static Integer[] triplet(int[] array, int i, int j, int k) {
		return new Integer[]{ array[i] , array[j] , array[k] };
	}
	
	private static void print(int[] array, int i, int j, int k) {
		System.out.printf("%s, %s, %s%n", array[i] , array[j] , array[k]);
	}
}
