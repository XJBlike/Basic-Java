package test;

public interface Action {
    public String getUrl();
    public void onGet(SessionManager sessionMgr,Request request,Response response,SQL Sql);
    public void onPost(SessionManager sessionMgr,Request request,Response response,SQL Sql);
}
