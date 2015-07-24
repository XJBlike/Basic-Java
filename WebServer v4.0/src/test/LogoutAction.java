package test;

public class LogoutAction extends AbstractAction implements Action {
    private analyze fileHandle;
    public LogoutAction(){
    	fileHandle=new analyze();
    }
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/logout.html";
	}
    
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		   String filename=request.getUri();
		   String uri2=decode(filename);
		  fileHandle.analyzeFile(uri2,response,Sql);
		  Session session=sessionMgr.getSession(request);
			 session.setValue("loginstate", "false");
			 super.onGet(sessionMgr, request, response, Sql);
	}
}
