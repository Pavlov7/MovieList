package pavlov.movie_list.user.anot;

import org.springframework.beans.factory.annotation.Autowired;
import pavlov.movie_list.user.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Daniel on 29-Apr-17.
 */
public class UsernameTakenValidator implements ConstraintValidator<UsernameTaken, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameTaken usernameTaken) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !this.userService.usernameTaken(username);
    }
}
