package test;

import java.io.*;
import java.sql.*;

import javax.swing.JOptionPane;

import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor extends Thread{
          public final String WEB_ROOT="D:";
          public Request request;
          public Response response;
          private Socket socket;
          private Connection cnn;
          private Statement statement;
          private SQL Sql;
          static final Logger logger=LoggerFactory.getLogger(Processor.class);
          private Map<String,Action> actions;
          private SessionManager sessionMgr;
          public  Processor(Socket socket,Map<String, Action> actions,SessionManager sessionMgr){
        	  this.socket=socket;
        	  this.actions=actions;
        	  this.sessionMgr=sessionMgr;
        	  try{
            	  request=new RequestImp(this.socket.getInputStream());
            	  response=new ResponseImp(this.socket.getOutputStream());
            	  cnn=Jdbc.getconnection();
            	try{
            	  statement=cnn.createStatement();
            	  this.Sql=new SQL(statement);
            	}catch(SQLException se){se.printStackTrace();}
            	  }catch(IOException e){
            		  e.printStackTrace();        		  
            	  }
          }
        
          public void close(){
        	  if(request.getUri().endsWith("/exit")){
        		  logger.info("webserver is closing!");
        		  System.exit(0);
        	  }
          }         
          
          public void run(){
        	  try{
        		  Filter ft=new Filter();
        		  ft.filter(sessionMgr, request, response);
        		  close();
        		  String uri=request.getUri();
        	  Action action = this.actions.get(uri);
        	  if(action == null){
        		 int index=uri.indexOf("_");
        		 if(index>-1){
        		 action=this.actions.get(uri.substring(0, index));
        		 }
        		 if(uri.endsWith("/web/login.html")){
        			 action=this.actions.get("/web/login.html");
        		 }
        		  if(action==null){
        		action =  new AllAction();
        		  }
        	  }
        	  if("GET".equals(request.getMethod())){
        		  action.onGet(sessionMgr,request, response,Sql);
        	  }else{
        		  action.onPost(sessionMgr,request, response,Sql);       		  
        	  }
          }catch(NullPointerException npe){}
          }
}
