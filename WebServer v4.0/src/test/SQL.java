package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQL {   
	private Statement statement;
	 static final Logger logger=LoggerFactory.getLogger(SQL.class);
	
	 public SQL(Statement statement){
		this.statement=statement;
	}
	
	 public void insert(String username,String password){   	
    	if(query(username)){
    		logger.info("The user already exists,you will update his password!");
    		alter(username,password);
    	}
    	else{
		 try{
    		String sql="insert into user values(\""+username+"\",\""+password+"\")";
    		int count=statement.executeUpdate(sql);
    		if(count==0)logger.info("insert-insert failed!");
    		else logger.info("insert-insert Success!");
    		}catch(SQLException se){se.printStackTrace();}   
    	}
     }
     
    
    
    public void alter(String username,String password){
    	delete(username);
    	insert(username,password);
    }
    
    public boolean query(String username){
    	try{ 
    	String sql="select password from user where username=\'"+username+"\'";
    	 ResultSet res=statement.executeQuery(sql);
    	 if(!res.next()){
    		 return false;
    	 }
    	 else{
    		 return true;
    	 }   		 
    	}catch(SQLException se){
    		se.printStackTrace();
    	}
    	return false;
    }
    
    public String query2(String username){
   	 try{
   	  String sql="SELECT * FROM user WHERE username=\'"+username+"\'";
   	  ResultSet res=statement.executeQuery(sql);
   	  while(res.next()){     		 
   		  String password=res.getString("password");
   		  return password;      		  
   	  }
   	 }catch(SQLException se){se.printStackTrace();}
   	 return null;
     }
    
    public void delete(String username){
    	if(query(username)){
    		try{
    		String sql="delete from user where username=\'"+username+"\'";
    		int count=statement.executeUpdate(sql);
    		if(count==0)logger.info("delete failed");
    		else logger.info("delete succeed!");
    		}catch(SQLException se){se.printStackTrace();}
    	}
    	else{
    		logger.info("Invalid username");
    	}
    }
    
    public boolean signIn(String username,String password){
    	if(password.equals(query2(username))){		        			 
		    return true;    			        		  
      }
	 else{
		 return false;	 
	 }
    }
    
    public boolean register(String username,String password){
    	if(query(username)){
    		return false;
    	}
    	else{
    		insert(username,password);
    		return true;
    	}
    }
    
   public void setCurrentUser(String username,String password){
	   if(queryCurrent()){
		   alterCurrent(username,password);
	   }
	   else{
		   try{
		   String sql="insert into currentUser values(\""+username+"\",\""+password+"\")";
		   statement.executeUpdate(sql);
		   }catch(SQLException se){se.printStackTrace();}
	   }	   
   }
   
   public boolean queryCurrent(){
	   try{ 
	    	String sql="select * from currentUser";
	    	 ResultSet res=statement.executeQuery(sql);
	    	 if(!res.next()){
	    		 return false;
	    	 }
	    	 else{
	    		 return true;
	    	 }   		 
	    	}catch(SQLException se){
	    		se.printStackTrace();
	    	}
	    	return false;
   }
   
   public void alterCurrent(String username,String password){
	  try{
	   String sql="delete from currentUser";
	   statement.executeUpdate(sql);
	   String sql2="insert into currentUser values(\""+username+"\",\""+password+"\")";
	   statement.executeUpdate(sql2);
	  }catch(SQLException se){se.printStackTrace();}
   }
   
   public String getCurrentUser(){
	  try{
	   String sql="select * from currentUser";
	   ResultSet res=statement.executeQuery(sql);
	   if(res.next()){
		   String username=res.getString("username");
		   return username;
	   }
	   else 
		   return null;
	  }catch(SQLException se){se.printStackTrace();}
	  return null;
   }
   
   public String createUserList(){
	   File html=new File("D:\\web\\show.html");
	   String temp="<html>"
	   		+ "<head>"
	   		+ "<title>all users</title>"
	   		+ "</head>"
	   		+ "<body>"
	   		+ "<table>"
	   		+ "<tr>"
	   		+"<td><font size=\"5\"><a href=\"\\\">back to web_root</a></font></td>"
	   		+"</tr>"
	   		+"";  
		   try{
			   try{
			   html.createNewFile();
		   String sql="select * from user";
		   ResultSet res=statement.executeQuery(sql);
		   int i=1;
		   while(res.next()){
			   
			   String username=res.getString("username");
			   String password=res.getString("password");
			   temp+="<tr>"
			   		 + "<td><font size=\"5\"><a href=\"manager"+"_"+username+".html\">"+i+".user:"+username+" pwd:"+password+"</a></font></td>"
			   	     + "</tr>"
			   	     + "";
			   i++;
		   }
		   temp+="<tr>"
		   		+ "<td><font size=\"5\"><a href=\"manager_register.html\">regist new user</a></font></td>"
		   		+ "</tr>"
				+"</table>"
		   		+ "</body>"
		   		+ "</html>";
		   PrintStream ps=new PrintStream(new FileOutputStream(html));
		   ps.println(temp);
		   return temp;
		   }catch(IOException e){e.printStackTrace();}
		   }catch(SQLException se){se.printStackTrace();}	
		   return null;
      }
   
   public void createManager(String uri){
	   try{
		File html=new File("D:"+uri);
	   String temp="<html>"
		   		+ "<head>"
		   		+ "<title>manager</title>"
		   		+ "</head>"
		   		+ "<body>"
		   		+ "<table>"
		   		+ "";
	   int index1=uri.indexOf("_");
	   int index2=uri.indexOf(".");
	   String username=uri.substring(index1+1, index2);
		   html.createNewFile();
		   temp+="<tr>"
		   		+ "<td colspan=2>"
		   		+ "<i>manager</i>"
		   		+ "</td>"
		   		+ "</tr>"
		   		+ "<tr>"
		   		+ "<td><font size=\"5\"><a href=\"delete"+"_"+username+".html\">delete "+username+"</a></font></td>"
		   		+ "</tr>"
		   		+ "<tr>"
		   		+ "<td><font size=\"5\"><a href=\"update"+"_"+username+".html\">update user's password</a></font></td>"
		   		+ "</tr>"
		   		+ "</table>"
		   		+ "</body>"
		   		+ "</html>";
		   PrintStream ps=new PrintStream(new FileOutputStream(html));
		   ps.println(temp);
	   }catch(IOException ie){ie.printStackTrace();}
   }
   
   public void createDelete(String uri){
	   try{
			File html=new File("D:"+uri);
		   String temp="<html>"
			   		+ "<head>"
			   		+ "<title>delete</title>"
			   		+ "</head>"
			   		+ "<body>"
			   		+ "<table>"
			   		+ "";
		   int index1=uri.indexOf("_");
		   int index2=uri.indexOf(".");
		   String username=uri.substring(index1+1, index2);
		   delete(username);
			   html.createNewFile();
			   temp+="<tr>"
			   		+ "<td colspan=2><div color=#00FF00><font size=\"5\">user has been deleted!</font></div></td>"
			   		+ "</tr>"
			   		+ "<tr>"
			   		+ "<td><font size=\"5\"><a href=\"show.html\">back to all users</a></font></td>"
			   		+ "</tr>"
			   		+ "</table>"
			   		+ "</body>"
			   		+ "</html>";
			   PrintStream ps=new PrintStream(new FileOutputStream(html));
			   ps.println(temp);
		   }catch(IOException ie){ie.printStackTrace();}
   }
   
   public void createUpdate(String uri){
	   try{
			File html=new File("D:"+uri);
		   String temp="<html>"
			   		+ "<head>"
			   		+ "<title>update</title>"
			   		+ "</head>"
			   		+ "<body>"
			   		+ "<table>"
			   		+ "";
		   int index1=uri.indexOf("_");
		   int index2=uri.indexOf(".");
		   String username=uri.substring(index1+1, index2);
		   delete(username);
			   html.createNewFile();
			   temp+="<form name=form method=post action=#>"
			   		+ "<table>"
			   		+ "<tr>"
			   		+ "<td colspan=2><font size=\"5\"><i>alter</i></font></td>"
			   		+ "</tr>"
			   		+ "<tr>"
			   		+ "<td>new password:</td>"
			   		+ "<td><input type=password  name=pwd size=16></td>"
			   		+ "<td colspan=2><input type=submit value=update></td>"
			   		+ "</tr>"
			   		+ "<tr>"
			   		+ "<td><font size=\"5\"><a href=\"show.html\">back to all users</a></font></td>"
			   		+ "</tr>"
			   		+ "</table>"
			   		+ "</form>"
			   		+ "</body>"
			   		+ "</html>";
			   PrintStream ps=new PrintStream(new FileOutputStream(html));
			   ps.println(temp);
		   }catch(IOException ie){ie.printStackTrace();}
   }
}
