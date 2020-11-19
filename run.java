import java.util.ArrayList; // import the ArrayList class
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;


class Token {
    private static final int q0 = 0;
    private static final int KEY = 1;
    private static final int ADD1 = 2;
    private static final int ADD2 = 3;
    private static final int ADD3 = 4;
    private static final int ADD4 = 5;
    private static final int ADD5 = 6;
    private static final int MUL1 = 7;
    private static final int MUL2 = 8;
    private static final int MUL3 = 9;
    private static final int MUL4 = 10;

    private static final int REG0 = 11;
    private static final int REG1 = 12;
    private static final int REG2 = 13;
    private static final int REG3 = 14;
    private static final int REG4 = 15;
    private static final int REG5 = 16;
    private static final int REG6 = 17;

    private static final int DOL = 18;
    private static final int FREG0 = 19;
    private static final int FREG1 = 20;
    private static final int FREG2 = 21;
    private static final int FREG3 = 22;
    private static final int FREG4 = 23;
    private static final int FREG5 = 24;
    private static final int FREG6 = 25;

    private static final int ERROR = -1;
    enum TokenType {
        GPR,
        FPR,
        KEYWORD,
        ERROR
    }
    
    public TokenType tokenType;
    public String lexeme;

    public Token(String word){
        this.tokenType = identifyToken(word);
        this.lexeme = word;
    }

    public TokenType getTokenType(){
        return this.tokenType;
    }

    public static TokenType identifyToken(String word){
        int state = q0;
        for(char c: word.toCharArray()){
            state = DFA(state, c);
        }

        if(state == ADD4){
            return Token.TokenType.KEYWORD;
        }else if(state == MUL4){
            return Token.TokenType.KEYWORD;
        }else if(state == REG1){
            return Token.TokenType.GPR;
        }else if(state == REG2){
            return Token.TokenType.GPR;
        }else if(state == REG3){
            return Token.TokenType.GPR;
        }else if(state == REG4){
            return Token.TokenType.GPR;
        }else if(state == REG5){
            return Token.TokenType.GPR;
        }else if(state == REG6){
            return Token.TokenType.GPR;
        }else if(state == FREG1){
            return Token.TokenType.FPR;
        }else if(state == FREG2){
            return Token.TokenType.FPR;
        }else if(state == FREG3){
            return Token.TokenType.FPR;
        }else if(state == FREG4){
            return Token.TokenType.FPR;
        }else if(state == FREG5){
            return Token.TokenType.FPR;
        }else if(state == FREG6){
            return Token.TokenType.FPR;
        }
        return Token.TokenType.ERROR;
    }

    private static int DFA(int s, char c){
        switch (s){
            //KEYWORD
            case q0: switch(c){
                case 'D': return KEY;
                case 'R': return REG0;
                case '$': return DOL;
                case 'F': return FREG0;
                default: return ERROR;
            }
            case KEY: switch (c){
                case 'A': return ADD1;
                case 'M': return MUL1;
                default: return ERROR;
            }
            case ADD1: switch(c){
                case 'D': return ADD2;
                default: return ERROR;
            }
            case ADD2: switch(c){
                case 'D': return ADD3;
                default: return ERROR;
            }
            case ADD3: switch(c){
                case 'U': return ADD4;
                case 'I': return ADD5;
                default: return ERROR;
            }
            case ADD5: switch(c){
                case 'U': return ADD4;
                default: return ERROR;
            }
            case MUL1: switch(c){
                case 'U': return MUL2;
                default: return ERROR;
            }
            case MUL2: switch(c){
                case 'L': return MUL3;
                default: return ERROR;
            }
            case MUL3: switch(c){
                case 'T': return MUL4;
                default: return ERROR;
            }
            case MUL4: switch(c){
                case 'U': return MUL4;
                default: return ERROR;
            }
            

            //REGISTERS
            case REG0: switch(c){
                case '0': return REG1;
                case '1':
                case '2': 
                    return REG2;
                case '3':
                    return REG3;
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return REG4;
                default: return ERROR;
            }

            case REG2: switch(c){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return REG5;
                default: return ERROR;
            }

            case REG3: switch(c){
                case '0':
                case '1':
                case '2':
                    return REG6;
                default: return ERROR;
            }

            //DOLLAR SIGN
            case DOL: switch(c){
                case 'F': return FREG0;
                case '0': return REG1;
                case '1':
                case '2': 
                    return REG2;
                case '3':
                    return REG3;
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return REG4;
                default: return ERROR;
            }

            //FLOAT REGISTERS

            case FREG0: switch(c){
                case '0': return FREG1;
                case '1':
                case '2': 
                    return FREG2;
                case '3':
                    return FREG3;
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return FREG4;
                default: return ERROR;
            }

            case FREG2: switch(c){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    return FREG5;
                default: return ERROR;
            }

            case FREG3: switch(c){
                case '0':
                case '1':
                case '2':
                    return FREG6;
                default: return ERROR;
            }

            default: return ERROR;
        }
    }
    
}

class LexicalAnalyzer {
    ArrayList<Token> process(String sourceCode){
        String[] words = sourceCode.split("[\\s|,|\n]+");
        ArrayList<Token> tokenList = new ArrayList<Token>();
        for (String w: words) {
            Token t = new Token(w);
            tokenList.add(t);
        }

        return tokenList;
    }
}

public class run{
    
    public static void main(String[] args){
        LexicalAnalyzer lexAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenList = new ArrayList<Token>();
        BufferedReader reader;
        BufferedWriter output;
        FileWriter file;
        
        
		try {
			reader = new BufferedReader(new FileReader("input.txt"));
            file = new FileWriter("output.txt");
            output = new BufferedWriter(file);
            String line = reader.readLine();
            tokenList = lexAnalyzer.process(line);

            for(Token t: tokenList){
                output.write(t.getTokenType() + " ");
            }
            output.write("\n");
			while (line != null) {
                line = reader.readLine();
                if(line!=null){
                    if (line.length() != 0){
                        tokenList = lexAnalyzer.process(line);
                        for(Token t: tokenList){
                            output.write(t.getTokenType() + " ");
                        }
                        output.write("\n");
                    } else if (line.length() == 0){
                        output.write("\n");
                    }
                }
			}
            output.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
        Token newToken = new Token("R3");
    }
}