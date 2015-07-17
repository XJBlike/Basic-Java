package webserver3;

import java.io.*;
import java.util.concurrent.*;
import java.net.*;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebServer {
	  static final Logger logger=LoggerFactory.getLogger(WebServer.class);
      private ServerSocket serversocket=null;
      private int port;
      /*
      public static void main(String[] args)throws NullPointerException{
    	  WebServer server=new WebServer();  	
    	  logger.info("\nInput a port number:");
    	  Scanner sc=new Scanner(System.in);
    	  int port=sc.nextInt();
    	  sc.close();
    	  server.run(port);
    	  }
         */   
      public void setPort(int port){
    	  this.port=port;
      }
      
      public int getPort(){
    	  return this.port; 
      }
      
      public void run(){	  
    	try{
    	  serversocket=new ServerSocket(port);
    	  logger.info("\nWebServer is listening to port:{}\n",serversocket.getLocalPort());
    	}catch(IOException e){
    		logger.info("\n{}\n",e.toString());
    	}
    	ExecutorService pool=Executors.newFixedThreadPool(20);
    	while(true){
    		try{
    		Socket socket=serversocket.accept();
    		Thread t=new Processor(socket);
    		pool.execute(t); 		
    		}catch(IOException e){
    			e.printStackTrace();
    			logger.info("\n{}\n",e.toString());
    		}
    	}
      }     
}
