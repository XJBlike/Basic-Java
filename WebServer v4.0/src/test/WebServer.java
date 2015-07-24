package test;

import java.io.*;
import java.sql.*;
import java.util.concurrent.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
	  static final Logger logger=LoggerFactory.getLogger(WebServer.class);
      private ServerSocket serversocket=null;
     
      public static void main(String[] args){
    	  WebServer server=new WebServer();  	
    	  logger.info("\nInput a port number:");
    	  Scanner sc=new Scanner(System.in);
    	  int port=sc.nextInt();
    	  sc.close();
    	  server.run(port);
    	  }
            
      public void run(int port){	  
    	Map<String, Action> actions = initActions();
    	SessionManager sessionMgr=new SessionManager();
    	sessionMgr.initSession();
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
    		Thread t=new Processor(socket,actions,sessionMgr);
    		pool.execute(t); 		
    		}catch(IOException e){
    			e.printStackTrace();
    			logger.info("\n{}\n",e.toString());
    		}
    	}
      }

      /**
       * 
       * @return
       */
	private Map<String, Action> initActions() {
		LoginAction login=new LoginAction();
    	RegisterAction register=new RegisterAction();
    	ShowUsers showusers=new ShowUsers();
    	ManagerAction manager=new ManagerAction();
    	DeleteUser delete=new DeleteUser();
    	UpdateAction update=new UpdateAction();
    	ManagerRegisterAction managerregister=new ManagerRegisterAction();
    	LogoutAction logout=new LogoutAction();
    	AboutAction about=new AboutAction();
    	Map<String,Action> actions=new HashMap<String,Action>(9);
    	actions.put(login.getUrl(),login);
    	actions.put(register.getUrl(), register);
    	actions.put(showusers.getUrl(), showusers);
    	actions.put(manager.getUrl(),manager);
    	actions.put(delete.getUrl(), delete);
    	actions.put(update.getUrl(), update);
    	actions.put(managerregister.getUrl(), managerregister);
    	actions.put(logout.getUrl(), logout);
    	actions.put(about.getUrl(), about);
		return actions;
	}     
}
