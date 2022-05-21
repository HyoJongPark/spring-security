package study.springsecurityform.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import study.springsecurityform.account.Account;
import study.springsecurityform.account.AccountService;
import study.springsecurityform.book.Book;
import study.springsecurityform.book.BookRepository;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account park = createUser("park");
        Account kim = createUser("kim");
        accountService.createNew(park);
        accountService.createNew(kim);

        createBook("spring", park);
        createBook("hibernate", kim);
    }

    private void createBook(String title, Account park) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(park);
        bookRepository.save(book);
    }

    private Account createUser(String username) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword("123");
        account.setRole("USER");
        return account;
    }
}
