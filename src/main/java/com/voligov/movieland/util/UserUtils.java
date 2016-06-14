package com.voligov.movieland.util;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserToken;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserUtils {
    public UserToken generateToken(User user) {
        UserToken userToken = new UserToken();
        UUID token = UUID.randomUUID();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setExpirationDate(DateUtils.addHours(new Date(), 2));
        return userToken;
    }
}
