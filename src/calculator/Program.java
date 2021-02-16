package calculator;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

class Calculator {
	public static Number calc(Number num1, char operation, Number num2) throws Exception {
        Number result = num1;
        if (num1.type != num2.type) {
        	throw new RuntimeException("You can't use an operation with different types of numbers!!!");
        }
        switch (operation) {
            case '+':
                result = num1.add(num2);
                break;
            case '-':
            	if (num1.type == NumericalSystem.NumberType.ROMAN && num1.value <= num2.value) {
            		throw new RuntimeException("The Romans did not know non positive numbers :)");
            	}
                result = num1.subtract(num2);
                break;
            case '*':
                result = num1.multiply(num2);
                break;
            case '/':
            	if (num1.type == NumericalSystem.NumberType.ROMAN && num1.value < num2.value) {
            		throw new RuntimeException("There was no zero in the Roman numeral system!!!");
            	}
                result = num1.divide(num2);
                break;
            default:
            	throw new RuntimeException("Unknown operator in the input string!!!");
        }
        return result;
    }
}

class NumericalSystem {
	enum NumberType {
		ARABIC,
		ROMAN
	}
	
	public static Number parse(String numStr) throws Exception {
		if (isRoman(numStr)) {
			return new RomanNumber(numStr);
		}
		return new Number(numStr);
	}
	
	public static boolean isRoman(String numStr) {
		for (String s : roman) {
			if (numStr.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public static String romanToArabic(String str) {
		for (int i = 0; i < roman.length; ++i) {
			if (str.equals(roman[i])) {
				return Integer.toString(i + 1);
			}
		}
		return Integer.toString(-1);
	}
	
	public static String arabicToRoman(int value) {
		return roman[value - 1];
	}
	
	private static String[] readRomansFromFile() {
		Path filePath = Paths.get(System.getProperty("user.dir") + "/tmp/roman.txt");
		String[] roman = {};
		try {
			roman = Files.readString(filePath).split(" ");
		}
		catch(Exception ex) {
			System.out.println("An error occurred while reading Roman numbers from the file!");
			System.exit(0);
		}
		return roman;
	}
	
	private static String[] roman = readRomansFromFile();
}

class Number {
	public int value = 0;
	public NumericalSystem.NumberType type = NumericalSystem.NumberType.ARABIC;
	
	public Number(String numStr) throws Exception {
		value = Integer.parseInt(numStr); // may throw RuntimeException
		if (value >= 11 || value <= 0) {
			throw new RuntimeException("Wrong Input!!! Numbers must be less than 11 and greater the 0");
		}
	}
	
	public void print() {
		System.out.println(value);
	}
	
	public Number add(Number a) {
		Number res = a;
		res.value += this.value;
		return res;
	}
	
	public Number subtract(Number a) {
		Number res = a;
		res.value = this.value - a.value;
		return res;
	}
	
	public Number multiply(Number a) {
		Number res = a;
		res.value *= a.value;
		return res;
	}
	
	public Number divide(Number a) {
		Number res = a;
		res.value = this.value / a.value;
		return res;
	}
}

class RomanNumber extends Number {
	public RomanNumber(String numStr) throws Exception {
		super(NumericalSystem.romanToArabic(numStr));
		type = NumericalSystem.NumberType.ROMAN;
	}
	
	public void print() {
		System.out.println(NumericalSystem.arabicToRoman(value));
	}
}

public class Program {
	private static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			Number a = NumericalSystem.parse(console.next());
			char b = console.next().charAt(0);
			Number c = NumericalSystem.parse(console.next());
			Number res = Calculator.calc(a, b, c);
			res.print();
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
