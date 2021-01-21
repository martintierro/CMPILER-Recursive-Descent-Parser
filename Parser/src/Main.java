import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("Parser/IO/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        FileWriter fileWriter = new FileWriter("Parser/IO/output.txt");
        String line;
        Scanner sc = new Scanner();
        String output = "";
        Parser parser = new Parser();
        parser.createRules();
        while((line = bufferedReader.readLine()) != null){
            ArrayList<Token> tokens = sc.process(line);
            for(Token token: tokens){
                output += token + " ";
            }
            String result = parser.parse(line);
            System.out.println(line + " - REJECT. Offending token '"+ result +"'");
            output += "\n";
        }
        output = output.trim();
        System.out.println(output);
        fileWriter.write(output);
        inputStream.close();
        fileWriter.close();
    }
}
