package nasp.lab12;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        RBStablo<Integer> stablo = new RBStablo<Integer>();
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        while (n != 4) {
            System.out.println("\nZa umetanje novog čvora u stablo, unesite 1\n" +
                    "Za INORDER ispis unesite 2\n" +
                    "Za brisanje unesite 3\n" +
                    "Za izlaz unesite 4\n");
            n = scanner.nextInt();
            if (n == 1) {
                int element = scanner.nextInt();
                //int[] niz = {6, 11, 10, 9, 7, 5, 13, 22, 27, 36, 12, 31};
                //for (int i = 0; i < 12; i++) {
                //    int element = niz[i];
                    stablo.insert(new Cvor(element));
               // }
            } else if (n == 2) {
                System.out.println("\n");
                stablo.ispisInorder();
            } else if (n == 3) {
                int element = scanner.nextInt();
                stablo.deleteKey(element);
            } else if (n == 4) {
                System.exit(1);
            } else System.out.println("Uneseni broj opcije nije validan!");
        }
    }
}
