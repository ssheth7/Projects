import java.util.Scanner;
class binary{
    static boolean isBinary(double input){
	if(input%10 ==1 && input%10 == 0)
	    isBinary(input/10);
	else if(input%10 !=1 && input%10 != 0)
	    return false;
	else if (input/10 == 1)
	    return true;
	return true;
    }
    static BigInteger conBinary(int input){
	int temp = input;
	BigInteger sum = new BigInteger("0");
	sum.add(new BigInteger(""));
    }
    public static void main(String[] args){
	double binary;
	Scanner scanner = new Scanner(System.in);
	System.out.println("Enter a binary number: ");
	while(true){
	    if(!scanner.hasNextInt()){
	    System.out.println("Not a binary number! Please enter a binary number: ");
	    scanner.next();
	    }
	    else {
		binary = scanner.nextDouble();
		if(isBinary(binary))
		    break;
		System.out.println("Not a binary number! Enter a binary number: ");
	    }
	}
	int decimal = 0;
	System.out.println((int) binary + " in decimal is " + decimal); 
	
    }

    
}


