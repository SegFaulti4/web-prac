package ru.msu.cmc.webprac.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.msu.cmc.webprac.models.ForumUser;

import java.util.List;

@Service
public class ForumUserService implements UserDetailsService {
    @Autowired
    ForumUserDAO forumUserDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ForumUser u = forumUserDAO.getByID(username); //userDAO == null Causing NPE
        if (u == null) {
            throw new UsernameNotFoundException("Oops!");
        }

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(u.getUserrole().toString()));

        return new org.springframework.security.core.userdetails
                .User(u.getId(), passwordEncoder.encode(u.getPasswd()), authorities);
    }

}
