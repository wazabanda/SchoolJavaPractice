import java.util.Scanner;
public class fibonacci
{
	public static void main(String args[]){
		int init = 0;
		int next = 1;
		int temp = 0;
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of values to print: ");

		int count = input.nextInt();
		System.out.println(init + "\n" + next);
		while(count > 0 ){
			temp = init + next;
			init = next;
			next = temp;
			System.out.println(temp);
			count -= 1;
		}
	}
}
