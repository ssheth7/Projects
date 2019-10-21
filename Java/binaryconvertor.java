//Can take a binary number of any length and convert it to decimal
import java.util.Scanner;
import java.math.BigInteger;
class binary{
    //Uses recursion to check if the input was a binary number
    //if the last digit is 0 or 1, it calls itself again and uses input without its last digit
    static boolean isBinary(String input){	
	if(input.length() > 1){
	    char lastnum = input.charAt(input.length()-1);
            String withoutlast = input.substring(0, input.length()-1);
	    if(lastnum =='1' || lastnum == '0')
		return isBinary(withoutlast);
	    else return false;
	}
	if(input.charAt(0)=='1' || input.charAt(0) == '0')
	    return true;
	return false;
	}
	
	public static String conHex(BigInteger dec){
		String hexreverse = ""; 
		String hex = "";
		char append = '?';
		BigInteger tento4 = new BigInteger("10000");
		while(dec.toString().length() >= 1){
		String hexChar = dec.mod(tento4).toString();
		switch (hexChar) {
			case  "0000":
				append = '0';
			case "0001":
				append = '1';
			case "0010":
				append = '2';
			case "0011":
				append = '3';
			case "0100":
				append = '4';
			case "0101":
				append = '5';
			case "0110":
				append = '6';
			case "0111":
				append = '7';
			case "1000":
				append = '8';
			case "1001":
				append = '9';
			case "1010":
				append = 'A';
			case "1011":
				append = 'B';
			case "1100":
				append = 'C';
			case "1101":
				append = 'D';
			case "1110":
				append = 'E';
			case "1111":
				append = 'F';
		}
		dec = dec.divide(tento4);
		hexreverse+=append;
	}
		 for (int i = hexreverse.length() - 1 ; i >= 0 ; i--)
         hex = hex + hexreverse.charAt(i);
		return hex;
		
	}

    
    //Uses the BigInteger class to account for large decimal numbers
    //Checks if the last digit of the binary number is 1, if true, it adds 2^place to sum, if false, place is incremented and the binary number is divided by 10 
    static BigInteger conBinary(BigInteger input){
	BigInteger temp = input;
	int place = 0;
	BigInteger sum = new BigInteger("0");
	BigInteger added= new BigInteger("2");
	BigInteger one  = new BigInteger("1");
	BigInteger ten = new BigInteger("10");
	while(!temp.toString().equals("0")){
	    if(temp.mod(ten).compareTo(one)== 0){
		sum = sum.add(added.pow(place));
	    }
	place++;
        temp= temp.divide(ten);
	}
	return sum;
	}
    //tries to convert the string to a long, if it fails the function catches the exception and returns false, else it returns true
    static boolean isNumeric(String binary){
	try {
	    Double.parseDouble(binary);
	    return true;
	}
	catch(NumberFormatException e){
	    return false;
	}
    }
    //Takes the users input as a string called binary
    //First checks if its numeric, checks if its binary, and then converts the String into a BigInteger
    public static void main(String[] args){
	String binary;
		var scanner = new Scanner(System.in);
	while(true){
	System.out.println("Enter a binary number: ");
	while(true){
		binary = scanner.nextLine();
		if(isNumeric(binary) && isBinary(binary))
		    break;
		System.out.println("Not a binary number! Enter a binary number: ");
	    }
	BigInteger binarycon = new BigInteger(binary);
	System.out.println(binarycon + " in decimal is " + conBinary(binarycon) + " and " + "conHex(binarycon)" + " in hexadecimal."); 
	System.out.println("Would you like to convert another number? ");
	String again = scanner.nextLine();
	if(again.indexOf("y") == -1)
	    break;
	}
    }
}





