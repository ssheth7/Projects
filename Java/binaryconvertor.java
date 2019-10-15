//To do: take in binary number as String

import java.util.Scanner;
import java.math.BigInteger;
import java.lang.Math;
class binary{
    //Uses recursion to check if the input was a binary number
    static boolean isBinary(String input){
	char lastnum = input.charAt(input.length()-1);
	if(lastnum =='1' && lastnum == '0')
	    isBinary(input.substring(0,input.length()-1));
	else if(lastnum !='1' && lastnum !='0')
	    return false;
	else if (input.substring(0, input.length()-1).equals("1")|| input.substring(0, input.length()-1).equals('0'))
	    return true;
	return true;
    }

    
    //Uses the BigInteger class to account for large decimal numbers
    static BigInteger conBinary(BigInteger input){
	BigInteger temp = input;
	int place = 0;
	BigInteger sum = new BigInteger("0");
	BigInteger added= new BigInteger("2");
	while(!temp.toString().equals(0)){
	    if(temp.divide(BigInteger.valueOf(10)).compareTo(new BigInteger("1"))== 0){
		sum = sum.add(added.pow(place));
	    }
	place++;
        temp= temp.divide(BigInteger.valueOf(10));
	}
	return sum;
	}
    static boolean isNumeric(String binary){
	try {
	    Double.parseDouble(binary);
	    return true;
	}
	catch(NumberFormatException e){
	    return false;
	}
    }
    
    public static void main(String[] args){
	String binary;
	Scanner scanner = new Scanner(System.in);
	while(true){
	System.out.println("Enter a binary number: ");
	while(true){
		binary = scanner.nextLine();
		if(isNumeric(binary) && isBinary(binary))
		    break;
		System.out.println("Not a binary number! Enter a binary number: ");
	    }
	BigInteger binarycon = new BigInteger(binary);
	System.out.println(binary + " in decimal is " + conBinary(binarycon)); 
	System.out.println("Would you like to convert another number? ");
	String again = scanner.next();
	if(again.indexOf("y") == -1)
	    break;
	}
    }
}





