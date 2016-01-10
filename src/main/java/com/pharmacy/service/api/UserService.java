package com.pharmacy.service.api;

import com.pharmacy.domain.User;
import com.pharmacy.exceptions.ServiceException;

import java.util.Optional;

/**
 * Created by Alexander on 28.12.2015.
 */
public interface UserService {

    public Optional<User> activateRegistration(String key);

    public Optional<User> completePasswordReset(String newPassword, String key);

    public Optional<User> requestPasswordReset(String mail);

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
                                 String langKey);

    public void updateUserInformation(String firstName, String lastName, String email, String langKey);

    public void changePassword(String password);

    public Optional<User> getUserWithAuthoritiesByLogin(String login);

    public User getUserWithAuthorities(Long id);

    public User getUserWithAuthorities();
}
