package me.trongtin.project.repository;

import me.trongtin.project.entity.MyUserDetails;
import me.trongtin.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("select new me.trongtin.project.entity.MyUserDetails(u.username, u.password, concat('ROLE_', u.role.name)) from User u " +
            "where u.email = :email")
    Optional<MyUserDetails> findUserDetailsByEmail(String email);

    @Query("select new me.trongtin.project.entity.MyUserDetails(u.username, u.password, concat('ROLE_', u.role.name)) from User u " +
            "where u.username = :username")
    Optional<MyUserDetails> findUserDetailsByUsername(String username);

}
