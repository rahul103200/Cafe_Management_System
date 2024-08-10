package com.in.cafe.JWT;

import com.in.cafe.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerUserService {

    @Autowired
    UserDao userDao;

    private com.in.cafe.POJO.User userDetail;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                log.info("Inside loadUserByUsername {}",username);
                userDetail = userDao.findByEmailId(username);
                if(!Objects.isNull(userDetail))
                    return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
                else
                    throw new UsernameNotFoundException("User not found.");
            }
        };
    }

    public com.in.cafe.POJO.User getUserDetail(){
        return userDetail;
    }
}
