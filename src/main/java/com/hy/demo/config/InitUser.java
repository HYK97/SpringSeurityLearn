package com.hy.demo.config;


import com.hy.demo.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class InitUser {
    private final InitUserService initUserService;





    @PostConstruct //분리해줘야함 transactional 이랑 postConstrct랑. 따라서 static으로 나누어 한것
    public void init(){
        initUserService.init();
    }

    @Component //
    static class InitUserService {

        @PersistenceContext
        private EntityManager em;
        private BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();



        @Transactional
        public void init(){

            User user =User.builder()
                    .username("user")
                    .password("1234")
                    .email("asd@naver.com")
                    .role("ROLE_USER")
                    .build();
            User manager =User.builder()
                    .username("manager")
                    .password("1234")
                    .email("asd@naver.com")
                    .role("ROLE_MANAGER")
                    .build();
            User admin =User.builder()
                    .username("admin")
                    .password("1234")
                    .email("asd@naver.com")
                    .role("ROLE_ADMIN")
                    .build();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));



            em.persist(user);
            em.persist(manager);
            em.persist(admin);


        }
    }
}