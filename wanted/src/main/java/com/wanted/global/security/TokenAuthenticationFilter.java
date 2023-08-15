package com.wanted.global.security;

import com.wanted.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws RuntimeException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String accessToken = request.getHeader("Authorization");

        if (accessToken != null) {
            accessToken = accessToken.substring(7);

            String memberId = tokenProvider.getMemberId(accessToken);
            String role = tokenProvider.getRole(accessToken);

            UserDetails userDetails = CustomUserDetail.of(memberId, role);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        chain.doFilter(request, response);
    }
}
