//To do: take in binary number as String
import java.util.Scanner;
import java.math.BigInteger;
import java.lang.Math;
class binary{
    //Uses recursion to check if the input was a binary number
    static boolean isBinary(long input){
	if(input%10 ==1 && input%10 == 0)
	    isBinary(input/10);
	else if(input%10 !=1 && input%10 != 0)
	    return false;
	else if (input/10 == 1)
	    return true;
	return true;
    }
    //Uses the BigInteger class to account for large decimal numbers
    static BigInteger conBinary(long input){
	long temp = input;
	int place = 0;
	BigInteger sum = new BigInteger("0");
	BigInteger added= new BigInteger("2");
	while(temp != 0){
	    if(temp % 10 == 1){
		sum = sum.add(added.pow(place));
	    }
	place++;
        temp/=10;
	}
	return sum;
	}
    public static void main(String[] args){
	long binary;
	Scanner scanner = new Scanner(System.in);
	while(true){
	System.out.println("Enter a binary number: ");
	while(true){
	    if(!scanner.hasNextLong()){
	    System.out.println("Not a binary number! Please enter a binary number: ");
	    scanner.next();
	    }
	    else {
		binary = scanner.nextLong();
		if(isBinary(binary))
		    break;
		System.out.println("Not a binary number! Enter a binary number: ");
	    }
	}
	System.out.println(binary + " in decimal is " + conBinary(binary)); 
	System.out.println("Would you like to convert another number? ");
	String again = scanner.next();
	if(again.indexOf("y") == -1)
	    break;
	}
    }

    
}


