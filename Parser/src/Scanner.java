import java.util.ArrayList;
import java.util.Collections;

public class Scanner {
    ArrayList<Token> process (String line){
        String[] temp = line.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String l: temp){
            char[] tempchar = l.toCharArray();
            StringBuilder wordbuilder = new StringBuilder();
            for (char c: tempchar) {
                if( c == '+' || c == '*' ||
                    c == '[' || c == ']' ||
                    c == '(' || c == ')'){
                    if(!wordbuilder.toString().equals("")){
                        words.add(wordbuilder.toString());
                    }
                    wordbuilder = new StringBuilder(String.valueOf(c));
                    words.add(wordbuilder.toString());
                    wordbuilder = new StringBuilder();
                }else{
                    wordbuilder.append(c);
                }
            }
            if(!wordbuilder.toString().equals("")){
                words.add(wordbuilder.toString());
            }
        }
        ArrayList<Token> tokenList = new ArrayList<>();
        for (String w: words) {
            if(!w.equals("")){
                tokenList.add(new Token(w));
            }
        }
        return tokenList;
    }
}
