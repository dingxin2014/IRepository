package com.fateking.optimize.performance;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.URL;  
import java.net.URLConnection;  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.List;


public class SinaStock {  
    private static String db  = "C:\\Documents and Settings\\Administrator\\Desktop\\sina-stock-codes.txt" ;  
    private static final int COLUMNS = 33;  
    private static List<String> codes = new ArrayList<String>() ;  
      
//    static{  
//        File in = new File(db) ;  
//        if(! in.exists()){  
//            // �������ȡ  
//            if(codes.size() < 1 )  
//                try {  
//                    codes = getAllStackCodes() ;  
//                } catch (IOException e) {  
//                    e.printStackTrace();  
//                }  
//        }else{  
//            // �ӱ��ػ�ȡ  
//            if(codes.size() < 1)  
//                try {  
//                    codes = getAllStockCodesFromLocal() ;  
//                } catch (IOException e) {  
//                    e.printStackTrace();  
//                }  
//        }  
//    }  
    
    public static void init(){
    	 File in = new File(db) ;  
       if(! in.exists()){  
           // �������ȡ  
           if(codes.size() < 1 )  
               try {  
                   codes = getAllStackCodes() ;  
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
       }else{  
           // �ӱ��ػ�ȡ  
           if(codes.size() < 1)  
               try {  
                   codes = getAllStockCodesFromLocal() ;  
               } catch (IOException e) {  
                   e.printStackTrace();  
               }  
       }  
    }
      
    // ����һ���Ʊ�����ַ���   ��code�а��������й�Ʊ�������List��  
    private static List<String> handleStockCode(String code){  
        List<String> codes = null ;  
        int end = code.indexOf(";") ;  
            code = code.substring(0,end) ;  
        int start = code.lastIndexOf("=") ;  
           code = code.substring(start) ;  
           code = code.substring(code.indexOf("\"")+1,code.lastIndexOf("\"")) ;  
           codes = Arrays.asList(code.split(",")) ;  
        return codes ;  
    }  
      
    //   ���ص�ֵ��һ��js�����  ����ָ��urlҳ����������й�Ʊ����  
    private static String getBatchStackCodes(URL url) throws IOException{  
         URLConnection connection = url.openConnection() ;  
         connection.setConnectTimeout(30000) ;  
         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;  
         String line = null ;  
         StringBuffer sb = new StringBuffer() ;  
        boolean flag =false ;  
         while((line = br.readLine()) != null ){  
             if(line.contains("<script type=\"text/javascript\">") || flag){  
                 sb.append(line) ;  
                 flag = true ;  
             }  
             if(line.contains("</script>")){  
                 flag =false ;  
                 if(sb.length() > 0 ){  
                     if(sb.toString().contains("code_list") && sb.toString().contains("element_list")){  
                         break ;  
                     }else{  
                         sb.setLength(0) ;  
                     }  
                 }  
             }  
         }  
         if(br != null ){  
             br.close() ;  
             br= null ;  
         }  
        return sb.toString() ;  
    }  
      
    // ��ȡ����38Ҳ�����й�Ʊ����  
    private static List<String> getAllStackCodes() throws IOException{  
        List<String> codes = new ArrayList<String>() ;  
        int i =1 ;  
        URL url = null ;  
        // ���� ��Ʊ ����ĿǰΪֹ�� 38ҳ  
        for(; i < 39 ; i ++ ){  
             url = new URL("http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p="+i) ;  
             String code = getBatchStackCodes(url) ;  
             codes.addAll(handleStockCode(code)) ;  
        }  
        if(! ( new File(db) ).exists() )  
            saveStockCodes(codes) ;  
        return codes ;  
    }  
      
    //������38ҳ�����й�Ʊ������뱾���ļ�  
    private static void saveStockCodes(List<String> codes ) throws IOException{  
        //�����й�Ʊ��������ļ���  
        File out = new File(db) ;  
        if(! out.exists())  
            out.createNewFile() ;  
        BufferedWriter bw = new BufferedWriter(new FileWriter(out)) ;  
        for(String code : codes ){  
            bw.write(code) ;  
            bw.newLine() ;  
        }  
        if(bw != null ){  
            bw.close() ;  
            bw = null ;  
        }  
    }  
      
    private static List<String> getAllStockCodesFromLocal() throws IOException{  
        List<String> codes = new ArrayList<String>() ;  
        File in = new File(db) ;  
        if(! in.exists())  
            throw new IOException("ָ�������ļ�������!");  
        BufferedReader br = new BufferedReader(new FileReader(in)) ;  
        String line = null ;  
        while( ( line = br.readLine() ) != null ){  
            codes.add(line) ;  
        }  
        // ɾ�����һ������  
        codes.remove(codes.size()-1) ;  
        if(br != null ){  
            br.close() ;  
            br = null ;  
        }  
        return codes ;  
    }  
      
    public static String[]  getStockInfoByCode(String stockCode) throws IOException{  
        // ������ӡ  
         String[] stockInfo = new String[COLUMNS] ;   
         URL url = new URL("http://hq.sinajs.cn/?list="+stockCode) ;  
         URLConnection connection = url.openConnection() ;  
         connection.setConnectTimeout(16000) ;  
         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;  
         String line = null ;  
         StringBuffer sb = new StringBuffer() ;  
         while(( line = br.readLine()) != null ){  
             sb.append(line) ;  
         }  
         System.out.print("\n");  
         if(sb.length() > 0 ){  
             String rs = sb.toString() ;  
             rs = rs.substring(rs.indexOf("\"")+1,rs.lastIndexOf("\"")) ;  
             String[] rss = rs.split(",") ;  
             for(int i = 0 ;  i< rss.length ; i ++ ){  
                 System.out.print(rss[i]+"\t|");  
                 stockInfo[i] = rss[i];  
             }  
             System.out.println("\n------------------------------------");   
         }  
         return stockInfo ;  
    }  
      
    public static void getAllStockInfo() throws IOException{  
        String[] header = getHeaders() ;  
        System.out.println(header.length);  
        for(int i = 0 ; i < header.length ;  i++ ){  
            System.out.print(header[i]+"\t|");  
        }  
        for(String code : codes ){  
            getStockInfoByCode(code) ;  
        }  
    }  
      
    /** 
     *  
     * @param first ��0��ʼ 
     * @param last  ������ last 
     * @return 
     */  
    public static List<String[]> getStockInfo(int first , int last , int recoeds)throws Exception{  
        List<String[]> stockInfo = new ArrayList<String[]>() ;  
        first = first < 0 ? 0 : first ;  
        if(first > last )  
            throw new Exception("�������Ϸ�!") ;  
        int i = 0 ;  
        while(last > codes.size()  ){  
            if(first + recoeds < codes.size()+1 ){  
                last = first +  recoeds ;  
                break ;  
            }else{  
                last = first + recoeds +(--i) ;  
            }  
        }  
        for( i = first ; i <= last ; i ++ ){  
            stockInfo.add(getStockInfoByCode(codes.get(i))) ;  
        }  
        return stockInfo ;  
    }  
      
    public static String[] getHeaders(){  
        String[] header = {"��Ʊ����","���տ��̼�    ","�������̼�","��ǰ�۸�","������߼�","������ͼ�","�����","������","�ɽ��Ĺ�Ʊ��","�ɽ����(Ԫ)","��һ","��һ","���","���","����","����","����","����","����","����","��һ","��һ","����","����","����","����","����","����","����","����","����","ʱ��","xxx"} ;  
        return header ;  
    }     
      
    public static List<String> getStockCodes(){  
        return codes ;  
    }  
    public static void main(String[] args) {  
        try {  
            getAllStockInfo() ;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  