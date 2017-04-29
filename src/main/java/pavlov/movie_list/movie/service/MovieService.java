package pavlov.movie_list.movie.service;

import org.springframework.stereotype.Service;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.model.ValidateMovieModel;

import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Service
public interface MovieService {
    void save(ValidateMovieModel validateMovieModel);

    List<Movie> getAll();
}
