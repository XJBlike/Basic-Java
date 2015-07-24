package test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

public class AllAction extends AbstractAction implements Action {
	private analyze fileHandle;
	
	public AllAction(){
		fileHandle=new analyze();
	}
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		  String filename=request.getUri();
		   String uri2=decode(filename);
		  fileHandle.analyzeFile(uri2,response,Sql);
	}
	
	public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql){
		try{
		if(request.getUri().contains("/update")){
			 int index1=request.getUri().indexOf("_");
			 int index2=request.getUri().indexOf(".");
			 String username=request.getUri().substring(index1+1,index2);
			 String temp=request.getPostData();
			 String pwd[]=temp.split("=");
			 Sql.alter(username, pwd[1]);   
			 fileHandle.analyzeFile("D:"+request.getUri(),response,Sql);
		 }      			
		 if(request.getUri().contains("/login.html")||"/".equals(request.getUri())){
			 String temp=request.getPostData();
			 String logs[]=temp.split("&");
			 String user[]=logs[0].split("=");
			 String pwd[]=logs[1].split("=");
			 if(Sql.signIn(user[1], pwd[1])){
				 Sql.setCurrentUser(user[1], pwd[1]);
				 fileHandle.analyzeFile("/",response,Sql);
				 try{
				 File file=new File("D:\\login.txt");
				 file.createNewFile();
				 }catch(IOException ie){ie.printStackTrace();}
			 }
			 else
				 fileHandle.analyzeFile("/web/login.html",response,Sql);
		 }
		 else if("/web/register.html".equals(request.getUri())){
			 String temp=request.getPostData();
			 String logs[]=temp.split("&");
			 String user[]=logs[0].split("=");
			 String pwd[]=logs[1].split("=");
			   if(Sql.register(user[1], pwd[1])){
				   Sql.setCurrentUser(user[1], pwd[1]);
				   fileHandle.analyzeFile("/",response,Sql);       					 
				 try{
					 File file=new File("D:\\login.txt");
					 file.createNewFile();
					 }catch(IOException ie){ie.printStackTrace();}
			   }
			   else{
				   fileHandle.analyzeFile("/web/register.html",response,Sql);
			   }
		 }
		}catch(NullPointerException npe){}
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
