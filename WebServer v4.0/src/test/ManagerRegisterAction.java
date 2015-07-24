package test;

public class ManagerRegisterAction extends AbstractAction implements Action {

    private analyze fileHandle;
    
	public ManagerRegisterAction(){
		fileHandle=new analyze();
	}
	
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/manager_register.html";
	}
    
	public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		 String temp=request.getPostData();
		 String logs[]=temp.split("&");
		 String user[]=logs[0].split("=");
		 String pwd[]=logs[1].split("=");
		   if(Sql.register(user[1], pwd[1])){
			   fileHandle.analyzeFile("/web/manager_register_success.html",response,Sql);       					 
			   Session session=sessionMgr.getSession(request);
				 session.setValue("loginstate", "true");
		   }
		   else{
			   fileHandle.analyzeFile("/web/manager_register_fail.html",response,Sql);
		   }
		super.onPost(sessionMgr, request, response,Sql);
	}

}
