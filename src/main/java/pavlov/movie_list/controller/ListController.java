package pavlov.movie_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pavlov.movie_list.constants.Constants;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.movie.model.ValidateWatchedMovieModel;
import pavlov.movie_list.movie.service.MovieService;
import pavlov.movie_list.user.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Created by Daniel on 08-Jun-17.
 */
@Controller
public class ListController {

    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @GetMapping("/add-to-list/{id}")
    private String addToListGET(@PathVariable(name = "id") Long id, Model model) {
        Movie movie = this.movieService.getById(id);
        if (movie == null) {
            return "redirect:/";
        }
        String movieName = movie.getName();
        ValidateWatchedMovieModel validateWatchedMovieModel = new ValidateWatchedMovieModel();
        model.addAttribute("name", movieName);
        model.addAttribute("id", id);
        model.addAttribute("validateWatchedMovieModel", validateWatchedMovieModel);
        model.addAttribute("title", "Add Movie to List");
        model.addAttribute("view", "movie/add-movie-to-list");
        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/add-to-list/{id}") //TODO: add edit-in-list
    private String addToListPOST(@Valid @ModelAttribute("validateWatchedMovieModel") ValidateWatchedMovieModel validateWatchedMovieModel, BindingResult bindingResult, Model model, Principal principal, @PathVariable(name = "id") Long id, @RequestParam Byte rating) {
        if (this.movieService.movieAlreadyInList(id, principal.getName())) {
            bindingResult.addError(new FieldError("validateWatchedMovieModel", "isAlreadyInList", Constants.ALREADY_IN_LIST));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add Movie To List");
            model.addAttribute("view", "movie/add-movie-to-list");
            return Constants.BASE_LAYOUT;
        }

        validateWatchedMovieModel.setRating(rating);
        this.movieService.addWatchedMovie(validateWatchedMovieModel, principal.getName(), id);

        return "redirect:/my-list";
    }

    @GetMapping("/remove-list/{id}")
    private String removeFromListGET(@PathVariable Long id, Principal principal){
        WatchedMovie movie = this.movieService.getWatchedById(id);
        if (movie != null && movie.getUser().getUsername().equals(principal.getName())){
            this.movieService.deleteWatched(movie);
        }

        return "redirect:/my-list";
    }

    @GetMapping("/my-list")
    private String viewListGET(Model model, Principal principal) {
        List<WatchedMovie> watchedMovies = this.movieService.getWatchedMoviesByUsername(principal.getName());
        model.addAttribute("title", "My List");
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("view", "user/current_user_list");
        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/{username}/list")
    private String otherListGET(@PathVariable String username, Model model, Principal principal){
        boolean usernameSameAsInURL = principal != null && (principal.getName().equals(username) || username.toLowerCase().equals(principal.getName().toLowerCase()));
        if (usernameSameAsInURL || !userService.usernameTaken(username)) {
            return "redirect:/my-list";
        }

        List<WatchedMovie> watchedMovies = this.movieService.getWatchedMoviesByUsername(username);
        model.addAttribute("title", username + "'s List");
        model.addAttribute("username", username);
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("view", "user/other_user_list");
        return Constants.BASE_LAYOUT;
    }
}
