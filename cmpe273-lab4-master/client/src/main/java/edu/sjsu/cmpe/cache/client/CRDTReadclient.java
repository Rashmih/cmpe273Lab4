package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

public class CRDTReadclient {

	//private static ArrayList<String> servers = new ArrayList<String>();
	
    public static void main(String[] args) throws Exception {
    	 
    	int count=0;
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");
        
        //First PUT call to store "a" to key 1
        int r1= cache1.put(1, "a");
        System.out.println("r1  : " +r1);
        int r2= cache2.put(1, "a");
        System.out.println("r2  : " +r2);
        int r3= cache3.put(1, "a");
        System.out.println("r3  : " +r3);
        
        //sleep for ~30 seconds so that you will have enough time to stop the server A
        System.out.println("sleep1 start");
       Thread.sleep(1000 * 30);
        System.out.println("sleep1 end");
        
        //second PUT call to update the key 1 to value "b"
        
        r1 = cache1.put(1,"b");
        System.out.println("r1  : " +r1);
        r2 = cache2.put(1,"b");
        System.out.println("r2  : " +r2);
        r3 = cache3.put(1,"b");
        System.out.println("r3  : " +r3);
        
        System.out.println("sleep2 start");
       Thread.sleep(1000*30);
        System.out.println("sleep2 end");
        
        //r1 = cache1.put(2,"b");
        String v1 = cache1.get(1);
        System.out.println("v1  : " +v1);
        String v2 = cache2.get(1);
        System.out.println("v2  : " +v2);
        String v3 = cache3.get(1);
        System.out.println("value" +v1+v2 +v3);
        
        if(v1 != null && v2 != null){
          if(v3==null && v1.equals("b") && v2.equals("b"))
          {
        	  cache3.put(1, "b");
        	  
        	  
          }
       }else if(v1 !=null && v3 !=null){
        	if(v2==null && v1.equals("b") && v3.equals("b"))
        	{
        		cache2.put(1, "b");
        	}
    	   
      }else if(v2 != null && v3 != null){
    	  if(v1==null && v2.equals("b") && v3.equals("b"))
    	  {
    		  cache1.put(1,"b");
    	  }
      	
    }     
        
         v1 = cache1.get(1);
        System.out.println("v1  : " +v1);
         v2 = cache2.get(1);
        System.out.println("v2  : " +v2);
         v3 = cache3.get(1);
        System.out.println("value" +v1+v2 +v3);
       
              
        System.out.println("Existing Cache Client...");
    }
    
    
	

}


