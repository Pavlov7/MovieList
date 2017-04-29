package pavlov.movie_list.movie.anot;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by Daniel on 25-Mar-17.
 */
public class IsInThePastValidator implements ConstraintValidator<IsInThePast, Date> {
   public void initialize(IsInThePast constraint) {
   }

   public boolean isValid(Date date, ConstraintValidatorContext context) {
      if (date == null){
         return false;
      }
      Date current = new Date();
      return date.before(current);
   }
}
