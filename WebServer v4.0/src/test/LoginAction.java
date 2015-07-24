package test;

import java.io.File;
import java.io.IOException;
import java.sql.Statement;



public class LoginAction extends AbstractAction implements Action {
	private analyze fileHandle;
    
	public LoginAction(){
		fileHandle=new analyze();
	}
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/login.html";
	}
    
	public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		String temp=request.getPostData();
		 String logs[]=temp.split("&");
		 String user[]=logs[0].split("=");
		 String pwd[]=logs[1].split("=");
		 if(Sql.signIn(user[1], pwd[1])){
			 Sql.setCurrentUser(user[1], pwd[1]);
			 fileHandle.analyzeFile("/",response,Sql);
			 Session session=sessionMgr.getSession(request);
			 session.setValue("loginstate", "true");
		 }
		 else
			 fileHandle.analyzeFile("/web/login.html",response,Sql);
		super.onPost(sessionMgr, request, response,Sql);
	}
	
	
}
