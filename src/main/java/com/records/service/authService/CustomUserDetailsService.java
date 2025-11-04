package com.records.service.authService;


import com.records.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// This service is used by Spring Security to load user-specific data during authentication.
// It implements UserDetailsService, which is a core interface in Spring Security for retrieving user information.
// In this implementation, users are loaded from the database using their email address.

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Injects the UserRepository to access user data from the database.
    private final UserRepository userRepository;

    /**
     * Loads the user from the database by email (used as username).
     * This method is called automatically by Spring Security during authentication.
     *
     * @param email the email of the user trying to authenticate
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Looks up the user by email. If not found, throws an exception.
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
