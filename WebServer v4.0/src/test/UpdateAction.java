package test;

public class UpdateAction extends AbstractAction implements Action {
    private analyze fileHandle;
    
    public UpdateAction(){
    	fileHandle=new analyze();
    }
    
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/update";
	}
	
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		String uri=request.getUri();
		Sql.createUpdate(uri);
		fileHandle.analyzeFile(uri, response, Sql);
		super.onGet(sessionMgr, request, response, Sql);
	}
	
	public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		int index1=request.getUri().indexOf("_");
		 int index2=request.getUri().indexOf(".");
		 String username=request.getUri().substring(index1+1,index2);
		 String temp=request.getPostData();
		 String pwd[]=temp.split("=");
		 Sql.alter(username, pwd[1]);   
		 fileHandle.analyzeFile(request.getUri(),response,Sql);
		 super.onPost(sessionMgr, request, response, Sql);
	}

}
