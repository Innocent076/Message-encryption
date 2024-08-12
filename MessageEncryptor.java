
package za.ac.tut.encryption;


public class MessageEncryptor {
    private static final int SHIFT_Value = 3;
    
    public static String encryptMessage(String plainMessage){
         char[] characterArray = plainMessage.toCharArray();
                 
        for(int i = 0;i<characterArray.length;i++){
            char ch = characterArray[i];
            
            if(ch>= 'A' && ch <= 'Z'){
                ch = (char)(ch + SHIFT_Value);
                if(ch > 'Z'){
                    ch = (char) (ch - 26);
                    
                }
            }else if(ch >= 'a' && ch <= 'z'){
                ch = (char) (ch + SHIFT_Value);
                if(ch > 'z'){
                    ch = (char) (ch - 26);
                }
            }
            characterArray[i] = ch;
        }
        return new String(characterArray);
    }
}
