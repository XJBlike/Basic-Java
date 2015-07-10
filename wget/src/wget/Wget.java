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
    	//����һ��scanner����ȡURL��·��
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Please input the file's Http_URL:");  	
        String  url=sc.nextLine();
        System.out.println("please input the path and the filename:");
        String path=sc.nextLine();
      //����downloadFromUrl
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
        	 //�����µ�URL����
            URL httpurl = new URL(url); 
            //��ȡ�ļ���
            String fileName =dir;
            		//getFileNameFromUrl(url); 
          //����ļ���
            System.out.println(fileName);         
          //���ļ���֮ǰ����·�������ļ�
            File f = new File( fileName); 
            //��URL���ݿ������ļ�
           copyURLToFile(httpurl, f); 
           String absolutepath=f.getAbsolutePath();
         //����ɹ���Ϣ�Լ��洢·����
           return "Successfully saved to "+absolutepath;
        } catch (Exception e) {   
            e.printStackTrace();
          //�쳣ʱ���������Ϣ
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
    	//���Ŀ���ļ�������
    	if(!dest.exists()){
    		//������Ŀ¼��Ȼ�󴴽����ļ�
    		dest.getParentFile().mkdirs();
    		dest.createNewFile();
    	}
    	//�ؼ�����ڴˣ�ͨ��URL���openStream�������һ��������
    	InputStream  in=source.openStream();
    	try{
    		//����һ��
    	FileOutputStream out =new FileOutputStream(dest);
    	BufferedOutputStream buf = new BufferedOutputStream(out);
        try{
            /*����һ���̶���С��buffer
        	*ͨ��buffer������������д�������
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