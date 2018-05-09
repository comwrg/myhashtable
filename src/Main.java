import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) throws IOException {
        for (int i = 100; i <= 1e6; i *= 10) {
            long hs = testHashtable(i);
            long myhs = testMyHashtable(i);
            String win;
            if (hs > myhs) {
                win = "myHashtable";
            } else {
                win = "Hashtable";
            }
            System.out.printf("n:%10d, Hashtable: %10fs, myHashtable: %10fs, win: %s\n", i, hs / 1e9, myhs / 1e9, win);
        }
    }
    public static long testMyHashtable(int n) throws IOException {
        long start, end;
        BufferedReader br;
        start = System.nanoTime();
        MyHashtable<String, String> hs = new MyHashtable<>(n);
        br = new BufferedReader(new FileReader("./10-million-combos.txt"));
        for (int i = 0; i < n; ++i) {
            String[] arr = br.readLine().split("\t");
            if (arr.length == 2)
                hs.put(arr[0], arr[1]);
        }
        br.mark(0);
        br.reset();
        for (int i = 0; i < n; ++i) {
            String[] arr = br.readLine().split("\t");
            String v = hs.get(arr[0]);
        }
        end = System.nanoTime();
        return end-start;
    }

    public static long testHashtable(int n) throws IOException {
        long start, end;
        BufferedReader br;
        start = System.nanoTime();
        Hashtable<String, String> hs = new Hashtable<>(n);
        br = new BufferedReader(new FileReader("./10-million-combos.txt"));
        for (int i = 0; i < n; ++i) {
            String[] arr = br.readLine().split("\t");
            if (arr.length == 2)
                hs.put(arr[0], arr[1]);
        }
        br.mark(0);
        br.reset();
        for (int i = 0; i < n; ++i) {
            String[] arr = br.readLine().split("\t");
            String v = hs.get(arr[0]);
        }
        end = System.nanoTime();
        return end-start;
    }
}
