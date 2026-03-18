package com.kheng.pos.configurations.security.service.impl;

import com.kheng.pos.databases.pg.userinfo.entity.UserInformation;
import com.kheng.pos.databases.pg.userinfo.entity.UserRole;
import com.kheng.pos.databases.pg.userinfo.repository.UserInformationRepository;
import com.kheng.pos.databases.pg.userinfo.repository.UserRoleRepository;
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
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInformation userInfo = userInformationRepository.findByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserRole role = userRoleRepository.findById(userInfo.getRoleId()).orElse(null);
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user: " + username);
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                role.getRoleType());
        Collection<GrantedAuthority> authorities =
                Collections.singletonList(grantedAuthority);

        return new User(
                userInfo.getEmail(), userInfo.getPassword(), authorities
        );
    }
}
