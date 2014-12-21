package edu.sjsu.cmpe.cache.client;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
    private final String cacheServerUrl;
    int code=0;
    String value = null;
    
    public DistributedCacheService(String serverUrl) {
        this.cacheServerUrl = serverUrl;
    }

    /**
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws JSONException 
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
     */
    @Override
    public String get(long key) throws UnirestException, JSONException, InterruptedException, ExecutionException {
    	
        Future<HttpResponse<JsonNode>> response = null;
        response = Unirest.get(this.cacheServerUrl + "/cache/{key}")
		        .header("accept", "application/json")
		        .routeParam("key", Long.toString(key)).asJsonAsync(new Callback<JsonNode>() {

        		    public void failed(UnirestException e) {
        		        System.out.println("The request has failed");
        		        value= "No value";
        		        
        		    }

        		    public void completed(HttpResponse<JsonNode> response) {
        		    
        		    		value = response.getBody().getObject().getString("value");
        		    	
        		        System.out.println(value);
        		    }

        		    public void cancelled() {
        		        System.out.println("The request has been cancelled");
        		        value="No Value";
        		    }

        		});
       
       
        Thread.sleep(1000 * 20);
        return value;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
     *      java.lang.String)
     */
    @Override
    public int put(long key, String value)throws InterruptedException, ExecutionException {
    	Future<HttpResponse<JsonNode>> response = null;
    	System.out.println(value);
            response = Unirest
                    .put(this.cacheServerUrl + "/cache/{key}/{value}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .routeParam("value", value).asJsonAsync(new Callback<JsonNode>() {

            		    public void failed(UnirestException e) {
            		        System.out.println("The request has failed");
            		        code=0;
            		        
            		    }

            		    public void completed(HttpResponse<JsonNode> response) {
            		         code = response.getStatus();
            		         Map<String, List<String>> headers = response.getHeaders();
            		         JsonNode body = response.getBody();
            		         
            		         InputStream rawBody = response.getRawBody();
            		         
            		    }

            		    public void cancelled() {
            		        System.out.println("The request has been cancelled");
            		        code=0;
            		    }

            		});
            
            Thread.sleep(1000 * 2);
            return code;
      
    }
    
    @Override
    public void delete(long key) {
    	Future<HttpResponse<JsonNode>> response = null;
        
            response = Unirest
                    .delete(this.cacheServerUrl + "/cache/{key}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .asJsonAsync(new Callback<JsonNode>() {

            		    public void failed(UnirestException e) {
            		        System.out.println("The request has failed");
            		    }

            		    public void completed(HttpResponse<JsonNode> response) {
            		         int code = response.getStatus();
            		         Map<String, List<String>> headers = response.getHeaders();
            		         JsonNode body = response.getBody();
            		         InputStream rawBody = response.getRawBody();
            		         System.out.println("The request has complted---->"+ code);
            		    }

            		    public void cancelled() {
            		        System.out.println("The request has been cancelled");
            		    }

            		});


    }

	
}
