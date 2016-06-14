package com.voligov.movieland.caching;

import com.voligov.movieland.entity.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserTokenCache {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<UserToken> tokenCache = new ArrayList<>();

    public synchronized UserToken addToCache(UserToken token) {
        if (tokenCache.contains(token)) {
            return tokenCache.get(tokenCache.indexOf(token));
        } else {
            tokenCache.add(token);
            log.info("Token for user Id = {} added to cache", token.getUser().getId());
            return token;
        }
    }

    public synchronized UserToken getByTokenString(String tokenValue) {
        for (UserToken userToken : tokenCache) {
            if (userToken.getToken().toString().equals(tokenValue)) {
                log.info("Token for value = {} found in cache", tokenValue);
                return userToken;
            }
        }
        log.warn("Token for value = {} not found in cache", tokenValue);
        return null;
    }

    @Scheduled(fixedRate = 60 * 1000)
    public synchronized void updateTokenCache() {
        Date currentDate = new Date();
        for (Iterator<UserToken> iterator = tokenCache.iterator(); iterator.hasNext(); ) {
            UserToken userToken = iterator.next();
            if (currentDate.after(userToken.getExpirationDate())) {
                iterator.remove();
                log.info("Token for user Id = {} removed from cache", userToken.getUser().getId());
            }
        }
    }
}
