package util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt implements AutoCloseable {
    private Scanner sc;

    public Prompt(InputStream in) {
        sc = new Scanner(in);
    }



    @Override
    public void close() throws Exception {
        sc.close();
    }

    public String input() {
        return sc.nextLine();
    }
}
