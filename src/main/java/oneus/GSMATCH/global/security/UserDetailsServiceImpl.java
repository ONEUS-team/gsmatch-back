package oneus.GSMATCH.global.security;

import lombok.RequiredArgsConstructor;
import oneus.GSMATCH.domain.user.entity.UserEntity;
import oneus.GSMATCH.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    // DB 에 저장된 사용자 정보와 일치 여부
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("Not Found " + name));

        return new UserDetailsImpl(user);
    }
}