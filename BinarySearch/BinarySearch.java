import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch
{

	public static void main(String[] args)
	{
		
		//int[] arrayToSearch = {1,2,3,4,5,6,7,8,9,10,20,22,30,29,40,50,11,17};
		int[] arrayToSearch = {'a','c','b','f','j','k','y','t'};

		Arrays.sort(arrayToSearch);
		System.out.println(Arrays.toString(arrayToSearch));
		
		Scanner input = new Scanner(System.in);

	//	int intToSearch = input.nextInt();
	
		int intToSearch = input.next().charAt(0);
		binarySearch(arrayToSearch,intToSearch);
	}

	public static int binarySearch(int[] arrayToSearch,int intToSearch)
	{
		int startIndex = 0;
		int endIndex = 0;
		int previousEndIndex = 0;
		endIndex = arrayToSearch.length - 1;
		previousEndIndex = endIndex - 1;
		int count = 0;
		while(true)
		{

			//System.out.println("EndIndex: " + endIndex + "\nStartIndex: "+ startIndex);
 			count += 1;
			if(count >= arrayToSearch.length){break;}	
			if(arrayToSearch[endIndex] == intToSearch)
			{
				printIndex(endIndex,count);
				return endIndex;
			}
			if(arrayToSearch[startIndex] == intToSearch)
			{
				printIndex(startIndex,count);
				return startIndex;
			}			
			if(arrayToSearch[previousEndIndex] == intToSearch)
			{
				printIndex(previousEndIndex,count);
				return previousEndIndex;
			}
		
			if(arrayToSearch[endIndex] < intToSearch)
			{
				int temp = endIndex;	
				startIndex = endIndex + 1;
				endIndex = previousEndIndex;
				previousEndIndex = endIndex;
				continue;
			}
			if(arrayToSearch[endIndex] > intToSearch && arrayToSearch[startIndex] < intToSearch)
			{
				previousEndIndex = endIndex - 1;
				endIndex = endIndex % 2 == 0 ? (endIndex / 2) : (endIndex - 1)/2; 
				continue;
			}
		
		}
		return 0;
	}
	public static void printIndex(int index, int count)
	{
		System.out.println("Found Index: " + Integer.toString(index));
		System.out.println("The search took: " + Integer.toString(count) +" Iterations to complete");
	}
}
