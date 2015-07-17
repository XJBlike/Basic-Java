//package webserver;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestImp implements Request{
       private StringBuffer in;
       static final Logger logger=LoggerFactory.getLogger(RequestImp.class);
       public RequestImp(InputStream input) {
    	   this.init(input);
		// TODO Auto-generated constructor stub
	}

	public void init(InputStream input) {
		// TODO Auto-generated method stub
		this.in=new StringBuffer(2048);
        byte[] bytes=new byte[2048];
       	  int i;
       	  int BUF_SIZE=1024;
       	  try{
       	  i=input.read(bytes, 0, BUF_SIZE);
       	  }catch(IOException e){
       		  e.printStackTrace();
       		  i=-1;
       	  }
       	  for(int j=0;j<i;j++)
       		  this.in.append((char)bytes[j]);
       	  logger.info("\n{}\n",in.toString());   
       	  //System.out.println(in.toString());
	}


	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		 String Method=null;
		  int index;
		  if((index=in.indexOf(" "))>-1)
		  Method= in.substring(0, index);
		  return Method;
	}


	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		int index;
		   String req=null;
		   if((index=in.indexOf("\n"))>-1)
		   {
		    req=in.substring(0,index);
		   }
		   int index1=req.indexOf(" ");
		   if(index1>-1)
		   {
			   int index2=req.indexOf(" ", index1+1);
		       if(index2>index1)
		       {
		    	   return req.substring(index1+1, index2);
		       }
		       else
		    	   return null;
		   }  
		   else 
			   return null;
	}


	@Override
	public String getHttpVersion() {
		// TODO Auto-generated method stub
		 int index;
		   String req=null;
		   if((index=in.indexOf("\n"))>-1)
		   {
		    req=in.substring(0,index);
		   }
		   int index1=req.lastIndexOf(' ');
		   return req.substring(index1+1);
	}


	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		 int index1;
		   if((index1=in.indexOf("\n"))>-1)
		   {
			   int index2=in.indexOf("\n", index1+1);
			   if(index2>index1){
				   String hostLine=in.substring(index1+1, index2);
				   return hostLine.substring(6);
			   }
			   else 
				   return null;
		   }
		   else
			   return null;
	}


	@Override
	public String getConnection() {
		// TODO Auto-generated method stub
		 int index1=in.indexOf("Connection");
		   if(index1>-1){
		     int index2=in.indexOf("\n", index1+1);
		     if(index2>index1){
		    	 return in.substring(index1+12, index2);
		     }
		     else 
		    	 return null;
		   }
		   else
			   return null;
	}
	   
	   
}
