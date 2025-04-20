package me.practice.springbootproject2.service;

import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾지 못했습니다."));
    }
    // 해당 인터페이스는 이름으로 사용자를 검색하는 역할만 하므로 하나의 method만 가짐

    // UserService: 일반적으로 사용자의 CRUD 기능을 제공하는 서비스
    // 예를 들어 사용자 정보를 수정하거나, 사용자 목록을 가져오는 등의 작업을 처리

    // UserDetailsService: Spring Security의 인증 과정에서 사용자의 인증 정보를 가져오는 역할만 담당
    // loadUserByUsername() 메소드로 사용자의 사용자명을 기반으로 사용자 정보를 검색, Spring Security의 인증을 위한 UserDetails를 반환
}
