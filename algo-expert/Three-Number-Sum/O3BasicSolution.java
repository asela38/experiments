import java.util.ArrayList;
import java.util.Arrays;

class Program {
  public static ArrayList<Integer[]> threeNumberSum(int[] array, int targetSum) {
  	int size = array.length;
		
		// Holder to retrun data
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		
		// If data is pre sorted then no need to sort again
		Arrays.sort(array);
		
		for(int i = 0 ; i < size - 2 ; i++)
			for(int j = i + 1 ; j < size - 1; j++)
				for(int k = j + 1 ; k < size; k++)
						if(array[i] + array[j] + array[k] == targetSum )
							list.add(new Integer[]{array[i], array[j], array[k]});
		
							
		return list;
  }
}


