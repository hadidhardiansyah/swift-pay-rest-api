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

import static com.hadid.swiftpay.exception.BusinessErrorCodes.ACCOUNT_NOT_ACTIVATED;
import static com.hadid.swiftpay.exception.BusinessErrorCodes.INVALID_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        User user = userRepository.findByIdentifier(credential)
                .orElseThrow(() -> new BusinessException(INVALID_CREDENTIALS));

        if (!user.isEnabled()) {
            throw new BusinessException(ACCOUNT_NOT_ACTIVATED);
        }

        return new UserPrincipal(user);
    }
}
