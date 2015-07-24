package test;

import java.util.HashMap;

public class Filter {
       private analyze fileHandle;
       private Session userCookie;
       public Filter(){
    	   fileHandle=new analyze();
    	   userCookie=new Session();
       }
	public void filter(SessionManager sessionMgr, Request request,Response response){
		try{
		if((!"GET".equals(request.getMethod()))&&(!"POST".equals(request.getMethod()))){
  		      response.setStatus(403);
     		  response.addHeader("Content-type", "text/html");
     		  fileHandle.dealError("403 Client invoke error");
     		  response.addBody(fileHandle.dealError("403 Client invoke error"));
     		  response.send();
  	   }
		if(request.getUri().endsWith("/exit")){
			return;
		}
		Session session = sessionMgr.getSession(request);
		if(session == null && !request.getUri().contains("/web/login.html") && !"/web/register.html".equals(request.getUri())&& !request.getUri().contains("/web/update")){
		 response.setStatus(302);	 
   		 response.writecookie();
   		 response.sendRedirect("/web/login.html");
   		 response.addHeader("Content-type", "text/html");
   		 response.addBody(fileHandle.dealError("you need to login!"));
   		 response.send();
		 return;
		}
		String loginstate = session.getValue("loginstate");
		if("true".equals(loginstate)){
			return;
		}
		if(!request.getUri().contains("/web/login.html")&& !"/web/register.html".equals(request.getUri())&& !request.getUri().contains("/web/update")){
		response.setStatus(302);
		response.sendRedirect("/web/login.html");
		response.addHeader("Content-type", "text/html");		
		response.addBody(fileHandle.dealError("you need to login!"));
   		response.send();  
		}
       }catch(NullPointerException npe){}
		return;
	}
}
