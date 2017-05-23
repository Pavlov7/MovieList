package pavlov.movie_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pavlov.movie_list.constants.Constants;

/**
 * Created by Daniel on 22-Apr-17.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    private String getHome(Model model){
        model.addAttribute("title", "Home");
        model.addAttribute("view", "homepage");
        return Constants.BASE_LAYOUT;
    }
}
