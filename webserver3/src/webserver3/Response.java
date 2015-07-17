package webserver3;


public interface Response {	
	 void setStatus(int status);
	 void addHeader(String key,String value);
	 void addBody(String body);
	 void addBody(byte[] body);
	 void send();
}
