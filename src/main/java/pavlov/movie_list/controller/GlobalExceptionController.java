package pavlov.movie_list.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pavlov.movie_list.constants.Constants;

/**
 * Created by Daniel on 11-Jul-17.
 */
@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public String genericException(Model model){
        model.addAttribute("title", "Error");
        model.addAttribute("view", "error/generic-exception");
        return Constants.BASE_LAYOUT;
    }
}
