package test;
import java.io.File;

public class Caculator{
public double getDirSize(File file) {     
        //�ж��ļ��Ƿ����     
        if (file.exists()) {     
            //�����Ŀ¼��ݹ���������ݵ��ܴ�С    
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += getDirSize(f);     
                return size;     
            } else {//������ļ���ֱ�ӷ������С,�ԡ��ס�Ϊ��λ   
                double size = (double) file.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            System.out.println("�ļ������ļ��в����ڣ�����·���Ƿ���ȷ��");     
            return 0;     
        }   
}
    public String fmat(double size){
    	return size+"MB";
    }

}