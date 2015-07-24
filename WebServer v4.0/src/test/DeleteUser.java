package test;

public class DeleteUser extends AbstractAction implements Action {
    private analyze fileHandle;
    
    public DeleteUser(){
    	fileHandle=new analyze();
    }
	
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/delete";
	}
	
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		String uri=request.getUri(); 
		Sql.createDelete(uri);
		fileHandle.analyzeFile(uri, response, Sql);
		super.onGet(sessionMgr, request, response, Sql);
	}

}
