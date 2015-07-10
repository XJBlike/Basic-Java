package wget;

import java.io.*;
import java.util.Scanner;   
import java.net.URL;   
    
public class Wget{    	
	/*
     * get url of the file you wanted
     * get the path to save your file 
     */                                                                                                                                                                              
    public static void main(String[] args) {
    	//创建一个scanner，获取URL和路径
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Please input the file's Http_URL:");  	
        String  url=sc.nextLine();
        System.out.println("please input the path and the filename:");
        String path=sc.nextLine();
      //调用downloadFromUrl
        String res = downloadFromUrl(url,path);  
        sc.close();
        System.out.println(res);
    }   
  
   /*
    * download file from url
    * returns success/failure message
    * if succeed,returns file's absolute path
    */
    public static String downloadFromUrl(String url,String dir) {   
        try { 
        	 //创建新的URL类型
            URL httpurl = new URL(url); 
            //获取文件名
            String fileName =dir;
            		//getFileNameFromUrl(url); 
          //输出文件名
            System.out.println(fileName);         
          //在文件名之前加上路径创建文件
            File f = new File( fileName); 
            //将URL内容拷贝到文件
           copyURLToFile(httpurl, f); 
           String absolutepath=f.getAbsolutePath();
         //输出成功信息以及存储路径名
           return "Successfully saved to "+absolutepath;
        } catch (Exception e) {   
            e.printStackTrace();
          //异常时输出错误信息
            return "Error!";   
        }        
    }   
   
    /*
     * copy URL file's content to local file
     * by
     * get an InputStream from URL and an OutputStream to local file
     * using a buffer to transport bytes
     */
    public static void copyURLToFile(URL source,File dest)throws IOException
    {
    	//如果目标文件不存在
    	if(!dest.exists()){
    		//创建父目录，然后创建新文件
    		dest.getParentFile().mkdirs();
    		dest.createNewFile();
    	}
    	//关键点就在此，通过URL类的openStream函数获得一个输入流
    	InputStream  in=source.openStream();
    	try{
    		//创建一个
    	FileOutputStream out =new FileOutputStream(dest);
    	BufferedOutputStream buf = new BufferedOutputStream(out);
        try{
            /*创建一个固定大小的buffer
        	*通过buffer将输入流内容写入输出流
        	*/
        	int numread=0;
        	int buf_size=4096;
        	byte[] buffer=new byte[buf_size];
        	while((numread=in.read(buffer,0,buf_size))!=-1)        	
        	    buf.write(buffer, 0, numread);           
    	   }finally{
    		     try{
    			      if(out!=null)
    				  out.close();
    		     	}catch(IOException oe)
    		             {
    		     		  System.out.println("Output file is null");
    		     		 }
    	           }
          }finally{
        	    try{
        	         if(in!=null)
        		     in.close();
                   }catch(IOException ie)
        	             {
                	   		System.out.println("Input file is null");
                	   	 }
        		}
    	}
}  