package com.voligov.movieland.caching;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.entity.UserToken;
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
                return copy(tokenCache.get(index));
            }
        } finally {
            readLock.unlock();
        }
        try {
            writeLock.lock();
            tokenCache.add(token);
            log.info("Token for user Id = {} added to cache", token.getUser().getId());
            return copy(token);
        } finally {
            writeLock.unlock();
        }
    }

    public UserToken getByTokenString(String tokenValue) {
        try {
            readLock.lock();
            for (Iterator<UserToken> iterator = tokenCache.iterator(); iterator.hasNext(); ) {
                UserToken userToken = iterator.next();
                if (userToken.getToken().equals(tokenValue)) {
                    log.info("Token for value = {} found in cache", tokenValue);
                    Date currentDate = new Date();
                    if (currentDate.after(userToken.getExpirationDate())) {
                        try {
                            readLock.unlock();
                            writeLock.lock();
                            iterator.remove();
                            log.info("Token for value = {} expired, deleting from cache", tokenValue);
                            break;
                        } finally {
                            writeLock.unlock();
                            readLock.lock();
                        }
                    }
                    return copy(userToken);
                }
            }
            log.warn("Token for value = {} not found in cache", tokenValue);
            return null;
        } finally {
            readLock.unlock();
        }

    }

    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
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

    private UserToken copy(UserToken token) {
        UserToken copiedToken = new UserToken();
        User copiedUser = new User();
        copiedUser.setId(token.getUser().getId());
        copiedUser.setPassword(token.getUser().getPassword());
        copiedUser.setFirstName(token.getUser().getFirstName());
        copiedUser.setLastName(token.getUser().getLastName());
        copiedUser.setEmail(token.getUser().getEmail());
        copiedUser.setRole(token.getUser().getRole());
        copiedToken.setUser(copiedUser);
        copiedToken.setToken(token.getToken());
        copiedToken.setExpirationDate(token.getExpirationDate());
        return copiedToken;
    }
}
