package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

public class analyze {
	public void analyzeFile(String filename,Response response,SQL Sql){
		File file=new File("D:"+filename);
		Caculator caculator=new Caculator();
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
  		sb.append("<h1>"+file.getAbsolutePath()+"的索引</h1><br/>");
  		if(file.getName().length()!=0){ 
  			int nameLength=file.getName().length();
  		       sb.append("<font size=\"5\"><a href="+file.getAbsolutePath().substring(2,file.getAbsolutePath().length()-nameLength)+">"+"返回上一级目录"+"</a></font>"+"<br/>");        		      
  		}
  		if(Sql.getCurrentUser().equals("XJB")&&file.getName().length()==0){
	    	   sb.append("<font size=\"5\"><a href=\"/web/show.html\">"+"用户管理"+"</a>   <a href=\"/web/xjb.html\">about"+"</font><br/>");
	       }
  		for (int i = 0; i < files.length; i++) {
  			if (files[i].isDirectory()) {    				      				
  				if(!files[i].getName().equals("web")){
  					int nameLength=file.getName().length();	
  				sb.append("<font size=\"5\"><a href="+ files[i].getAbsolutePath().substring(2,file.getAbsolutePath().length()-nameLength)+files[i].getName()+">"+(i+1)+".Dir:  "+files[i].getName()+"</a>" + "</font><br/>");     			      		
  				}
  				else if(Sql.getCurrentUser().equals("XJB")){
  					int nameLength=file.getName().length();	
  					sb.append("<font size=\"5\"><a href="+ files[i].getAbsolutePath().substring(2,file.getAbsolutePath().length()-nameLength)+files[i].getName()+">"+(i+1)+".Dir:  "+files[i].getName()+"</a></font><br/>"); 
  				}
  			}
  			else{
  				if(!files[i].getName().endsWith(".html"))
  				   sb.append("<font size=\"5\"><a href=" + files[i].getAbsolutePath().substring(2) +">"+(i+1)+".File:  "+files[i].getName()+"</a></font><br/>");
  				else
  					 sb.append("<font size=\"5\"><a href=" + files[i].getAbsolutePath().substring(2) +">"+(i+1)+".File:  "+files[i].getName()+"</a></font><br/>");
  			}
  		}
  	          sb.append("<font size=\"5\"><a href=\"/web/logout.html\">log out</a></font><br/>");
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
