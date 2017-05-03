package pavlov.movie_list.user.anot;

import org.springframework.beans.factory.annotation.Autowired;
import pavlov.movie_list.user.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Daniel on 29-Apr-17.
 */
public class EmailTakenValidator implements ConstraintValidator<EmailTaken, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailTaken emailTaken) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !this.userService.emailTaken(email);
    }
}
