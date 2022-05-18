package study.springsecurityform.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    /**
     * 스프링 시큐리티에서 사용자 정보를 입력해 테스트하는 방법
     * 1. 시큐리티 제공 애노테이션 사용
     * 2. 시큐리티 제공 애노테이션을 이용해 자주 쓰는 애노테이션을 직접 작성
     * 3. 애노테이션을 사용하지 않고 사용자 정보 입력
     */
    @Test
    @WithAnonymousUser //스프링 시큐리티에서 제공하는 익명 사용자 애노테이션
    void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser //직접 작성한 애노테이션
    void index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //애노테이션을 사용하지 않는 방식
    @Test
    void index_admin() throws Exception {
        mockMvc.perform(get("/").with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void admin_user() throws Exception {
        mockMvc.perform(get("/admin").with(user("hyojong").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_admin() throws Exception {
        mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * 스프링 시큐리티에서 로그인 성공, 실패를 테스트하는 방법
     * 데이터베이스와 연관된 테스트에서는 @Transactional 을 선언해 테스트가 독립적일 수 있도록 작성
     */
    //user.getPassword() 같이 가져오면 암호화 된 값이 바로 가져와져 인증에 실패한다.
    @Test
    @Transactional
    void login_success() throws Exception {
        String username = "hyojong";
        String password = "123";
        Account user = createUser(username, password);
        mockMvc.perform(formLogin().user(username).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    void login_fail() throws Exception {
        String username = "hyojong";
        String password = "123";
        Account user = createUser(username, password);
        mockMvc.perform(formLogin().user(username).password(password+"12312"))
                .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}