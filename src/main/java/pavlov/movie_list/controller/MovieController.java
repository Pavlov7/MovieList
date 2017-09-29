package pavlov.movie_list.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private String allMoviesGET(Model model, @PageableDefault(Constants.MOVIES_PER_PAGE) Pageable pageable, HttpServletRequest httpServletRequest) {
        Page<Movie> movies = null;
        String filter = httpServletRequest.getParameter("filter");
        filter = filter == null ? "Ascending" : filter;
        switch (filter) {
            case "Ascending":
                movies = this.movieService.getAllAscending(pageable);
                break;
            case "Descending":
                movies = this.movieService.getAllDescending(pageable);
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

    @GetMapping("/approve")
    private String approveGET(Model model, @PageableDefault(Constants.MOVIES_PER_PAGE) Pageable pageable) {
        Page<Movie> movies = this.movieService.getAllNotApproved(pageable);
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
}
