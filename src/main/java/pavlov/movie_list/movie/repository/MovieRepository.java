package pavlov.movie_list.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlov.movie_list.movie.Movie;

import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> getAllByApprovedByIsNotNullOrderByNameAsc();
    List<Movie> getAllByApprovedByIsNotNullOrderByNameDesc();
    List<Movie> getAllByApprovedByIsNullOrderByIdAsc();
}
