package test;

public class ShowUsers extends AbstractAction implements Action {
    private analyze fileHandle;
    
    public ShowUsers(){
    	fileHandle=new analyze();
    }
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/show.html";
	}
	
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		  Sql.createUserList();
		  fileHandle.analyzeFile("/web/show.html",response,Sql);
		  super.onGet(sessionMgr, request, response, Sql);
	}

}
