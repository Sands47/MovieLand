package com.voligov.movieland.controller.interceptor;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
            if (roleRequired != null) {
                String token = httpServletRequest.getHeader("token");
                UserToken userToken = securityService.validateToken(token);
                UserRole requiredRole = roleRequired.role();
                if (!userToken.getUser().getRole().equalOrHigher(requiredRole)) {
                    throw new SecurityException("Role required: " + requiredRole);
                } else {
                    httpServletRequest.setAttribute("authorizedUser", userToken.getUser());
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
