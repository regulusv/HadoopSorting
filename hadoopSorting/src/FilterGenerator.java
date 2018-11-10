
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class FilterGenerator {
    public static void generate() throws FileNotFoundException {
        double wantedSize = 10;

        Random random = new Random();
        File file = new File(Constants.UNSORTED_FILE_NAME);
        long start = System.currentTimeMillis();
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)), false);
        int counter = 0;
        int index = 0;
        while (true) {
            for (int i = 0; i < 100; i++) {
                int number = random.nextInt(Integer.MAX_VALUE);
                writer.println(index++ + Constants.KEY_VALUE_DIVIDER + number);
            }
            //Check to see if the current size is what we want it to be
            if (++counter == 10000) {
                System.out.printf("Current file size: %.3f GB%n", file.length() / 1e9);
                if (file.length() >= wantedSize * 1e9) {
                    writer.close();
                    break;
                } else {
                    counter = 0;
                }
            }
        }
        long time = System.currentTimeMillis() - start;
        System.out.printf("Took %.1f seconds to create a file of %.3f GB", time / 1e3, file.length() / 1e9);
    }
}
