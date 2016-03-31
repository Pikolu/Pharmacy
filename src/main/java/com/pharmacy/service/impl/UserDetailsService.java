package com.pharmacy.service.impl;

import com.pharmacy.domain.User;
import com.pharmacy.repository.UserRepository;
import com.pharmacy.security.CustomUserDetails;
import com.pharmacy.security.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 28.12.2015.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        LOG.debug("Authenticating {}", login);
        String lowercaseLogin = login;
        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toSet());
            return new CustomUserDetails(user.getId(), lowercaseLogin, user.getFirstName(), user.getLastName(),
                    user.getPassword(), user.getEmail(),
                    grantedAuthorities, true, true, true, true);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                "database"));
    }


}
