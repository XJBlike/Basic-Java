package webserver3;

public interface Request {
             String getMethod();
             String getUri();
             String getHttpVersion();
             String getValue(String key);
}
