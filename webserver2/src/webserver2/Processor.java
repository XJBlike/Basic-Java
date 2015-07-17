//package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Processor extends Thread{
          public final String WEB_ROOT="D:";
          public Request request;
          public Response response;
          private Socket socket;
          static final Logger logger=LoggerFactory.getLogger(Processor.class);

          public  Processor(Socket socket){
        	  this.socket=socket;
        	  try{
            	  request=new RequestImp(this.socket.getInputStream());
            	  response=new ResponseImp(this.socket.getOutputStream());
       
            	  }catch(IOException e){
            		  e.printStackTrace();        		  
            	  }
          }
        
          public void close(){
        	  if(request.getConnection().equals("close"))
        	  {
        	  try{
        		  socket.close();
        		  logger.info("\nsocket is closed!\n");
        	  }catch(IOException e){}
        	  }
          }         
          
          public void run(){
        	  try{
        	  if(request.getMethod().equals("GET")){
        		  String uri=request.getUri();
        		  if(uri.endsWith("/exit")){
        			  logger.info("WebServer is closed!");
        			  System.exit(0);       			  
        		  }
        		  String uri2=decode(uri);
        		  analyzeFile(WEB_ROOT+uri2);   
        		  close();
        	  }
        	  else{
        	  response.setStatus(403);
      		  response.addHeader("Content-type", "text/html");
      		  dealError("403 Client invoke error");
      		  response.addBody(dealError("403 Client invoke error"));
      		  response.send();
        	  }
          }catch(NullPointerException e){}
          }
          
          public String decode(String ChineseUrl){
          	  Decode URL=new Decode();
          	  if(URL.isUtf8Url(ChineseUrl)){
                   return URL.Utf8URLdecode(ChineseUrl);
                    }else{
                    return URLDecoder.decode(ChineseUrl);
                    }
          }
          
          public void analyzeFile(String filename){
          	  File file=new File(filename);
          	  if(!file.exists()){
          		 response.setStatus(404);
          		 response.addHeader("Content-type", "text/html");
          		 response.addBody(dealError("404 File not found"));
          		 response.send();
          	  }
          	  else if(file.isDirectory()){         		  
          		  response.setStatus(200);
          		File[] files = file.listFiles();
        		StringBuffer sb = new StringBuffer();
        		if(file.getName().length()!=0){ 
        			int nameLength=file.getName().length();
        		       sb.append("<a href="+file.getAbsolutePath().substring(2,file.getAbsolutePath().length()-nameLength)+">"+"返回上一级目录"+"</a>"+"<br/>");
        		}
        		for (int i = 0; i < files.length; i++) {
        			if (files[i].isDirectory()) {    				      				
        				sb.append("<a href="+ files[i].getAbsolutePath().substring(2)+">"+(i+1)+".Dir:  "+files[i].getName()+"</a>" + "<br/>");     			      		
        			}
        			else{
        				if(!files[i].getName().endsWith(".html"))
        				   sb.append("<a href=" + files[i].getAbsolutePath().substring(2) +" download="+files[i].getName()+">"+(i+1)+".File:  "+files[i].getName()+"</a>"+ "<br/>");
        				else
        					 sb.append("<a href=" + files[i].getAbsolutePath().substring(2) +">"+(i+1)+".File:  "+files[i].getName()+"</a>"+ "<br/>");
        			}
        		}
          		  response.addHeader("Content-type", "text/html");
          		  response.addBody(dealDir(sb.toString()));
          		  response.send();
          	  }
          	  else if(filename.endsWith(".html")){
          		 try{
          		     InputStream in=new FileInputStream(file);
          		     byte[] content=new byte[(int)file.length()];
          		     in.read(content);
          		     in.close();
          			 response.setStatus(200);
          		     response.addHeader("Content-Type","text/html;charset=utf-8");
          		     response.addBody(content);
          		     response.send();
          		 }catch(IOException e){
          			 e.printStackTrace();
          		 }
          	  }
          	  else{
          		  try{
          		  InputStream in=new FileInputStream(file);
          		  byte[] content=new byte[(int)file.length()];
          		  in.read(content);
          		  in.close();
          		 response.setStatus(200);
          		 response.addHeader("Content-length",""+content.length);
          		 response.addBody(content);
                 response.send();
          		  }catch(IOException e){
          			  e.printStackTrace();
          		  }  
            }          	
          }     
          
          public String dealDir(String body){
        	  return "<html>\n"+"<head><title>XJB File Browser</title></head>\n"+body+"\n</html>"+"\n\n";
          }
          
          public String dealError(String body){
        	  return "<html>\n"+"<head><title>XJB ERROR</title></head>\n"+body+"\n</html>"+"\n\n";
          }
}
