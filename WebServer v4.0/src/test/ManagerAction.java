package test;

public class ManagerAction extends AbstractAction implements Action{
    private analyze fileHandle;
    public ManagerAction(){
    	fileHandle=new analyze();
    }
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/manager";
	}
	
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		 String uri=request.getUri(); 
		  Sql.createManager(uri);
		  fileHandle.analyzeFile(uri,response,Sql);
		  super.onGet(sessionMgr, request, response, Sql);
	}
}
