/**
 * @author : tadiewa
 * date: 6/12/2025
 */


package com.records.security;


import com.records.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

// JwtAuthenticationFilter intercepts HTTP requests to validate JWT tokens and authenticate users.
// It ensures that only requests with valid JWTs can access protected endpoints.
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Service for JWT operations (extracting username, validating token, etc.)
    private final JwtService jwtService;
    // Repository to load user details from the database
    private final UserRepository userRepository;
    // Logger for debugging and error tracking


    // Constructor injection for dependencies
    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Determines if the filter should not be applied to a request.
     * Skips filtering for authentication endpoints (e.g., /api/auth/).
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/swagger-ui.html") ||
               path.startsWith("/swagger-resources") ||
               path.startsWith("/webjars");
    }

    /**
     * Main filter logic: extracts JWT from the Authorization header, validates it,
     * loads the user, and sets the authentication in the security context.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            log.info("authHeader --------------------------------------------------------->: {}", authHeader);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.debug("No Bearer token found in request");
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("Attempting to authenticate user-------------------------------------------------->: {}", userEmail);

                var userOptional = userRepository.findByUsername(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));
                log.info("User details fetched for email --------------------------------------->: {}", userOptional.getEmail());

                if (userOptional==null) {
                    log.error("User not found in database for email---------------------------------->: {}", userEmail);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("User not found or invalid token");
                    throw new UsernameNotFoundException("User not found with email: " + userEmail);
                }

                UserDetails userDetails = userOptional;
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authentication successful for user---------------------------->: {}", userEmail);
                } else {
                    log.error("Token validation failed for user------------------------------->: {}", userEmail);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid token");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
        }
    }
}
