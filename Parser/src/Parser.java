import java.io.*;
import java.util.*;

public class Parser {
    private LinkedHashMap<String, Rule> rules = new LinkedHashMap<>();
    private Stack<String> stack = new Stack<>();
    private Stack<String> temp_stack = new Stack<>();
    private String first = "";

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
        }

    }


    public String parse(ArrayList<Token> tokens){
        this.stack.clear();
        this.stack.push("E"); //push starting symbol
        int token_pointer = 0;
        int rule_index = 0;
        Rule curr_rule = null;

        while(!stack.isEmpty()){
            Token currentToken = null;
            boolean isToken = Token.checkIfToken(this.stack.peek());

            if(token_pointer < tokens.size())
                currentToken = tokens.get(token_pointer);

            if(currentToken != null && currentToken.getTokenType().equals("ERROR")){
                return " - REJECT. Offending token '" + currentToken.getLexeme() +"'";
            }
            else if(isToken && currentToken != null && this.stack.peek().equals(currentToken.getTokenType())) {
                token_pointer++;
                System.out.println("MATCH: " + this.stack.pop());
                rule_index = 0;
                if(!temp_stack.isEmpty()){
                    temp_stack.pop();
                }
            } else if (stack.peek().equals("")) {
                System.out.println("EPSILON");
                stack.pop();
                rule_index = 0;
            } else{
                if(!isToken){
                    curr_rule = rules.get(this.stack.peek());
                    ArrayList<String> RHS = curr_rule.getRHS();
                    if(rule_index < RHS.size())
                        expand(RHS.get(rule_index));
                    else if(currentToken != null)
                        return " - REJECT. Offending token '" + currentToken.getLexeme() +"'";
                    else return " - REJECT. Offending token '" + tokens.get(token_pointer-1).getLexeme()+"'";
                } else if(this.stack.peek().equals("RPAREN")){
                    return " - REJECT. Offending token '('";
                } else if(this.stack.peek().equals("RBRACKET")) {
                    return " - REJECT. Offending token '['";
                } else{
                    rule_index++;
                    performBacktrack(curr_rule);
                }

            }

            if(token_pointer == tokens.size() && stack.isEmpty()){
//                noRule = false;
                return " - ACCEPT";
            }

            System.out.println("Stack: " + stack);
        }

        return " - REJECT. Offending token '" + tokens.get(token_pointer).getLexeme() +"'";

    }

    public void expand(String production){
        if(stack.isEmpty()){
            return;
        }
        this.temp_stack.clear();
        System.out.println("EXPAND: " + stack.pop());
        String[] temp = production.split(" ");
        for(int i = temp.length-1; i >= 0; i--){
            this.stack.push(temp[i]);
            this.temp_stack.push(temp[i]);
        }

    }

    public void performBacktrack(Rule currentRule){
        if(stack.isEmpty()){
            return;
        }

        while(!temp_stack.isEmpty()){
            stack.pop();
            temp_stack.pop();
        }
        String LHS = currentRule.getLHS();
        System.out.println("Backtrack - adding: " + LHS);
        stack.push(LHS);
    }

}
