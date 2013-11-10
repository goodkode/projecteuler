package euler98;

import java.io.*;
import java.util.*;

public class Euler98 {
    String input;
    String[] words;
    int[] wordScores;
    Set<Integer> scoreSet, anagramSet, squares;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("Euler 98 - program started...\n");
        Euler98 problem = new Euler98();
        problem.solve();
        System.out.println("\n...finished: " + 
                (System.currentTimeMillis() - start) + "ms\n");
    }

    private Euler98() {
        int max = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("words.txt"));
            input = br.readLine();
        }
        catch (Exception ex) {}
        input = input.replace("\"", "");
        words = input.split(",");
        wordScores = new int[words.length];
        scoreSet = new HashSet<>();
        anagramSet = new HashSet<>();
        int i = 0;
        for (String word: words) {
            int score = 0;
            char[] wordChars = word.toCharArray();
            for (char ch: wordChars)
                score += Math.pow(2, ch - 'A');
            score += wordChars.length * 100000000;
            if (max < score)
                max = score;
            if (scoreSet.contains(score))
                anagramSet.add(score);
            else
                scoreSet.add(score);
            wordScores[i] = score;
            //System.out.println(words[i] + " -> " + wordScores[i]); 
            i++;
        }
        squares = new HashSet<>();
        for (int x = 1; x * x < 999999999; x++)
            squares.add(x * x);
        //System.out.println(squares);
    }
    
    private void solve() {
        SortedSet<Integer> solution = new TreeSet<>();
        Queue<String> candidates = new ArrayDeque<>();
        Iterator<Integer> it = anagramSet.iterator();
        while(it.hasNext()) {
            int hitScore = it.next();
            for (int i = 0; i < wordScores.length; i++) {
                if (wordScores[i] == hitScore) {
                    System.out.print(words[i] + " ");
                    candidates.add(words[i]); }
            }
            System.out.println(); 
            String s1 = candidates.poll();
            String s2 = candidates.poll();
            String s3 = candidates.poll();
            solution.add(squareAnagram(s1, s2));
            solution.add(squareAnagram(s1, s3));
            solution.add(squareAnagram(s2, s3));
        }
        System.out.println(solution);
        System.out.println("\nThe solution is: " + solution.last());
    }
    
    private int squareAnagram(String s1, String s2) {
        if (s2 == null)
            return 0;
        StringBuilder p1 = new StringBuilder();
        for (int i = s1.length() - 1; i >= 0; i--)
            p1.append(s1.charAt(i)); 
        if (p1.toString().equals(s2)) {
            System.out.println("*** palindrom found: " + s1 + ", " + s2 + "\n");
            return -1; }
        Set<Character> s1Set = new TreeSet<>();
        Set<Character> s2Set = new TreeSet<>();
        for (int i = 0; i < s1.length(); i++)
            s1Set.add(s1.charAt(i));
        for (int i = 0; i < s1.length(); i++)
            s2Set.add(s2.charAt(i));
        Iterator<Character> s1It = s1Set.iterator();
        while (s1It.hasNext())
            if (!s2Set.contains(s1It.next())) {
                System.out.println("*** fake match found: " + s1 + ", " + s2 + "\n");
                return -99; }
        // Now check for the real deal:
        return checkNumber(s1, s2, s1Set);
    }
    
    private int checkNumber(String s1, String s2, Set<Character> set) {
        Map<Character, Integer> charToInt = new HashMap<>();
        int size = set.size();
        int[] digs = new int[size];
        digs[0] = 1;
        boolean good = false;
        int num1, num2;
        int max = 0;
        while (!good) {
            digs = iterateDigs(digs);
            if (digs == null) {
                System.out.println("* no solution for this one");
                break; }
            num1 = checkIfMappedSquare1(digs, set, charToInt, s1);
            if (num1 != 0) {
                //System.out.println("square one: " + num1);
                num2 = checkIfMappedSquare2(charToInt, s2);
                if (num2 != 0) {
                    System.out.println("square one: " + num1);
                    System.out.println("square two: " + num2);
                    if (num1 > max)
                        max = num1;
                    if (num2 > max)
                        max = num2;
                }
            }
        }
        System.out.println();
        return max;
    }
    private int[] iterateDigs(int[] digs) {
        boolean good = false;
        int last = digs.length - 1;
        int i;
        loop1:
        while (!good) {
            i = last;
            digs[i]++;
            while (digs[i] == 10) {
                digs[i] = 0;
                i--;
                if (i < 0)  return null;
                else        digs[i]++;   }
            //printArr(num);
            checkDuplicate:
            for (int i1 = 0; i1 < digs.length-1; i1++)
                for (int i2 = i1+1; i2 < digs.length; i2++)
                    if (digs[i1] == digs[i2])
                        continue loop1;
            good = true;
        }
        return digs;
    }
    
    private int checkIfMappedSquare1(int[] digs, Set<Character> set, 
                               Map<Character, Integer> charToInt, String s1) {
        charToInt.clear();
        Iterator<Character> charIt = set.iterator();
        char ch;
        int i = 0;
        while (charIt.hasNext()) {
            ch = charIt.next();
            charToInt.put(ch, digs[i++]); }
        int num = 0;
        for (int j = 0; j < s1.length(); j++)
            num = num * 10 + charToInt.get(s1.charAt(j));
        //printArr(digs);
        //System.out.println(num);
        if (num >= Math.pow(10, s1.length()-1) && squares.contains(num))
            return num;
        else
            return 0;
    }
    
    private int checkIfMappedSquare2(Map<Character, Integer> charToInt, String s2) {
        int num = 0;
        for (int j = 0; j < s2.length(); j++)
            num = num * 10 + charToInt.get(s2.charAt(j));
        if (num >= Math.pow(10, s2.length()-1) && squares.contains(num))
            return num;
        else
            return 0;
    }
   
    private void printArr(int[] n) {
        System.out.print(" \"");
        for(int i = 0; i < n.length; i++)
            System.out.print(n[i]);
        System.out.print("\" ");
    }
}
