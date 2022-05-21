package study.springsecurityform.form;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import study.springsecurityform.account.Account;
import study.springsecurityform.account.AccountService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    //메서드 시큐리티를 적용해 메서드에 접근이 불가능함
    @Test
    @WithMockUser
    void dashboard() {
//        //계정 생성
//        Account account = new Account();
//        account.setRole("USER");
//        account.setUsername("park");
//        account.setPassword("123");
//        accountService.createNew(account);
//
//        //계정 찾기 - 이때 계정 타입은 UserDetails 타입
//        UserDetails userDetails = accountService.loadUserByUsername("park");
//
//        //인증
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
//        Authentication authenticate = authenticationManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authenticate);

        sampleService.dashboard();
    }
}