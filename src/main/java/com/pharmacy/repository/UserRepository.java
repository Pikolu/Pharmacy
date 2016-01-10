package com.pharmacy.repository;

import com.pharmacy.domain.User;
import org.springframework.data.jpa.repository.*;

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

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    @Override
    void delete(User t);
}
