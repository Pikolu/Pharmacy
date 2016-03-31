package com.pharmacy.repository;

import com.pharmacy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Alexander on 28.12.2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findOneByEmail(@Param(value = "email") String email);

    @Query(value = "SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findOneByLogin(@Param(value = "login") String login);

    @Override
    void delete(User t);
}
