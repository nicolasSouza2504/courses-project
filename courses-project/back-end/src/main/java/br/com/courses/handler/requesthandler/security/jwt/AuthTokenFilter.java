package br.com.courses.handler.requesthandler.security.jwt;

import br.com.courses.handler.requesthandler.security.user.AuthyUserDetailsService;
import br.com.courses.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthyUserDetailsService userDetailsService;

        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain) throws ServletException, IOException {
            try {

                String jwt = parseJwt(request);

                if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {

                    JWTContext.setJwt(jwt);

                    String username = jwtUtils.getUsernameFromToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);

                }

            } catch (JwtException e) {

                setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, e.getMessage(), "Token inválido ou expirado. Faça login e tente novamente.");

                return;

            } catch (Exception e) {

                setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, e.getMessage(), "Erro interno no servidor.");

                return;

            }

            filterChain.doFilter(request, response);

        }

    private String parseJwt(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;

    }


    /**
     * Método auxiliar para configurar a resposta de erro em formato JSON.
     */
    private void setErrorResponse(int status, HttpServletResponse response, String technicalMessage, String userMessage) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");

        ApiResponse apiResponse = new ApiResponse(userMessage, technicalMessage);

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));

    }


}
