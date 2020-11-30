package com.example.demo.auth.interceptro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.auth.model.serivce.JwtTokenProvider;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	
//	private AuthorizationExtractor authExtractor;
//	private JwtTokenProvider jwtTokenProvider;
//	
//	
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response, Object handler) {
//        String token = authExtractor.extract(request, "Bearer");
//        if(!jwtTokenProvider.validateToken(token)) {  // 토큰의 유효성 검증
//            return throw new InvalidAuthenticationException("만료된 세션입니다.");
//        };
//
//        String email = jwtTokenProvider.getSubject(token);
//        request.setAttribute("loginMemberEmail", email);
//        return true;
//    }
}
