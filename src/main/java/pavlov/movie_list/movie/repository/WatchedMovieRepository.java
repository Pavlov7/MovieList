package pavlov.movie_list.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pavlov.movie_list.movie.WatchedMovie;

import java.util.List;

/**
 * Created by Daniel on 03-May-17.
 */
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Long> {
    @Query(value = "SELECT * FROM watched_movies WHERE username = ?1", nativeQuery = true)
    List<WatchedMovie> findAllByUsername(String username);
}
