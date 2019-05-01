package com.icecream.server.service.user;

import com.icecream.server.entity.CurrentUser;
import com.icecream.server.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(UserSecurityService.class);
    private final UserService userService;

    @Autowired
    public UserSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug(String.format("Authenticating user with phone=%s", username));
        User user = userService.findByPhoneNumber(username);
        if(null == user) {
            throw new UsernameNotFoundException("Phone number "+username+" not found");
        }
        return new CurrentUser(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LOGGER.debug(String.format("Authenticating user with phone=%s", username));
//        User user = userService.findByPhoneNumber(username);
//        if(null == user) {
//            throw new UsernameNotFoundException("Phone number "+username+" not found");
//        }
//        return user;
//    }

}
