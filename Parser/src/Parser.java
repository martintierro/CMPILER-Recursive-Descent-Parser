import java.io.*;
import java.util.*;

public class Parser {
    HashMap<String, Rule> rules = new HashMap<>();
    Stack<String> stack = new Stack<>();

    public void createRules() throws IOException {
        InputStream inputStream = new FileInputStream("Parser/IO/grammar.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder grammar = new StringBuilder();

        while((line = bufferedReader.readLine())!= null){
            grammar.append(line.trim());
        }

        String[] rules = grammar.toString().split(";");
        for (String rule: rules) {
            rule = rule.trim();
            String[] temp = rule.split(":");
            String LHS = temp[0].trim();
            Rule r = new Rule(LHS);
            String[] RHS = temp[1].split("\\|");
            for (String right:RHS) {
                if(right.trim().equals("epsilon")){
                    r.addRHS("");
                }else {
                    r.addRHS(right.trim());
                }
            }
            this.rules.put(LHS, r);
            System.out.println(r.getLHS());
            System.out.println(r.getRHS());
        }

    }


    public String parse(String input){
        this.stack.push("E");

        int pointer = 0;
        boolean noRule = false;

        if(noRule){
            System.out.println("ERROR: No proper derivation for input!");
        }

        return "";
    }

    public void expand(String production){
        if(stack.isEmpty()){
            return;
        }

        stack.pop();
        String[] temp = production.split("");
        for(int i = temp.length-1; i >= 0; i--){
            this.stack.push(temp[i]);
        }

    }

    public void performBacktrack(String production){
        if(stack.isEmpty()){
            return;
        }

        stack.pop();
        String LHS = "a";
        stack.push(LHS);
    }



}
