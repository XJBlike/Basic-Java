package test;

public interface Request {
             String getMethod();
             String getUri();
             String getHttpVersion();
             String getHost();
             String getConnection();
             String getPostData();
             String[] getCookies();
}
