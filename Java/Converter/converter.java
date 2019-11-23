
//Can take a binary number of any length and convert it to decimal
import java.util.Scanner;
import java.math.BigInteger;

class converter {
	// Uses recursion to check if the input was a binary number
	// if the last digit is 0 or 1, it calls itself again and uses input without its
	// last digit
	static boolean isBinary(String input) {
		if (input.length() > 1) {
			char lastnum = input.charAt(input.length() - 1);
			String withoutlast = input.substring(0, input.length() - 1);
			if (lastnum == '1' || lastnum == '0')
				return isBinary(withoutlast);
			else
				return false;
		}
		if (input.charAt(0) == '1' || input.charAt(0) == '0')
			return true;
		return false;
	}

	// tries to convert the string to a long, if it fails the function catches the
	// exception and returns false, else it returns true
	static boolean isNumeric(String binary) {
		try {
			Double.parseDouble(binary);
			if(binary.indexOf(".") == -1)
			return true;
			else return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	static boolean isHex(String hex){
		String badchar = "?><:/.,';'[|}{qwrtyuioplkjhgszxvnm`~";
		for(int i = 0; i < hex.length();i++)
			for(int j = 0; j < badchar.length();j++)
			if(hex.charAt(i) == badchar.charAt(j))
			return false;
		return true;
	}

	// Uses BigInteger's toString method to convert the given decimal to hexadecimal
	public static String conHex(BigInteger dec) {
		return dec.toString(16);
	}

	// Uses the BigInteger class to account for large decimal numbers
	// Checks if the last digit of the binary number is 1, if true, it adds 2^place
	// to sum, if false, place is incremented and the binary number is divided by 10
	static BigInteger conBinary(BigInteger input) {
		BigInteger temp = input;
		int place = 0;
		BigInteger sum = new BigInteger("0");
		BigInteger added = new BigInteger("2");
		BigInteger one = new BigInteger("1");
		BigInteger ten = new BigInteger("10");
		while (!temp.toString().equals("0")) {
			if (temp.mod(ten).compareTo(one) == 0) {
				sum = sum.add(added.pow(place));
			}
			place++;
			temp = temp.divide(ten);
		}
		return sum;
	}

	static BigInteger HextoBinary(String hex){
			hex = hex.toLowerCase();
			String binary = "";
			char hexchar;
			for(int i = 0; i < hex.length();i++){
			hexchar = hex.charAt(i);
			switch (hexchar){
				case ('0'):
				binary+="0000";
				break;
				case ('1'):
				binary+="0001";
				break;
				case ('2'):
				binary+="0010";
				break;
				case ('3'):
				binary+="0011";
				break;
				case ('4'):
				binary+="0100";
				break;
				case ('5'):
				binary+="0101";
				break;
				case ('6'):
				binary+="0110";
				break;
				case ('7'):
				binary+="0111";
				break;
				case ('8'):
				binary+="1000";
				break;
				case ('9'):
				binary+="1001";
				break;
				case ('a'):
				binary+="1010";
				break;
				case ('b'):
				binary+="1011";
				break;
				case ('c'):
				binary+="1100";
				break;
				case ('d'):
				binary+="1101";
				break;
				case ('e'):
				binary+="1110";
				break;
				case ('f'):
				binary+="1111";
				break;
			}
			
			
		}
		return new BigInteger(binary);
	}


	// Takes the users input as a string called binary
	// First checks if its numeric, checks if its binary, and then converts the
	// String into a BigInteger
	public static void main(String[] args) {
		String binary;
		var scanner = new Scanner(System.in);
		//while loop that runs as long as the user wants to convert numbers
		while (true) {
			String binordec;
			//while loop that runs until the user enters a valid menu input
			while (true) {
				System.out.println("Press 1 to convert a binary number to hex and decimal");
				System.out.println("Press 2 to convert a decimal number to hex and binary");
				System.out.println("Press 3 to convert a hex number to decimal and binary");
				binordec = scanner.nextLine();
				if (binordec.equals("1") || binordec.equals("2") || binordec.equals("3"))
					break;
				System.out.println("Invalid Input!");
			}
			//while loop that runs if the user inputs a binary number they want to convert
			while (binordec.equals("1")) {
				System.out.println("Enter a binary number: ");
				binary = scanner.nextLine();
				if (isNumeric(binary) && isBinary(binary)) {
					BigInteger binarycon = new BigInteger(binary);
					System.out.println(binarycon + " in decimal is " + conBinary(binarycon) + " and "
							+ conHex(conBinary(binarycon)) + " in hexadecimal.");
					break;
				}
				System.out.println("Not a binary number! Enter a binary number: ");
			}

			//while loop that runs if the user inputs a decimal number they want to convert
			while (binordec.equals("2")) {
				String decimalnum;
				//while loop that ensures the users input is a decimal number
				while (true) {
					System.out.println("Enter your decimal number");
					decimalnum = scanner.nextLine();
					if (isNumeric(decimalnum))
						break;
					else
						System.out.println("Not a convertible number!");
				}
				BigInteger bigdecimalnum = new BigInteger(decimalnum);
				System.out.println(bigdecimalnum.toString() + " in binary is " + bigdecimalnum.toString(2) + " and "
						+ bigdecimalnum.toString(16) + " in hexadecimal.");
				break;
			}

			while(binordec.equals("3")){
				String hexnum;
				//while loop that ensures the users input is a hexadecimal number
				while(true){
					System.out.println("Enter your hex number");
					hexnum = scanner.nextLine();
					if(isHex(hexnum) && hexnum.indexOf(".")==-1)
						break;
					else System.out.println("Not a convertible number!");
				}
				System.out.println(hexnum + " in binary is " + HextoBinary(hexnum) + " and in decimal it is "+ conBinary(HextoBinary(hexnum)));
				break;
			}

			//If statement that stops the program or restarts it depending on the users input
			System.out.println("Would you like to convert another number? ");
			String again = scanner.nextLine();
			if (again.indexOf("y") == -1 )
				break;
		}
	}

}
