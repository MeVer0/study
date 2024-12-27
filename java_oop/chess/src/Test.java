import java.io.*;

public class Test {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            System.out.println("You entered: " + input);
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }
}