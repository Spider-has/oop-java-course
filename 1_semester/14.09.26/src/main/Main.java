package main;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private class Task1 {
    public static String OperateNum(int num){
            if(num % 3 == 0 && num % 5 == 0 ){
                return "FizzBuzz";
            } else if(num % 3 == 0) {
                return "FIZZ";
            } else if (num % 5 == 0){
                return "BUZZ";
            } 
            return Integer.toString(num); 
        }
    }
    private class Task2 {
    public static int CalculateDigits(int num){
            int sum = 0;
            while(num > 0){
                sum += num % 10;
                num = num / 10;
            }
            return sum;
        }
    }
    private class Task3 {
        public static int ProcessArray(int[] input){
            int element = 0;
            for(int num : input){
                element = element ^ num; 
            }
            return element;
        }
    }
    private class Task4 {
    public static boolean ProcessString(String input, HashMap<Character, Integer> map){
            int first = 0;
            int last = input.length()-1;
            char[] charArray = input.toCharArray();
            for(Character ch : charArray){
                map.putIfAbsent(ch, 0); 
                map.put(ch, map.get(ch) + 1);
            }
            while(first < last){
                if(charArray[first] != charArray[last]){
                    return false;
                }
                ++first;
                --last;
            }
            return true;
        }
    }
    public static void Task1(){
        for(int i = 1; i <= 100; ++i){
            System.out.println(Task1.OperateNum(i));
        }
    }

    public static void Task2(){
        int num = 0;
        Scanner scanner = new Scanner(System.in);
        try {    
            num = Math.abs(scanner.nextInt());
        } catch (Exception e) {
            System.err.println("НЕЛЬЗЯ ТАКОЙ ВВОД. ТОЛЬКО ЧИСЛА");
        }

        while(num / 10 != 0){
            num = Task2.CalculateDigits(num);
        }

        System.out.println(num);
        scanner.close();
    }

    public static void Task3(){
        Scanner scanner = new Scanner(System.in);
        try {
            int size = scanner.nextInt();
            int[] numbers = new int[size];
            for(int i = 0; i < size; ++i){
                numbers[i] = scanner.nextInt();
            }
            System.out.println(Task3.ProcessArray(numbers));
        } catch (Exception e) {
            System.err.println("Incorrect input");
        } finally {
            scanner.close();
        }
    }
    public static void Task4(String str){
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        boolean isPalindrome = Task4.ProcessString(str, map);
        System.out.println("is palindrome: " + isPalindrome);
        map.forEach((key, value) -> {
            System.out.println("char: " + key + ", count: " + value);
        });
    }
    public static void main(String[] args) {
        Task4(args[0]);
    }
}


