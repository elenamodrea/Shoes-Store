package Ajutor;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Hashtable;

public class CodeGenerator {
    //Hashtable<String, String> h = new Hashtable();
    public String generateCodes(){
        String random= RandomStringUtils.random(6,false, true);
        return random;
    }
    public void addCode(Hashtable<String, String> h,String code, String email){
        h.put(email,code);
    }
    public void deleteCode(Hashtable<String, String> h,String email){
        h.remove(email);
    }

}
