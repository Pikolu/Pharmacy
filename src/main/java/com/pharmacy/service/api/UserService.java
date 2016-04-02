package com.pharmacy.service.api;

import com.pharmacy.domain.User;

import java.util.Optional;

/**
 * Created by Alexander on 28.12.2015.
 */
public interface UserService {

    User activateRegistration(String key);

    Optional<User> completePasswordReset(String newPassword, String key);

    Optional<User> requestPasswordReset(String mail);

    User createUserInformation(String login, String password, String firstName, String lastName, String email,
                               String langKey);

    void updateUserInformation(String firstName, String lastName, String email, String langKey);

    void changePassword(String password);

    Optional<User> getUserWithAuthoritiesByLogin(String login);

    User getUserWithAuthorities(Long id);

    User getUserWithAuthorities();

    User getUser(Long id);

    Optional<User> findOneByEmail(String mail);

    Optional<User> findOneByLogin(String login);

}
