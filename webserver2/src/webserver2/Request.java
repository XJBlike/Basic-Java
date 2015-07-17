//package webserver;

public interface Request {
             String getMethod();
             String getUri();
             String getHttpVersion();
             String getHost();
             String getConnection();
}
