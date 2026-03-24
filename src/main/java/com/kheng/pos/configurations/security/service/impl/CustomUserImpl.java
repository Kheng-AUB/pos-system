package com.kheng.pos.configurations.security.service.impl;

import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
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
    private final UserInformationRepository userInformationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInformation userInfo = userInformationRepository.findByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(STR."User not found with username: \{username}");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
               userInfo.getUserRole().name());
        Collection<GrantedAuthority> authorities =
                Collections.singletonList(grantedAuthority);

        return new User(
                userInfo.getEmail(), userInfo.getPassword(), authorities
        );
    }
}
