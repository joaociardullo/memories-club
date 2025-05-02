package com.devjoao.passagem.service.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CustomCacheService {

    private final CacheManager cacheManager;

    public CustomCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void cleanCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}
