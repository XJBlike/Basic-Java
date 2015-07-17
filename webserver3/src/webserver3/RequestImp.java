package webserver3;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;


public class RequestImp implements Request{
       private HashMap hm;
       private String requestLine;
       static final Logger logger=LoggerFactory.getLogger(RequestImp.class);
       public RequestImp(InputStream input) {
    	   this.hm=new HashMap();
    	   this.requestLine=new String("XJB");
    	   this.init(input);
		// TODO Auto-generated constructor stub
	}
    
     public void init(InputStream input){
    	 BufferedReader br=new BufferedReader(new InputStreamReader(input));
    	 try{
    	 requestLine=br.readLine();
    	 logger.info(requestLine);
    	 String temp;
    	 while((temp=br.readLine()).length()!=0){
    		 logger.info(temp);
    		 String temp1=temp.substring(0, temp.indexOf(":"));
    		 String temp2=temp.substring(temp.indexOf(":")+1);
    		 hm.put(temp1, temp2);
    	 }
    	 logger.info("\n");
    	 }catch(IOException e){
    		 logger.info(e.toString());
    	 }
    	 
     }

	@Override
	public String getMethod() {
		  int index;
		  if((index=requestLine.indexOf(" "))>-1)		  
		  return  requestLine.substring(0, index);
		  else
			  return null;
	}


	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		   int index1=requestLine.indexOf(" ");
		   if(index1>-1)
		   {
			   int index2=requestLine.indexOf(" ", index1+1);
		       if(index2>index1)
		       {
		    	   return requestLine.substring(index1+1, index2);
		       }
		       else
		    	   return null;
		   }  
		   else 
			   return null;
	}


	@Override
	public String getHttpVersion() {
		   return requestLine.substring(requestLine.lastIndexOf(' ')+1);
	}


	@Override
	public String getValue(String key){
		return (String)hm.get(key);
	}
	   
}
