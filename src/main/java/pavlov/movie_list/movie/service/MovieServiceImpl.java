package pavlov.movie_list.movie.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pavlov.movie_list.movie.Movie;
import pavlov.movie_list.movie.model.ValidateMovieModel;
import pavlov.movie_list.movie.repository.MovieRepository;

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

    @Override
    public void save(ValidateMovieModel validateMovieModel) {
        Movie movie = this.modelMapper.map(validateMovieModel, Movie.class);
        this.movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }
}
