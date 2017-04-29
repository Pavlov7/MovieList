package pavlov.movie_list.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov.movie_list.movie.Movie;

/**
 * Created by Daniel on 26-Apr-17.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
