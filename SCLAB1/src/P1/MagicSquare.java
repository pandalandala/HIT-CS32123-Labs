package P1;
import java.util.*;
import java.io.*;

public class MagicSquare {
    static final int N = 200;
    public static int[][] square = new int[N][N];
    public static boolean[] visited = new boolean[N*N+1];

    public static void main(String[] args) throws IOException {
        for (char i = '1'; i <= '5'; i++) {
            System.out.print(i + " ");
            System.out.println(String.valueOf(isLegalMagicSquare("src/P1/txt/" + i + ".txt")));
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        while (n <= 0 || n % 2 == 0) {
            System.out.println("InputWrongly!");
            n = scanner.nextInt();
        }
        generateMagicSquare(n);
        System.out.println("6" + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + "6" + ".txt")));
        return;
    }

    public static boolean isLegalMagicSquare(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        int n = 0, m = 0;
        Arrays.fill(visited, false);
        while ((line = bufferedReader.readLine()) != null) {
            String[] l = line.split("\t");
            m = l.length;
            for (int i = 0; i < m; i++) {
                try {
                    square[n][i] = Integer.valueOf(l[i].trim());
                }
                catch(NumberFormatException e) {
                    System.out.print("NotAvailableException");
                    return false;
                }
                if (square[n][i] <= 0) {
                    System.out.print("NegativeNumberException");
                    return false;
                }
                else if (visited[square[n][i]]) {
                    System.out.print("RepeatedNumberException");
                    return false;
                }
                else
                    visited[square[n][i]] = true;
            }
            n++;
        }
        BufferedReader.close();
        if (n != m || n <= 0 || m <= 0) {
            System.out.print("NonSquareExcption");
            return false;
        }
        int s1 = 0, s2 = 0, s = 0;
        for (int i = 0; i < n; i++) {
            s1 += square[i][i];
            s2 += square[n - i - 1][i];
        }
        if (s1 == s2)
            s = s1;
        else {
            System.out.print("DifferentSumException");
            return false;
        }
        for (int i = 0; i < n; i++) {
            s1 = s2 = 0;
            for (int j = 0; j < n; j++) {
                s1 += square[i][j];
                s2 += square[j][i];
            }
            if (s1 != s || s2 != s) {
                System.out.print("DifferentSumException");
                return false;
            }
        }
        return true;
    }

    public static boolean generateMagicSquare(int n) throws IOException {
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        File file = new File("src/P1/txt/6.txt");
        PrintWriter output = new PrintWriter(file);
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                output.print(magic[i][j] + "\t");
            output.println();
        }
        output.close();
        return true;
    }
}