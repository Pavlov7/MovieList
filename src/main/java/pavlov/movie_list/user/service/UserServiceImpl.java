package pavlov.movie_list.user.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pavlov.movie_list.user.Role;
import pavlov.movie_list.user.User;
import pavlov.movie_list.user.model.RegisterModel;
import pavlov.movie_list.user.repository.RoleRepository;
import pavlov.movie_list.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Daniel on 29-Apr-17.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void register(RegisterModel registerModel) {
        User user = this.modelMapper.map(registerModel, User.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registerModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        Set<Role> userRole = new HashSet<>(Collections.singletonList(this.roleRepository.findOne(new Long(2)))); //TODO: make constant
        user.setAuthorities(userRole);
        this.userRepository.save(user);
    }

    @Override
    public boolean emailTaken(String email) {
        return this.userRepository.findOneByEmail(email) != null;
    }

    @Override
    public boolean usernameTaken(String username) {
        return this.userRepository.findOneByUsername(username) != null;
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findOneByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid Credentials");
        }

        return user;
    }
}
