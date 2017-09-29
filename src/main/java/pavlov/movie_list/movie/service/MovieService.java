package pavlov.movie_list.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.model.ValidateWatchedMovieModel;

import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Service
public interface MovieService {
    void save(ValidateMovieModel validateMovieModel);

    Page<Movie> getAllAscending(Pageable page);

    Page<Movie> getAllDescending(Pageable page);

    Page<Movie> getAllNotApproved(Pageable page);

    Movie getById(Long id);

    void addWatchedMovie(ValidateWatchedMovieModel validateWatchedMovieModel, String username, Long movieId);

    List<WatchedMovie> getWatchedMoviesByUsername(String username);

    boolean movieAlreadyInList(Long movieId, String username);

    void approveMovie(Long id, String adminUsername);

    void delete(Movie movie);

    WatchedMovie getWatchedById(Long id);

    void deleteWatched(WatchedMovie movie);

    Iterable<Movie> getAll();
}
