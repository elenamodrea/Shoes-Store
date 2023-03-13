package Tables;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RandomID {
    private  Set<String> linkedHashSet;

    public RandomID() {
        this.linkedHashSet = new HashSet<>();
    }

    public  String getRandomID(){
      int length=linkedHashSet.size();
      while(true){
          String random= RandomStringUtils.random(16,true, true);
          linkedHashSet.add(random);
          if(length<linkedHashSet.size())
             return random;}

        }
    public  void afisare(){
        for (String s: linkedHashSet) {
            System.out.println(s);
        }

  }
}
