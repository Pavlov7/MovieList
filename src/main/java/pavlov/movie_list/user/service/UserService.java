package pavlov.movie_list.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pavlov.movie_list.user.User;
import pavlov.movie_list.user.model.RegisterModel;

/**
 * Created by Daniel on 29-Apr-17.
 */
@Service
public interface UserService extends UserDetailsService{
    void register(RegisterModel registerModel);
    boolean emailTaken(String email);
    boolean usernameTaken(String username);
    User getByUsername(String username);
}
