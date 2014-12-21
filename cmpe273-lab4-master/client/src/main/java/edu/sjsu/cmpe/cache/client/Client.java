package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

public class Client {

	
	
    public static void main(String[] args) throws Exception {
    	 
    	int count=0;
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");

        int r1= cache1.put(1, "a");
        System.out.println("r1  : " +r1);
        int r2= cache2.put(1, "a");
        System.out.println("r2  : " +r2);
        int r3= cache3.put(1, "a");
        System.out.println("r3  : " +r3);

        
        if(r1 != 200 && r2 != 200){
        	cache3.delete(1);
       	cache1.put(1, "a");
           cache2.put(1, "a");
           cache3.put(1, "a");
            System.out.println("delete written to cache3 ");
       }else if(r1 != 200 && r3 != 200){
        	cache2.delete(1);
        	cache1.put(1, "a");
       	cache2.put(1, "a");
     	cache3.put(1, "a");
      	 System.out.println("only written to cache2 ");
      }else if(r2 != 200 && r3 != 200){
      	cache1.delete(1);
       	cache1.put(1, "a");
        	cache2.put(1, "a");
      	cache3.put(1, "a");
     	 System.out.println("only written to cache1 ");
    }     
       
              
        System.out.println("Existing Cache Client...");
    }
    
    
	

}
