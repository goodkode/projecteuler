package euler96;
import java.io.*;
import java.util.*;

public class Euler96 {
    private long start;
    private int solved, unSolved;
    private int solution = 0;
    
    public static void main(String[] args) {
        Euler96 problem = new Euler96();
        problem.start = System.nanoTime();
        problem.solve();
        System.out.println("Program run in " + (System.nanoTime() 
                            - problem.start) / 1000000000.0 + " seconds");
    }
    
    private void solve() {
        int[][] cell = new int[9][9];
        Matrix sudoku;
        solved = unSolved = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("sudoku.txt"))) {
            String line = br.readLine();
            while (line != null) {
                for (int i = -1; i < 9; i++) {
                    if (i == -1) {
                        System.out.println("Processing: " + line + "...");
                        cell = new int[9][9];
                    }
                    else if (i <= 8)
                        for (int j = 0; j < 9; j++)
                            cell[i][j] = line.charAt(j) - '0';
                    if (i == 8) {
                        sudoku = new Matrix(cell);
                        solveMatrix(sudoku);
                        System.out.println((System.nanoTime() 
                                - start) / 1000000000.0 + " seconds");
                    }
                    line = br.readLine();
            } }
        }
        catch (Exception e) { e.printStackTrace(); }
        System.out.println("Solved: " + solved + ", not solved: " + unSolved);
    }
    
    private void solveMatrix(Matrix sudoku) {
        //sudoku.printMatrix();
        int i, j;
        int sc, twth, lc, lc2, np;
        sc = twth = lc = lc2 = np = 0;
        while (sudoku.isSolution() == false) {
            sc = sudoku.singleChoice();
            twth = sudoku.twoOutThree();
            sc += sudoku.singleChoice();
            lc = sudoku.lockedCand();
            sc += sudoku.singleChoice();
            lc2 = sudoku.lockedCand2();
            sc += sudoku.singleChoice();
            np = sudoku.nakedPairs();
            if (sc != 0)   System.out.println("sc:   " + sc);
            if (twth != 0)  System.out.println("twth: " + twth);
            if (lc != 0)  System.out.println("lc:  " + lc);
            if (lc2 != 0)  System.out.println("lc2: " + lc2);
            if (np != 0)  System.out.println("np:  " + np);
            if ((sc + twth + lc + lc2 + np) == 0) {
                System.out.println("-- now guess..");
                sudoku.guess();
                //sudoku.printMatrix();
                if (!sudoku.isSolution()) {
                    unSolved++;
                    return; }
            }
        }
        solved++;
        System.out.println("*** Solved! ***");
        //System.out.println("-- Verified: " + sudoku.verifySolution());
        solution += sudoku.value();
        System.out.println("* Solution: " + solution);
        System.out.println();
        //sudoku.printMatrix();
    }
}

class Matrix {
    private int[][] cell;
    private boolean[][] origCell;
    private List<List<Integer>> h, v, b;
    private Set<Integer> candidate[][];
    
    public Matrix(int[][] refMatrix) {
        this.cell = new int[9][9];
        for (int k = 0; k < 9; k++)
            System.arraycopy(refMatrix[k], 0, this.cell[k], 0, 9);
        this.setHVBandCand();
    }
    public Matrix(int[][] refMatrix, int i, int j, int newValue) {
        this.cell = new int[9][9];
        for (int k = 0; k < 9; k++)
            System.arraycopy(refMatrix[k], 0, this.cell[k], 0, 9);
        this.cell[i][j] = newValue;
        this.setHVBandCand();
    }
    
