package com.lviv.hnatko.controllers;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.lviv.hnatko.security.UserPrincipal;

@Component
public class UserHandlerMethodArgumentController implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

        Authentication auth = (Authentication) nativeWebRequest.getUserPrincipal();

        return auth != null && auth.getPrincipal() instanceof UserDetails ? auth.getPrincipal() : null;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return UserPrincipal.class.isAssignableFrom(methodParameter.getParameterType()) &&
                methodParameter.getParameterAnnotation(AuthenticationPrincipal.class) != null;
    }
}
