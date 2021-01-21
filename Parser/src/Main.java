import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("Parser/IO/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        FileWriter fileWriter = new FileWriter("Parser/IO/output.txt");
        String line;
        Scanner sc = new Scanner();
        StringBuilder output = new StringBuilder();
        Parser parser = new Parser();
        parser.createRules();
        while((line = bufferedReader.readLine()) != null){
            ArrayList<Token> tokens = sc.process(line);
            output.append(line);
            for(Token token: tokens){
                //output.append(token).append(" ");
            }
            String result = parser.parse(line);
            output.append("\n");
        }
        output = new StringBuilder(output.toString().trim());
        System.out.println(output);
        fileWriter.write(output.toString());
        inputStream.close();
        fileWriter.close();
    }
}
