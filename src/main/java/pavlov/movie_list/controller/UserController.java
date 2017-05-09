package pavlov.movie_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.user.model.RegisterModel;
import pavlov.movie_list.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Daniel on 29-Apr-17.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    private String registerGET(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("view", "user/register");
        model.addAttribute("registerModel", new RegisterModel());
        return "base-layout";
    }

    @PostMapping("/register")
    private String postRegister(@Valid @ModelAttribute RegisterModel registerModel, BindingResult bindingResult, Model model) {
        if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("registerModel", "confirmPassword", "Passwords do not match"));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Register");
            model.addAttribute("view", "user/register");
            return "base-layout";
        }

        this.userService.register(registerModel);

        return "redirect:/login";
    }

    @GetMapping("/login")
    private String getlogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("view", "user/login");
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        return "base-layout";
    }
}
