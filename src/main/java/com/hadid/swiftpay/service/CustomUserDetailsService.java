package com.hadid.swiftpay.service;

import com.hadid.swiftpay.entity.User;
import com.hadid.swiftpay.entity.UserPrincipal;
import com.hadid.swiftpay.exception.BusinessException;
import com.hadid.swiftpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.hadid.swiftpay.exception.BusinessErrorCodes.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        return new UserPrincipal(user);
    }
}
