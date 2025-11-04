/**
 * @author : tadiewa
 * date: 9/10/2025
 */

package com.records.service.authService;


import com.records.dto.user.AuthResponse;
import com.records.dto.user.LoginRequest;
import com.records.dto.user.UserDto;
import com.records.model.User;

public interface AuthService {

    UserDto register(UserDto request);
    AuthResponse login(LoginRequest request);
    UserDto update(UserDto request);

    User getAuthenticatedUser();
}
