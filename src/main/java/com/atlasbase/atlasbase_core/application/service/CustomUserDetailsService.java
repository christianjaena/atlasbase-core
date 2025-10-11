package com.atlasbase.atlasbase_core.application.service;

import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user.UserEntity;
import com.atlasbase.atlasbase_core.infrastructure.persistence.jpa.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userJpaRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();
    }
}
