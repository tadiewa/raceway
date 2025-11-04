/**
 * @author : tadiewa
 * date: 7/23/2025
 */

package com.records.repository;


import com.records.constants.Role;
import com.records.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Page<User> findByRole(Role role, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
}
