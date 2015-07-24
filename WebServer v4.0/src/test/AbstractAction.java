package test;

import java.net.URLDecoder;

public abstract class AbstractAction implements Action{
   private analyze fileHandle;
	
   public AbstractAction(){
	   fileHandle=new analyze();
   }
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql) {
	   String filename=request.getUri();
	   String uri2=decode(filename);
	  fileHandle.analyzeFile(uri2,response,Sql);
   }
   
   
   public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
	   
   }
   
	public String decode(String ChineseUrl){
	   	  Decode URL=new Decode();
	   	  if(URL.isUtf8Url(ChineseUrl)){
	            return URL.Utf8URLdecode(ChineseUrl);
	             }else{
	             return URLDecoder.decode(ChineseUrl);
	             }
	   }
}
