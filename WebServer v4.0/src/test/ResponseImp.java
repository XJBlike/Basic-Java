package test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseImp implements Response{
	private PrintStream output;
	static final Logger logger=LoggerFactory.getLogger(ResponseImp.class);
	ResponseImp(OutputStream os){
		this.output = new PrintStream(os);
	}
	
	@Override
	public void setStatus(int status) {
		// TODO Auto-generated method stub
		 switch(status){		 
		 case 200:{
			 output.println("HTTP/1.1 200 sendFile");
		          }
		 case 404:{
				output.println("HTTP/1.1 404 File not found");
		          } 
		 case 403:{
				output.println("HTTP/1.1 403 Client invoke error");
		          }
		 case 302:{
			     output.println("HTTP/1.1 302 redirect");
		 }
		 default:return;
		 }
	}
	
	@Override
	public void addHeader(String key, String value) {
		StringBuilder header = new StringBuilder(key.length()+value.length()+3);
		header.append(key).append(':').append(value).append("\r\n");
		output.println(header);
	}

	
	@Override
	public void addBody(String body){
		output.println(body);
	}
	
	public void addBody(byte[] body){
		try{
		output.write(body);
		}catch(IOException e){
			e.printStackTrace();
			logger.info(e.toString());
		}
	}
	
	public void send(){
		output.flush();
		output.close();
	}

	@Override
	public void writecookie() {
		// TODO Auto-generated method stub
		Cookie cookie=new Cookie("session=XJBlike");
		output.println("Set-Cookie:session=XJBlike");
	}

	@Override
	public void sendRedirect(String string) {
		// TODO Auto-generated method stub
		output.println("Location:http://localhost:8080"+string);
	}
   
}
