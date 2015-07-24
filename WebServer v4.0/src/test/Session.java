package test;

import java.util.HashMap;

public class Session {
      private HashMap<String,String> session;
      
      public Session(){
    	  session=new HashMap<String,String>();
      }
      
      public String getValue(String key){
    	  return session.get(key);
      }
      
      public void setValue(String key,String value){
    	  if(session.containsKey(key)){
    	  }
    	  session.put(key, value);
      }

}
