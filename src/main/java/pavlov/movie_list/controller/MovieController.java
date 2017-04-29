package pavlov.movie_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pavlov.movie_list.movie.enums.Genre;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.service.MovieService;

import javax.validation.Valid;
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
}
