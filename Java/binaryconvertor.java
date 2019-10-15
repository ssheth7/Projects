import java.util.Scanner;
import java.math.BigInteger;
import java.lang.Math;
class binary{
    static boolean isBinary(long input){
	if(input%10 ==1 && input%10 == 0)
	    isBinary(input/10);
	else if(input%10 !=1 && input%10 != 0)
	    return false;
	else if (input/10 == 1)
	    return true;
	return true;
    }
    static BigInteger conBinary(long input){
	long temp = input;
	double power;
	int place = 0;
	BigInteger sum = new BigInteger("0");
	while(temp != 0){
	    power = Math.pow(2.0,(double) temp%10);
	    BigInteger added = new BigInteger("");
	    added.valueOf((long) (power));
	    sum.add(added);
	temp/=10;
	}
	return sum;
	}
    public static void main(String[] args){
	long binary;
	Scanner scanner = new Scanner(System.in);
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
	System.out.println( binary + " in decimal is " + conBinary(binary)); 
	
    }

    
}