    private void setHVBandCand() {
        h = new ArrayList<>(9);
        v = new ArrayList<>(9);
        b = new ArrayList<>(9);
        candidate = new HashSet[9][9];
        for (int k = 0; k < 9; k++) {
            h.add(k, new ArrayList<Integer>(9));
            v.add(k, new ArrayList<Integer>(9));
            b.add(k, new ArrayList<Integer>(9)); }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cell[i][j] == 0)
                    candidate[i][j] = new HashSet<>();
                else {
                    h.get(i).add(cell[i][j]);
                    v.get(j).add(cell[i][j]);
                    b.get(i/3*3 + j/3).add(cell[i][j]); 
                    candidate[i][j] = null; } } }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cell[i][j] == 0)
                    for (int x = 1; x <= 9; x++) {
                        if (h.get(i).contains(x) || v.get(j).contains(x) || b.get(i/3*3 + j/3).contains(x))
                            continue;
                        candidate[i][j].add(x); } } } 
    }
    private void setHVBandCand(int i, int j, int x) {
        h.get(i).add(x);
        v.get(j).add(x);
        b.get(i/3*3 + j/3).add(x);
        for (int k = 0; k < 9; k++) {
            if (candidate[k][j] != null)
                    candidate[k][j].remove(x);
            if (candidate[i][k] != null)
                    candidate[i][k].remove(x);
            if (candidate[i/3*3 + k/3][j/3*3 + k%3] != null)
                    candidate[i/3*3 + k/3][j/3*3 + k%3].remove(x);
    } }
    
    public int singleChoice() {
        int y, worked = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cell[i][j] == 0 && candidate[i][j].size() == 1) {
                    y = candidate[i][j].iterator().next();
                    candidate[i][j] = null;
                    cell[i][j] = y;
                    this.setHVBandCand(i, j, y);
                    //System.out.println("SC: ["+i+"]["+j+"]: " + cell[i][j]);
                    //this.printMatrix();
                    worked++; } } }
        return worked;
    }
    
    public int twoOutThree() {
        int worked = 0;
        int iScore, jScore, bScore, lScore, cScore;
        int line, col, loc;
        for (int x = 1; x <= 9; x++) {
            for (int i = 0; i < 9; i += 3) {
                iScore = bScore = lScore = 0;
                if (h.get(i).contains(x))       iScore += 1;
                if (h.get(i+1).contains(x))     iScore += 2;
                if (h.get(i+2).contains(x))     iScore += 4;
                {   if (iScore == 6)                line = i;
                    else if (iScore == 5)           line = i + 1;
                    else if (iScore == 3)           line = i + 2;
                    else                            continue; }
                if (b.get(i).contains(x))       bScore += 1;
                if (b.get(i+1).contains(x))     bScore += 2;
                if (b.get(i+2).contains(x))     bScore += 4;
                {   if (bScore == 6)                col = 0;
                    else if (bScore == 5)           col = 3;
                    else if (bScore == 3)           col = 6;
                    else                            continue; }
                if (cell[line][col] != 0 
                        || !candidate[line][col].contains(x))   lScore += 1;
                if (cell[line][col+1] != 0 
                        || !candidate[line][col+1].contains(x)) lScore += 2;
                if (cell[line][col+2] != 0
                        || !candidate[line][col+2].contains(x)) lScore += 4;
                {   if (lScore == 6)          ;
                    else if (lScore == 5)     col += 1;
                    else if (lScore == 3)     col += 2; 
                    else                      continue; }
                worked++;
                cell[line][col] = x;
                setHVBandCand(line, col, x);
                //System.out.println("Changed cell["+line+"]["+col+"]: " + x);
                //printMatrix();
            }
            for (int j = 0; j < 9; j += 3) {
                jScore = bScore = cScore = 0;
                if (h.get(j).contains(x))       jScore += 1;
                if (h.get(j+1).contains(x))     jScore += 2;
                if (h.get(j+2).contains(x))     jScore += 4;
                {   if (jScore == 6)                col = j;
                    else if (jScore == 5)           col = j + 1;
                    else if (jScore == 3)           col = j + 2;
                    else                            continue; }
                if (b.get(j/3).contains(x))       bScore += 1;
                if (b.get(j/3+3).contains(x))     bScore += 2;
                if (b.get(j/3+6).contains(x))     bScore += 4;
                {   if (bScore == 6)                line = 0;
                    else if (bScore == 5)           line = 3;
                    else if (bScore == 3)           line = 6;
                    else                            continue; }
                if (cell[line][col] != 0 
                        || !candidate[line][col].contains(x))   cScore += 1;
                if (cell[line+1][col] != 0 
                        || !candidate[line+1][col].contains(x)) cScore += 2;
                if (cell[line+2][col] != 0
                        || !candidate[line+2][col].contains(x)) cScore += 4;
                {   if (cScore == 6)          ;
                    else if (cScore == 5)     line += 1;
                    else if (cScore == 3)     line += 2; 
                    else                      continue; }
                worked++;
                cell[line][col] = x;
                setHVBandCand(line, col, x);
                //System.out.println("Changed cell["+line+"]["+col+"]: " + x);
                //printMatrix();
            }
        }
        return worked;
    }
    
    public int lockedCand() {
        int worked = 0;
        int lScore, cScore;
        int lCount, cCount;
        for (int line = 0; line < 9; line += 3)
        for (int col = 0; col < 9; col += 3)
            for (int x = 1; x <= 9; x++) {
                if (b.get(line + col/3).contains(x))   continue;
                lScore = cScore = 0;
                for (int k = 0; k < 3; k++) {
                    if ((cell[line+k][col] == 0 && candidate[line+k][col].contains(x)) 
                            || (cell[line+k][col+1] == 0 && candidate[line+k][col+1].contains(x)) 
                            || (cell[line+k][col+2] == 0 && candidate[line+k][col+2].contains(x)))
                        lScore += (k + 3);    // lScore += 3, 4, or 5 for line 0, 1, or 2
                    if ((cell[line][col+k] == 0 && candidate[line][col+k].contains(x)) 
                            || (cell[line+1][col+k] == 0 && candidate[line+1][col+k].contains(x))
                            || (cell[line+2][col+k] == 0 && candidate[line+2][col+k].contains(x)))
                        cScore += (k + 3);    // lScore += 3, 4, or 5
                }
                for (int j = 0; j < 9; j++) {
                    if (lScore == 3 && (j < col || j > col+2)  && cell[line][j] == 0 
                            && candidate[line][j].remove(x))        worked++;
                    if (lScore == 4 && (j < col || j > col+2)  && cell[line+1][j] == 0 
                            && candidate[line+1][j].remove(x))        worked++;
                    if (lScore == 5 && (j < col || j > col+2)  && cell[line+2][j] == 0 
                            && candidate[line+2][j].remove(x))        worked++; }
                for (int i = 0; i < 9; i++) {
                    if (cScore == 3 && (i < line || i > line+2)  && cell[i][col] == 0 
                            && candidate[i][col].remove(x))        worked++;
                    if (cScore == 4 && (i < line || i > line+2)  && cell[i][col+1] == 0 
                            && candidate[i][col+1].remove(x))        worked++;
                    if (cScore == 5 && (i < line || i > line+2)  && cell[i][col+2] == 0 
                            && candidate[i][col+2].remove(x))        worked++; }
            }
        return worked;
    }
   
    public int lockedCand2() {
        int worked = 0;
        int lCount, hCount;
        int cCount, vCount;
        for (int line = 0; line < 9; line += 3)
        for (int col = 0; col < 9; col += 3)
            for (int x = 1; x <= 9; x++) {
                if (b.get(line + col/3).contains(x))   continue;
                for (int k = 0; k < 3; k++) {
                    lCount = hCount = 0;
                    cCount = vCount = 0;
                    for (int l = 0; l < 3; l++) {
                        if (cell[line+k][col+l] == 0 && candidate[line+k][col+l].contains(x))   lCount++;
                        if (cell[line+l][col+k] == 0 && candidate[line+l][col+k].contains(x))   cCount++; }
                    for (int j = 0; j < 9; j++)
                        if (cell[line+k][j] == 0 && candidate[line+k][j].contains(x))           hCount++;
                    for (int i = 0; i < 9; i++)
                        if (cell[i][col+k] == 0 && candidate[i][col+k].contains(x))             vCount++;
                    if (lCount != 0 && lCount == hCount)
                        for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++)
                            if (i != k && cell[line+i][col+j] == 0 && candidate[line+i][col+j].remove(x))
                                worked++;
                    if (cCount != 0 && cCount == vCount)
                        for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++)
                            if (j != k && cell[line+i][col+j] == 0 && candidate[line+i][col+j].remove(x))
                                worked++;
            } }
        return worked;
    }
    
    public int nakedPairs() {
        int worked = 0;
        int loc1, loc2;
        int p1, p2, pp;
        Iterator<Integer> it;
        Set<Integer> pairs = new HashSet<>();
        for (int x = 1; x <= 9; x++) {
            for (int k = 0; k < 9; k++) {
                pairs.clear();
                for (int j = 0; j < 0; j++) 
                    if (cell[k][j] == 0 && candidate[k][j].size() == 2) {
                        it = candidate[k][j].iterator();
                        p1 = it.next();
                        p2 = it.next();
                        pp = (p1 > p2) ? (p1*10+p2) : (p2*10+p1);
                        if (pairs.contains(pp))
                            for (int j2 = 0; j2 < 9; j2++) {
                                if (cell[k][j2] != 0 || (candidate[k][j2].size() == 2 
                                        && candidate[k][j2].contains(p1) 
                                        && candidate[k][j2].contains(p2)))       continue;
                                if (candidate[k][j2].remove(p1))             worked++;
                                if (candidate[k][j2].remove(p2))             worked++; }
                        pairs.add(pp); }
                pairs.clear();
                for (int i = 0; i < 9; i++)
                    if (cell[i][k] == 0 && candidate[i][k].size() == 2) {
                        it = candidate[i][k].iterator();
                        p1 = it.next();
                        p2 = it.next();
                        pp = (p1 > p2) ? (p1*10+p2) : (p2*10+p1);
                        if (pairs.contains(pp))
                            for (int i2 = 0; i2 < 9; i2++) {
                                if (cell[i2][k] != 0 || (candidate[i2][k].size() == 2 
                                        && candidate[i2][k].contains(p1) 
                                        && candidate[i2][k].contains(p2)))      continue;
                                if (candidate[i2][k].remove(p1))                worked++;
                                if (candidate[i2][k].remove(p2))                worked++; }
                        pairs.add(pp); }
                pairs.clear();
                for (int l = 0; l < 9; l++)
                    if (cell[k/3*3 + l/3][k%3*3 + l%3] == 0 && candidate[k/3*3 + l/3][k%3*3 + l%3].size() == 2) {
                        it = candidate[k/3*3 + l/3][k%3*3 + l%3].iterator();
                        p1 = it.next();
                        p2 = it.next();
                        pp = (p1 > p2) ? (p1*10+p2) : (p2*10+p1);
                        if (pairs.contains(pp))
                            for (int l2 = 0; l2 < 9; l2++) {
                                if (cell[k/3*3 + l2/3][k%3*3 + l2%3] != 0 || (candidate[k/3*3 + l2/3][k%3*3 + l2%3].size() == 2 
                                        && candidate[k/3*3 + l2/3][k%3*3 + l2%3].contains(p1) 
                                        && candidate[k/3*3 + l2/3][k%3*3 + l2%3].contains(p2)))       continue;
                                if (candidate[k/3*3 + l2/3][k%3*3 + l2%3].remove(p1))                 worked++;
                                if (candidate[k/3*3 + l2/3][k%3*3 + l2%3].remove(p2))                 worked++; }
                        pairs.add(pp); }
        } }
        return worked;
    }
    
     public void guess() {
        origCell = new boolean[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if (cell[i][j] == 0)    origCell[i][j] = false;
                else                    origCell[i][j] = true; }
        int x;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if (origCell[i][j])     continue;
                x = nextCand(i, j, cell[i][j]);
                if (x > 0)
                    cell[i][j] = x;
                else { 
                    cell[i][j] = 0;
                    do {
                        j--;
                        if (j < 0) { i--; j = 8; }
                    } while (origCell[i][j]);
                    j--;
                    if (j < 0) { i--; j = 8; }
            } }
    }
    private int nextCand(int i, int j, int x) {
        boolean good;
        for (int y = x+1; y <= 9; y++) {
            good = true;
            for (int k = 0; k < 9; k++)
                if (cell[i][k] == y || cell[k][j] == y || cell[i/3*3 + k/3][j/3*3 + k%3] == y) {
                    good = false;
                    break; }
            if (good)
                return y; }
        return -1;
    }
    
    public boolean isSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (cell[i][j] == 0)
                    return false; }
        return true; }
    public boolean verifySolution() {
        ArrayList<Set<Integer>> h2 = new ArrayList<>();
        ArrayList<Set<Integer>> v2 = new ArrayList<>();
        ArrayList<Set<Integer>> b2 = new ArrayList<>();
        for (int k = 0; k < 9; k++) {
            h2.add(k, new HashSet<Integer>());
            v2.add(k, new HashSet<Integer>());
            b2.add(k, new HashSet<Integer>()); }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                h2.get(i).add(cell[i][j]);
                v2.get(j).add(cell[i][j]);
                b2.get(i/3*3 + j/3).add(cell[i][j]);
        } }
        for (int k = 0; k < 9; k++)
            for (int x = 1; x <= 9; x++) {
                if (h2.get(k).contains(x) && v2.get(k).contains(x) && b2.get(k).contains(x));
                else    return false; }
        return true;
    }
    
    public int value() {
        return (cell[0][0] * 100 + cell[0][1] * 10 + cell[0][2]);
    }
        
    public void printMatrix() {
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++)
                System.out.print(cell[i][j] + " ");
            System.out.println(); }
        //printHVB();
    }
    public void printHVB() {
        System.out.println(h);
        System.out.println(v);
        System.out.println(b);
    }
}