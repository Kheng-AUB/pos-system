package com.kheng.pos.configurations.security.service.impl;

import com.kheng.pos.databases.user.entities.UserInfo;
import com.kheng.pos.databases.user.repositories.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserImpl implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoRepository.findByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                userInfo.getRole().toString());
        Collection<GrantedAuthority> authorities =
                Collections.singletonList(grantedAuthority);

        return new User(
                userInfo.getEmail(), userInfo.getPassword(), authorities
        );
    }
}
