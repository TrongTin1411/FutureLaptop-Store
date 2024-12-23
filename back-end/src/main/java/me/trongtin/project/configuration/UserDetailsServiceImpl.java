package me.trongtin.project.configuration;

import lombok.RequiredArgsConstructor;
import me.trongtin.project.entity.MyUserDetails;
import me.trongtin.project.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUserDetails> userDetails;

        if (username.contains("@"))
            userDetails = userRepository.findUserDetailsByEmail(username);
        else
            userDetails = userRepository.findUserDetailsByUsername(username);

        if (userDetails.isEmpty())
            throw new UsernameNotFoundException("User not found with username: " + username);

        MyUserDetails myUserDetails = userDetails.get();
        myUserDetails.setPassword(passwordEncoder.encode(myUserDetails.getPassword()));

        return myUserDetails;
    }

}
