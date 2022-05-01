import java.util.*;
import java.math.*;
public class Rsa
{
	//final static String[] commands = {"Quit","Encrypt","Decrypt"};
	//Declaration of global scope variables
	//final static List<String> commandList = Collections.unmodifiableList(Arrays.asList({"Quit","Encrypt","Decrypt"})); 
	final static Scanner INPUT = new Scanner(System.in);
	final static int ASCIIOFFSET = 64;
	static boolean running = true;
	final static String ESCAPESEQ = " "; // change this to change what characters apper between each encoded one

	public static void main(String[] args)
	{
		
		while(running)
		{
			showMenu();
		}
		
		/*BigInteger base,exp,mod;
		base = new BigInteger("60");
		exp = new BigInteger("43");
		mod = new BigInteger("77");

		System.out.println(base.pow(43).remainder(mod));
		*///System.out.println(power(60,43) % 77 );
	}
	//used to show a menu to the user
	public static void showMenu()
	{
		clearScreen();
		System.out.println("Waza_Crypt Menu");
	
		System.out.printf("%s %s %s %s",
				"Enter the following commands \n",
				"=> Q to quit \n",
				"=> E to encrypt a message \n",
				"=> D to decrypt a message \n"
				);
		System.out.print("=> ");

		String userInput = INPUT.next();

			//we do cool stuff here
		switch(userInput.toLowerCase())	
		{
			case "q":
				running = false;
				return;
			
			case "e":
				System.out.print("Please enter any two prime numbers => ");
				int p = INPUT.nextInt();
				int q = INPUT.nextInt();
				System.out.println("Please enter the message you wish to encrypt =>");
				INPUT.nextLine();
				String message = INPUT.nextLine();
				String msg = encryptMessage(message,p,q);
				System.out.printf("You top Seceret message is \n ----- \n %s \n -----\n => Enter to continue....",msg);
				INPUT.nextLine();
				INPUT.nextLine();
				break;
			case "d":
				System.out.print("\nPlease Enter two prime numbers p and q => ");
				int p2 = INPUT.nextInt();
				int q2 = INPUT.nextInt();
				//System.out.print("\nPlease Enter the Coprime of (p-1)(q-1) i.e your e value => ");
				//int e2 = INPUT.nextInt();
				
				INPUT.nextLine();
				System.out.printf("\nPlease Enter your super Secret message use '%s' between each encoding => \n",ESCAPESEQ);
				String in = INPUT.nextLine();
				String msg2 = decryptMessage(in,p2,q2);
				System.out.printf("\nWow!! now we a done Beep Boop, Your super secret message is\n %s \n press enter to continue",msg2);
				INPUT.nextLine();
				INPUT.nextLine();
				break;
			default:
				System.out.printf("The command %s you entered is not a valid command =( \n Continue... \n ",userInput);
				INPUT.nextLine();
				INPUT.nextLine();
				showMenu();				
		}
	
	}
	//clears the console to make it cleaner
	public static void clearScreen()
	{
		System.out.println("\033[H\033[2J");
		System.out.flush();
       	}
	//Encrypts the message with RSA	
	public static String encryptMessage(String message , int p , int q)
	{	
		message = message.toUpperCase();
		int n = p * q;
		int tn = (p-1) * (q - 1);
		int e = 0;
		/*for(int i = 2; i < tn;i++)
		{
			if(getGcd(i,tn) == 1)
			{
				e = i;
				break;
			}
		}*/
		e = getCoprime(tn);
		System.out.println("\nBip Bop doing some computer magic beep bop :) \n");
		System.out.println("-------");
		System.out.printf("Your public key i.e (e , n) is (%d , %d)\n",e,n);
		System.out.println("-------");
		System.out.println("\nStarting to generate your top secret message boop beep \n");
		String encryptedMsg = "";
		int length = message.length();
		for(int i = 0; i < length;i++)
		{	
			if((i+1 % length/2 == 0) && i > 0)
			{
				System.out.println("50% done beep");
			}
			char letter = message.charAt(i);
			//char secretLetter = '';
			if(letter == ' ')
			{
				encryptedMsg += " ";
			       	continue;	
			}
			if(Character.isLetter(letter))
			{
				encryptedMsg += Integer.toString(encryptLetter(letter,n,e)) + ESCAPESEQ;
				continue;	
			}
			encryptedMsg += letter;
			
		}
		
		return encryptedMsg;
	}
	//Encrypts a single letter
	public static int encryptLetter(char letter,int n,int e)
	{
		int charNumber = letter - ASCIIOFFSET; // this normalized the char from a range of 65-90 to 1-26. 65-90 is the ascii encoding for decimal
		return (int)(Math.pow(charNumber,e) % n);

	}
	//Decrypts a message with RSA
	public static String decryptMessage(String message , int p , int q  /*int e*/)
	{
		int n = p * q;

		int tn = (p-1) * (q-1);
		int e = getCoprime(tn);
		String msg = "";
		int length = message.length();
		//int previousEscapeIndex = 0;
		int startIndex = 0;
		int endCount = 0;
		boolean decrypting = true;

		System.out.println("\nBeep Boop decrypting your supper secret message ;) but this will take a while =(.\nSo please wait..");
		int count = 0;
		while(decrypting && count < message.length())
		{
			int endIndex = message.indexOf(ESCAPESEQ,startIndex);
			endCount += (endIndex == -1)? 1 : 0;
			if(endCount >= 2)
			{
				break;
			}
			
			endIndex = (endIndex == -1)?length:endIndex; 
			//if(startIndex < 0 || endIndex < 0){break;}
			//System.out.printf("\n %d %d\n",startIndex,endIndex);
			try{
				String extractedString = message.substring(startIndex,endIndex);
				String cleanedExtraction = "";
			
				for(int i = 0; i < extractedString.length();i++)
				{
					if(Character.isDigit(extractedString.charAt(i)))
					{
						cleanedExtraction += Character.toString(extractedString.charAt(i));
						continue;
					}
					msg += Character.toString(extractedString.charAt(i));
				}
				//System.out.println(cleanedExtraction.trim() + "-");//For Debugging	
				if(cleanedExtraction != ""){
					//int numericalLetter = Integer.parseInt(cleanedExtraction);		
			
					int d = getD(e,tn,0);
					//System.out.println(d);
					msg += decryptCharacter(cleanedExtraction.trim(),n,d);
				}
				count += 1;
				startIndex = endIndex + ESCAPESEQ.length();
				if(ESCAPESEQ.length() == 1)
				{
					if(Character.toString(message.charAt(startIndex)) == ESCAPESEQ)
					{
						msg += ESCAPESEQ;
					}
				}
				//previousEscapeIndex = endIndex;
			}
			catch(Exception ex)
			{
				System.out.println("Something when wrong while Decrypting");
				decrypting = false;
			}
		}	
		return msg;
	}
	//Decrypt a single letter
	public static String decryptCharacter(String msg, int n, int d)
	{
		//System.out.printf("\n %d %d %d\n",msg,d,n);

		//System.out.println(Math.pow(msg,d));
		//double simExpo = simplifiedExponent(msg,d,0,n);
		//System.out.println(simExpo);
		//int num = (int)(simExpo% n);
		BigInteger base,mod;
		base = new BigInteger(msg);
		mod = new BigInteger(Integer.toString(n));
		double num = base.pow(d).remainder(mod).doubleValue();
		//System.out.printf("\n%d , %d \n",num,num+ASCIIOFFSET);
		char letter = (char)(num + ASCIIOFFSET);
		return Character.toString(letter);
	}
	//makes the exponent smaller
	public static double simplifiedExponent(int base, int exp,int count,int modulo)
	{
		count = (count == 0)? 1:count;
		//System.out.println(count);
		if( Math.pow(base,count) % modulo == 1)
		{
			
			return Math.pow(base,count-1);
		}else
		{
			return simplifiedExponent(base,exp,count+1 ,modulo);
		}
	}
	//Generates d recursevly
	public static int getD(int e, int tn,int d)
	{
		
		if( ((e*d)%tn) == 1)
		{
			return d;
		}else{
			return getD(e,tn,d+1);
		}
		
		
	}

	public static int getCoprime(int tn)
	{	
		for(int i = 2; i < tn;i++)
		{
			if(getGcd(i,tn) == 1)
			{
				return i;
			
			}
		}
		return 0;
	}
	public static int getGcd(int a,int b)
	{

		if(a == 0 || b == 0)
		{
			return 0;
		}	
		if(a == b)
		{
			return a;
		}
		if(a > b)
		{
			return getGcd(a-b,b);
		}
		return getGcd(a, b-a);
	}
	//Tried manually getting the power, dont use this
	public static double power(double base,double exp)
	{
		double res = 1;
		for(int i = 0; i < exp ; i++)
		{
			res *= base;
		}

		return res;
	}

}
