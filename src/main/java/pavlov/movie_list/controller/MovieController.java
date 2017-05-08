package pavlov.movie_list.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.movie.enums.Genre;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.model.ValidateWatchedMovieModel;
import pavlov.movie_list.movie.model.ViewMovieModel;
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
    private String addMovieGET(Model model){
        model.addAttribute("title", "Add Movie");
        model.addAttribute("view", "movie/add-movie");
        model.addAttribute("validateMovieModel", new ValidateMovieModel());
        return "base-layout";
    }

    @PostMapping("/add")
    private String addMoviePOST(@Valid @ModelAttribute("validateMovieModel") ValidateMovieModel validateMovieModel, BindingResult bindingResult, Model model) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("title", "Add Movie");
                model.addAttribute("view", "movie/add-movie");
                return "base-layout";
            }

            this.movieService.save(validateMovieModel);

            return "redirect:/";
    }

    @GetMapping("")
    private String allMoviesGET(Model model, HttpServletRequest httpServletRequest){
        List<Movie> movies = new ArrayList<>();
        String filter = httpServletRequest.getParameter("filter");
        filter = filter == null ? "Ascending" : filter;
        switch (filter){
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
        return "base-layout";
    }

    @GetMapping("/{id}")
    private String movieInfoGET(@PathVariable(name = "id") Long id, Model model){
        Movie movie = this.movieService.getById(id);

        if (movie == null){
            return "redirect:/movies";
        }

        model.addAttribute("title", movie.getName());
        model.addAttribute("view", "movie/movie-info");
        model.addAttribute("movie", movie);
        return "base-layout";
    }

    @GetMapping("/add-to-list/{id}")
    private String addToListGET(@PathVariable(name = "id") Long id, Model model){
        Movie movie = this.movieService.getById(id);
        if (movie == null){
            return "redirect:/";
        }
        String movieName = movie.getName();
        ValidateWatchedMovieModel validateWatchedMovieModel = new ValidateWatchedMovieModel();
        model.addAttribute("name", movieName);
        model.addAttribute("id", id);
        model.addAttribute("validateWatchedMovieModel", validateWatchedMovieModel);
        model.addAttribute("title", "Add Movie to List");
        model.addAttribute("view", "movie/add-movie-to-list");
        return "base-layout";
    }

    @PostMapping("/add-to-list/{id}") //TODO: add check if already in list
    private String addToListPOST(@Valid @ModelAttribute("validateWatchedMovieModel") ValidateWatchedMovieModel validateWatchedMovieModel, BindingResult bindingResult, Model model, Principal principal, @PathVariable(name = "id") Long id){
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Add Movie To List");
            model.addAttribute("view", "movie/add-movie-to-list");
            return "base-layout";
        }

        this.movieService.addWatchedMovie(validateWatchedMovieModel, principal.getName(), id);

        return "redirect:/movies/my-list";
    }

    @GetMapping("/my-list")
    private String viewListGET(Model model, Principal principal){
        List<WatchedMovie> watchedMovies = this.movieService.getWatchedMoviesByUsername(principal.getName());
        model.addAttribute("title", "My List");
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("view", "user/list");
        return  "base-layout";
    }
}