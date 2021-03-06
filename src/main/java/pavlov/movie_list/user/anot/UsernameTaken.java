package pavlov.movie_list.user.anot;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Daniel on 29-Apr-17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameTakenValidator.class)
public @interface UsernameTaken {
    String message() default "Username is taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
