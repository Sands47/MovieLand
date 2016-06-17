package com.voligov.movieland.caching;

import com.voligov.movieland.entity.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class UserTokenCachingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private List<UserToken> tokenCache = new ArrayList<>();

    public UserToken addToCache(UserToken token) {
        try {
            readLock.lock();
            int index = tokenCache.indexOf(token);
            if (index != -1) {
                log.info("Token for user Id = {} already exists in cache", token.getUser().getId());
                return tokenCache.get(index);
            }
        } finally {
            readLock.unlock();
        }
        try {
            writeLock.lock();
            tokenCache.add(token);
            log.info("Token for user Id = {} added to cache", token.getUser().getId());
            return token;
        } finally {
            writeLock.unlock();
        }
    }

    public UserToken getByTokenString(String tokenValue) {
        try {
            readLock.lock();
            for (UserToken userToken : tokenCache) {
                if (userToken.getToken().equals(tokenValue)) {
                    log.info("Token for value = {} found in cache", tokenValue);
                    return userToken;
                }
            }
            log.warn("Token for value = {} not found in cache", tokenValue);
            throw new SecurityException("Token is not registered");
        } finally {
            readLock.unlock();
        }
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void updateTokenCache() {
        try {
            writeLock.lock();
            Date currentDate = new Date();
            for (Iterator<UserToken> iterator = tokenCache.iterator(); iterator.hasNext(); ) {
                UserToken userToken = iterator.next();
                if (currentDate.after(userToken.getExpirationDate())) {
                    iterator.remove();
                    log.info("Token for user Id = {} removed from cache", userToken.getUser().getId());
                }
            }
        } finally {
            writeLock.unlock();
        }
    }
}
