package test;

public class AboutAction extends AbstractAction implements Action {
	private analyze fileHandle;
    public AboutAction(){
    	fileHandle=new analyze();
    }
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/web/xjb.html";
	}
   
	public void onGet(Request request,Response response,SQL Sql){
		  String filename=request.getUri();
		  String uri2=decode(filename);
		  fileHandle.analyzeFile(uri2,response,Sql);
	}
}
