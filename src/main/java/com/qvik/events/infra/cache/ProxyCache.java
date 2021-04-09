package com.qvik.events.infra.cache;

import com.qvik.events.infra.response.ResponseMessage;
import org.springframework.core.io.Resource;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProxyCache {

    private static final ProxyCache proxyCache = new ProxyCache();

    private final Map<String, ResponseMessage> cache = new HashMap<>();
    private final Map<String, Resource> image = new HashMap<>();

    private LocalDateTime lastUpdateDateTime = LocalDateTime.now();

    private ProxyCache(){}

    public static ProxyCache getInstance(){
        return proxyCache;
    }

    /*
     * Getter & Setter for data
     */
    public ResponseMessage getData (String key){
        return cache.get(key);
    }

    public void setData (String key, ResponseMessage message){
        cache.put(key, message);
    }

    public void resetData (){
        cache.clear();

        lastUpdateDateTime = LocalDateTime.now();
    }

    /*
     * Getter & Setter for image
     */
    public Resource getImage (String key){
        return image.get(key);
    }

    public void setImage (String key, Resource resource){
        image.put(key, resource);
    }

    public void resetImage (){
        image.clear();

        lastUpdateDateTime = LocalDateTime.now();
    }

    /*
     * Getter for last update
     */
    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }
}
