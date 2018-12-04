import java.io.*;

public class CCipher {
    //this is our cipher shift. change the int to change how much the characters are shifted.
    int shift = 5;
  public static final  String message = ("TRY A DIFFERENT PHRASE SEE IF IT WORKS");

    char [] alphashift = new char[26];
    char [] [] cipher = new char [26] [2];
    char [] messagechar = new char [message.length()];
    char [] coddedmessage = new char [message.length()];
    char [] alphabet = 
            {'A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z' };

    public CCipher(int sft){
        this.shift = sft;
        this.initializeCipherArray(shift);
        
        System.out.println(this.message);
        System.out.println("~~~~~~~~~~~~~");

            String ans = this.encode(this.message);
            String ans2 = this.decode(ans);
            
            System.out.println( ans );
            System.out.println( ans2 );
            System.out.println("~~~~~~~~~~~~~");
            
            
    }
    public static void main(String[] args) {
            CCipher tester = new CCipher(5);
            
    }

    public void initializeCipherArray( int shift ){
            //Shifts the alphabet and adds it to the alphashift array
            for ( int i = 0; i < 26; i++ ){
               char alphabet_cur = alphabet[i];
                    alphashift[i] = alphabet[this.TrueMod(i+this.shift, 26)];
                    char shift_cur = alphashift[i];
                    cipher [i] [0] = alphabet_cur;
                    cipher [i] [1] = shift_cur;
            }
    }

    public char [ ] getCipherArray( ){
            return alphashift;

    }
        
    public String encode( String message ){
            char[] encode = new char[message.length()];
            //this indexes it and encodes the characters and stores it as char code
            for (int i = 0; i < message.length();i++ ){
                    char current = message.charAt(i);
                    char code = '•';
                    if (current==(' ')){
                        continue;
                    }
                    for (int x = 0; x < 26; x++) {
                        if (current==(cipher [x] [0])) {
                          code = cipher [x] [1];
                          encode[i] = code;
                         
                    } 
                }
            }	
            return charArray2String(encode);
        }

    public String charArray2String(char[] msg){
        String ans = "";
        for (int x = 0; x<msg.length; x++){
            ans += msg[x];
            
        }
        return ans;
    }
    public String decode( String secret ){
            char[] encode = new char[secret.length()];
            //this indexes it and decodes the characters and stores it as char code
            for (int i = 0; i <  secret.length(); i++ ){
                    char current = secret.charAt(i); // Read from SECRET not MESSAGE!!!
                    char code = '•';
                    if (current==(' ')){
                        continue;
                    }

                    for (int x = 0; x < 26; x++) {
                        
                        if (current==(cipher [x] [1])) {
                            code  = cipher [x] [0];
                            encode [i] = code;
                        } 
                    }
            }	
            return charArray2String(encode);
    }
        
    
	
    public void Cipher(String message){

        char [] codeArray = message.toCharArray();

        System.out.println(message);

    }
    
    public int TrueMod(int A, int B){
        // A % B
        return (A % B + B) % B;
    }
}
