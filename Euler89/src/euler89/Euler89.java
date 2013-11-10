
package euler89; 

import java.io.BufferedReader;
import java.io.FileReader;

public class Euler89 {
    private int[] nums;
    private String[] romans;
    private String[] newRomans;

    public static void main(String[] args) { 
        Euler89 euler = new Euler89();
    } 
    
    private Euler89() {
        nums = new int[1000];
        romans = new String[1000];
        newRomans = new String[1000];
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("roman.txt"));
            for(int i = 0; i < 1000; i++) {
                romans[i] = br.readLine();
                nums[i] = i;
            }
        }
        catch(Exception e) {}
        convToInt(romans, nums);
        convToRoman(nums, newRomans);
        printArray(nums, newRomans, romans);
        System.out.println("---***---\nInitially we have: " + countChars(romans) + " chars");
        System.out.println("At the end we have: " + countChars(newRomans) + " chars");
        System.out.println("***---***\nThe savings is: " + (countChars(romans) - countChars(newRomans)));
    }
    
    private void printArray(int[] nums, String[] newRomans, String[] romans) {
        for(int i = 0; i < 1000; i++) {
            System.out.println(nums[i] + "\t" + newRomans[i] + "\t(" + romans[i] + ")");
        }
    }
    
    private int countChars(String[] romans) {
        int count = 0;
        for(String roman: romans)
            count += roman.length();
        return count;
    }
    
    private void convToInt(String[] romans, int[] nums) {
        int num;
        char c;
        for(int i = 0; i < 1000; i++) {
            num = 0;
            for(int j = 0; j < romans[i].length(); j++) {
                c = romans[i].charAt(j);
                switch(c) {
                    case 'M':   num += 1000;
                                break;
                    case 'D':   num += 500;
                                break;
                    case 'C':   if(j + 1 < romans[i].length() && 
                                    (romans[i].charAt(j + 1) == 'M' || romans[i].charAt(j + 1) == 'D'))
                                    num -= 100;
                                else
                                    num += 100;
                                break;
                    case 'L':   num += 50;
                                break;
                    case 'X':   if(j + 1 < romans[i].length() &&
                                    (romans[i].charAt(j + 1) == 'C' || romans[i].charAt(j + 1) == 'L'))
                                    num -= 10;
                                else
                                    num += 10;
                                break;
                    case 'V':   num += 5;
                                break;
                    case 'I':   if(j + 1 < romans[i].length() && 
                                    (romans[i].charAt(j + 1) == 'X' || romans[i].charAt(j + 1) == 'V'))
                                    num -= 1;
                                else
                                    num += 1;
                                break;
                    default:    System.exit(i);
                                break;
                }
            }
            nums[i] = num;
            //System.out.println(nums[i] + "\t" + romans[i]);
        }
    }
    
    private void convToRoman(int[] nums, String[] newRomans) {
        String roman;
        int num, y;
        for(int i = 0; i < 1000; i++) {
            roman = "";
            // Thousands
            y = nums[i] / 1000;
            num = nums[i] % 1000;
            for(int j = 0; j < y; j++)
                roman += "M";
            // Hundreds
            y = num / 100;
            num %= 100;
            if(y == 9)
                roman += "CM";
            else if(y == 4)
                roman += "CD";
            else {
                if(y >= 5) {
                    roman += "D";
                    y -= 5;
                }
                for(int j = 0; j < y; j++)
                    roman += "C";
            }
            // Tens
            y = num / 10;
            num %= 10;
            if(y == 9)
                roman += "XC";
            else if(y == 4)
                roman += "XL";
            else {
                if(y >= 5) {
                    roman += "L";
                    y -= 5;
                }
                for(int j = 0; j < y; j++)
                    roman += "X";
            }
            // Tens
            y = num;
            if(y == 9)
                roman += "IX";
            else if(y == 4)
                roman += "IV";
            else {
                if(y >= 5) {
                    roman += "V";
                    y -= 5;
                }
                for(int j = 0; j < y; j++)
                    roman += "I";
            }
            newRomans[i] = roman;
        }
    }
}

