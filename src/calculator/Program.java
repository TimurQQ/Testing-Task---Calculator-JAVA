package calculator;

import java.util.Scanner;

class Calculator {
	public static Number calc(Number num1, char operation, Number num2) throws Exception {
        Number result = num1;
        if (num1.isArabic != num2.isArabic) {
        	throw new RuntimeException("You can't use an operation with different types of numbers!!!");
        }
        switch (operation) {
            case '+':
                result = num1.add(num2);
                break;
            case '-':
            	if (num1.isArabic && num1.value <= num2.value) {
            		throw new RuntimeException("The Romans did not know non positive numbers :)");
            	}
                result = num1.subtract(num2);
                break;
            case '*':
                result = num1.multiply(num2);
                break;
            case '/':
            	if (num1.isArabic && num1.value < num2.value) {
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

class Number {
	public int value = 0;
	public boolean isArabic = false;
	
	public Number(String numStr) throws Exception {
		if (isArabic(numStr)) {
			numStr = Integer.toString(arabicToInt(numStr));
		}
		value = Integer.parseInt(numStr); // may throw RuntimeException
		if (value >= 11 || value <= 0) {
			throw new RuntimeException("Wrong Input!!! Numbers must be less than 11 and greater the 0");
		}
	}
	
	public void print() {
		if (isArabic) {
			System.out.println(arabic[value - 1]);
		}
		else {
			System.out.println(value);
		}
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
	
	private boolean isArabic(String numStr) {
		for (String s : arabic) {
			if (numStr.equals(s)) {
				isArabic = true;
				return true;
			}
		}
		return false;
	}
	
	private static int arabicToInt(String str) {
		for (int i = 0; i < arabic.length; ++i) {
			if (str.equals(arabic[i])) {
				return i + 1;
			}
		}
		return -1;
	}
	
	public static String[] arabic = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
									"XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"};
}

public class Program {
	private static Scanner console = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			Number a = new Number(console.next());
			char b = console.next().charAt(0);
			Number c = new Number(console.next());
			Number res = Calculator.calc(a, b, c);
			res.print();
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
