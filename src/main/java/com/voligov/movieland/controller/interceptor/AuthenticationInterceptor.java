package com.voligov.movieland.controller.interceptor;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.util.entity.UserToken;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.enums.UserRole;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.voligov.movieland.util.Constant.*;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        String user = GUEST;
        String token = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            token = httpServletRequest.getHeader(TOKEN);
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleRequired roleRequiredAnnotation = handlerMethod.getMethodAnnotation(RoleRequired.class);
            UserRole roleRequired = (roleRequiredAnnotation != null) ? roleRequiredAnnotation.role() : UserRole.GUEST;
            UserToken userToken = securityService.validateToken(token, roleRequired);
            if (userToken != null) {
                user = userToken.getUser().getEmail();
                if (userToken.getUser().getRole().equalOrHigher(roleRequired)) {
                    httpServletRequest.setAttribute(AUTHORIZED_USER, userToken.getUser());
                } else {
                    throw new SecurityException("Role required: " + roleRequired);
                }
            }
        }
        MDC.put(USER, user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
