package com.devjoao.passagem.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheManager {

    @Autowired
    private CacheManager cacheManager;

    public CacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void cleanCache(String cacheName) {
        cacheManager.getCacheManager();
    }
}
