package com.voligov.movieland.service.impl;

import com.voligov.movieland.caching.UserTokenCachingService;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.enums.UserRole;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final int USER_TOKEN_DURATION = 2;

    @Autowired
    private UserTokenCachingService userTokenCachingService;

    @Override
    public boolean validateUser(UserCredentials credentials, User user) {
        return user.getEmail().equals(credentials.getLogin()) &&
                user.getPassword().equals(credentials.getPassword());
    }

    @Override
    public UserToken validateToken(String token, UserRole roleRequired) {
        UserToken userToken = userTokenCachingService.getByTokenString(token);
        if (roleRequired != UserRole.GUEST && userToken == null) {
            throw new SecurityException("Token is not registered");
        }
        return userToken;
    }

    @Override
    public UserToken registerUser(User user) {
        UserToken token = generateToken(user);
        return userTokenCachingService.addToCache(token);
    }

    private UserToken generateToken(User user) {
        UserToken userToken = new UserToken();
        UUID token = UUID.randomUUID();
        userToken.setToken(token.toString());
        userToken.setUser(user);
        userToken.setExpirationDate(DateUtils.addHours(new Date(), USER_TOKEN_DURATION));
        return userToken;
    }
}
