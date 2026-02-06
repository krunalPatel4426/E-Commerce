package com.demo.e_commerce.service.userService;

import com.demo.e_commerce.model.UserEntity;
import com.demo.e_commerce.repository.userrepo.UserRepository;
import com.demo.e_commerce.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // LOGIC: Check both columns with the same input string
        UserEntity user = userRepository.findByUsernameOrEmailAndIsDeleted(usernameOrEmail, usernameOrEmail, 0)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username or email: " + usernameOrEmail));

        return UserDetailsImpl.build(user);
    }

}
