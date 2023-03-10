package com.weather.config;

import com.weather.error.InvalidCityException;
import com.weather.service.StandardUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
   private JWTUtil jwtUtil;
    @Autowired
    private StandardUserDetailService standardUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final  String requestToken=request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;

        if(requestToken!=null && requestToken.startsWith("Bearer ")) {
            jwtToken=requestToken.substring(7);
            try {
                username = jwtUtil.getUserNameFromToken(jwtToken);
            }catch (IllegalArgumentException illegalArgumentException){
                logger.error("Error in retrieving username !!");
            }catch (ExpiredJwtException e) {
                logger.error("Token is expired or not valid");
                throw new ExpiredJwtException( e.getHeader(),e.getClaims(),e.getMessage());

            }



        }else {
            logger.warn("Jwt token does not start with bearer");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()== null) {
            UserDetails userDetails = standardUserDetailService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
