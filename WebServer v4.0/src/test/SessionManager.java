package test;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	
	private SessionManager mgr;
	
	private Map<String, Session> sessions = new HashMap<String, Session>();
	
	public Session getSession(Request request){
		try{
		String[] cookies=request.getCookies();
		if(cookies[0].length()==0){
			return null;
		}
		Cookie Cookies[] =new Cookie[10];
		for(int i=0;i<cookies.length;i++){
			if(cookies[i]!=null){
			Cookies[i]=new Cookie(cookies[i]);
			}
		}
		
		String sessionid=null;
		for(int i=0;i<cookies.length;i++){
		sessionid = Cookies[i].get("session");
		if(sessionid!=null)
			break;
		}
		if(sessionid==null){
			return null;
		}
		else if(sessions.containsKey(sessionid)){
			return sessions.get(sessionid);
		}
		else{
			return null;
		}
		}catch(NullPointerException npe){}
		return null;
	}
	
	public void initSession(){
		Session value=new Session();
		value.setValue("loginstate","false");
		sessions.put("XJBlike", value);
	}
}
