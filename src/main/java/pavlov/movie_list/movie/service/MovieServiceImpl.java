package pavlov.movie_list.movie.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.WatchedMovie;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.model.ValidateWatchedMovieModel;
import pavlov.movie_list.movie.repository.MovieRepository;
import pavlov.movie_list.movie.repository.WatchedMovieRepository;
import pavlov.movie_list.user.repository.UserRepository;
import pavlov.movie_list.user.service.UserService;

import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Override
    public void save(ValidateMovieModel validateMovieModel) {
        Movie movie = this.modelMapper.map(validateMovieModel, Movie.class);
        this.movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getAllAscending(Pageable page) {
        return this.movieRepository.getAllByApprovedByIsNotNullOrderByNameAsc(page);
    }

    @Override
    public Page<Movie> getAllDescending(Pageable page) {
        return this.movieRepository.getAllByApprovedByIsNotNullOrderByNameDesc(page);
    }

    @Override
    public Page<Movie> getAllNotApproved(Pageable page) {
        return this.movieRepository.getAllByApprovedByIsNullOrderByIdAsc(page);
    }

    @Override
    public Movie getById(Long id) {
        return this.movieRepository.findOne(id);
    }

    @Override
    public void addWatchedMovie(ValidateWatchedMovieModel validateWatchedMovieModel, String username, Long movieId) {
        WatchedMovie watchedMovie = this.modelMapper.map(validateWatchedMovieModel, WatchedMovie.class);
        watchedMovie.setUser(this.userService.getByUsername(username));
        watchedMovie.setMovie(this.movieRepository.findOne(movieId));
        this.watchedMovieRepository.save(watchedMovie);
    }

    @Override
    public List<WatchedMovie> getWatchedMoviesByUsername(String username) {
        return this.watchedMovieRepository.findAllByUsername(username);
    }

    @Override
    public boolean movieAlreadyInList(Long movieId, String username) {
        return this.watchedMovieRepository.findOneByMovieIdAndUsername(movieId, username) != null;
    }

    @Override
    public void approveMovie(Long id, String adminUsername) {
        Movie movie = this.movieRepository.findOne(id);
        movie.setApprovedBy(this.userService.getByUsername(adminUsername));
        this.movieRepository.save(movie);
    }

    @Override
    public void delete(Movie movie) {
        this.movieRepository.delete(movie);
    }

    @Override
    public WatchedMovie getWatchedById(Long id) {
        return this.watchedMovieRepository.getOne(id);
    }

    @Override
    public void deleteWatched(WatchedMovie movie) {
        this.watchedMovieRepository.delete(movie);
    }

    @Override
    public Iterable<Movie> getAll() {
        return this.movieRepository.findAll();
    }
}
