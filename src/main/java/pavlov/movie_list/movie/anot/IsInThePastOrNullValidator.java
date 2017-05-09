package pavlov.movie_list.movie.anot;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by Daniel on 25-Mar-17.
 */
public class IsInThePastOrNullValidator implements ConstraintValidator<IsInThePastOrNull, Date> {
   public void initialize(IsInThePastOrNull constraint) {
   }

   public boolean isValid(Date date, ConstraintValidatorContext context) {
      if (date == null){
         return true;
      }
      Date current = new Date();
      return date.before(current);
   }
}
