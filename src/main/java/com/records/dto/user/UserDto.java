/**
 * @author : tadiewa
 * date: 9/10/2025
 */


package com.records.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.records.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @JsonIgnore
    private Long userId;
    private String email;
    private String password;
    private String username;
    private Role role;
    private String fullName;
    private String contactInfo;
    private Boolean isActive;

}
