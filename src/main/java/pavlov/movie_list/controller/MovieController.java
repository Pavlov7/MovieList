package pavlov.movie_list.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pavlov.movie_list.constants.Constants;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.movie.enums.Genre;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.model.ValidateWatchedMovieModel;
import pavlov.movie_list.movie.service.MovieService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;

    @ModelAttribute(name = "genres")
    public List<String> getGenres() {
        List<String> genresList = new ArrayList<>();
        Genre[] genres = Genre.values();
        for (Genre genre : genres) {
            genresList.add(genre.toString());
        }

        return genresList;
    }


    @GetMapping("/add")
    private String addMovieGET(Model model) {
        model.addAttribute("title", "Add Movie");
        model.addAttribute("view", "movie/add-movie");
        model.addAttribute("validateMovieModel", new ValidateMovieModel());
        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/add")
    private String addMoviePOST(@Valid @ModelAttribute("validateMovieModel") ValidateMovieModel validateMovieModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add Movie");
            model.addAttribute("view", "movie/add-movie");
            return Constants.BASE_LAYOUT;
        }

        this.movieService.save(validateMovieModel);

        return "redirect:/";
    }

    @GetMapping("")
    private String allMoviesGET(Model model, HttpServletRequest httpServletRequest) {
        List<Movie> movies = new ArrayList<>();
        String filter = httpServletRequest.getParameter("filter");
        filter = filter == null ? "Ascending" : filter;
        switch (filter) {
            case "Ascending":
                movies = this.movieService.getAllAscending();
                break;
            case "Descending":
                movies = this.movieService.getAllDescending();
                break;
        }
        model.addAttribute("movies", movies);
        model.addAttribute("title", "Movies");
        model.addAttribute("view", "movie/all-movies");
        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/{id}")
    private String movieInfoGET(@PathVariable(name = "id") Long id, Model model) {
        Movie movie = this.movieService.getById(id);

        if (movie == null) {
            return "redirect:/movies";
        }

        model.addAttribute("title", movie.getName());
        model.addAttribute("view", "movie/movie-info");
        model.addAttribute("movie", movie);
        return Constants.BASE_LAYOUT;
    }

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

        return "redirect:/movies/my-list";
    }

    @GetMapping("/remove-list/{id}")
    private String removeFromListGET(@PathVariable Long id, Principal principal){
        WatchedMovie movie = this.movieService.getWatchedById(id);
        if (movie != null && movie.getUser().getUsername().equals(principal.getName())){
            this.movieService.deleteWatched(movie);
        }

        return "redirect:/movies/my-list";
    }

    @GetMapping("/my-list")
    private String viewListGET(Model model, Principal principal) {
        List<WatchedMovie> watchedMovies = this.movieService.getWatchedMoviesByUsername(principal.getName());
        model.addAttribute("title", "My List");
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("view", "user/current_user_list");
        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/approve")
    private String approveGET(Model model) {
        List<Movie> movies = this.movieService.getAllNotApproved();
        model.addAttribute("title", "Movies to be approved");
        model.addAttribute("movies", movies);
        model.addAttribute("view", "movie/approve-movies");
        return Constants.BASE_LAYOUT;
    }

    @GetMapping("/approve/{id}")
    private String approveIdGET(@PathVariable Long id, Principal principal) {
        if (movieService.getById(id).getApprovedBy() != null) {
            return "redirect:/movies/approve";
        }
        this.movieService.approveMovie(id, principal.getName());
        return "redirect:/movies/approve";
    }

    @GetMapping("/delete/{id}")
    private String deleteGET(@PathVariable Long id, Model model) {
        Movie movie = movieService.getById(id);
        if (movie == null || movie.getApprovedBy() != null) {
            return "redirect:/movies/approve";
        }
        model.addAttribute("title", "Delete movie");
        model.addAttribute("view", "movie/confirm-delete-movie");
        model.addAttribute("movieTitle", movie.getName());
        return Constants.BASE_LAYOUT;
    }

    @PostMapping("/delete/{id}")
    private String deletePOST(@PathVariable Long id, Model model) {
        Movie movie = movieService.getById(id);
        if (movie == null || movie.getApprovedBy() != null) {
            return "redirect:/movies/approve";
        }

        this.movieService.delete(movie);
        return "redirect:/movies/approve";
    }

    @GetMapping("/{username}/list")
    private String otherListGET(@PathVariable String username, Model model, Principal principal){
        if (principal != null && (principal.getName().equals(username) || username.toLowerCase().equals(principal.getName().toLowerCase()))) {
            return "redirect:/movies/my-list";
        }

        List<WatchedMovie> watchedMovies = this.movieService.getWatchedMoviesByUsername(username);
        model.addAttribute("title", username + "'s List");
        model.addAttribute("username", username);
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("view", "user/other_user_list");
        return Constants.BASE_LAYOUT;
    }
}
