package pavlov.movie_list.api.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.service.MovieService;

/**
 * Created by Daniel on 30-Sep-17.
 */

@RestController
@RequestMapping("/movies")
public class MoviesApiController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/list")
    private Iterable<Movie> allMoviesGET() {
        return this.movieService.getAll();
    }
}
