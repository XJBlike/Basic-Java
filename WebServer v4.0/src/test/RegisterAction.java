package test;

import java.io.File;
import java.io.IOException;

public class RegisterAction extends AbstractAction implements Action {

	
   private analyze fileHandle;
    
	public RegisterAction(){
		fileHandle=new analyze();
	}
	
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/register.html";
	}
    
	public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		 String temp=request.getPostData();
		 String logs[]=temp.split("&");
		 String user[]=logs[0].split("=");
		 String pwd[]=logs[1].split("=");
		   if(Sql.register(user[1], pwd[1])){
			   Sql.setCurrentUser(user[1], pwd[1]);
			   fileHandle.analyzeFile("/",response,Sql);       					 
			   Session session=sessionMgr.getSession(request);
				 session.setValue("loginstate", "true");
		   }
		   else{
			   fileHandle.analyzeFile("/web/register_fail.html",response,Sql);
		   }
		super.onPost(sessionMgr, request, response,Sql);
	}
}
