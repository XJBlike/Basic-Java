package test;


public class Cookie {
    private String value;
    private String key;
    
    public Cookie(String cookie){
    	String[] temp={};
    	temp=cookie.split("=");
    	if(temp.length==2){
    		this.key=temp[0];
    		this.value=temp[1]; 	
    	}
    }
    
    public boolean exist(){
    	if(value.isEmpty()){
    		return false;
    	}
    	else
    		return true;
    }
    
    public String get(String key){
    	if(key.equals(this.key))
    	return value;
    	else
    		return null;
    }
}
