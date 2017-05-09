package pavlov.movie_list.movie.anot;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Daniel on 25-Mar-17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsInThePastOrNullValidator.class)
public @interface IsInThePastOrNull {
    String message() default "Date is not in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
