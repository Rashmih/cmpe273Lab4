package edu.sjsu.cmpe.cache.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Cache Service Interface
 * 
 */
public interface CacheServiceInterface {
    public String get(long key) throws UnirestException, JSONException, InterruptedException, ExecutionException;

    public int put(long key, String value) throws InterruptedException, ExecutionException;

	public void delete(long key);
}
