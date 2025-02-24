package com.music.project.securities;

import com.music.project.api.user.repository.UserRepository;
import com.music.project.api.userRole.repository.UserRoleM2MRepository;
import com.music.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleM2MRepository userRoleM2MRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = username;
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        List<GrantedAuthority> authorities = userRoleM2MRepository.findRolesByUserId(user.getId())
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return UserDetailsImpl.build(user, userRoleM2MRepository.findRolesByUserId(user.getId()));
//            return new UserDetailsImpl(
//                    user.getId(),
//                    user.getEmail(),
//                    user.getEmail(),
//                    user.getPassword(),
//                    authorities,
//                    user.getIsActive());

    }
}
