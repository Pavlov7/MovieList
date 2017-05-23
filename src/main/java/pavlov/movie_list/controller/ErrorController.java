package pavlov.movie_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pavlov.movie_list.constants.Constants;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/403")
    private String accessDenied(Model model){
        model.addAttribute("title", "Access Denied");
        model.addAttribute("view", "error/403");
        return Constants.BASE_LAYOUT;
    }
}
