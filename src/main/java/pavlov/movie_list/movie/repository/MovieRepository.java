package pavlov.movie_list.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pavlov.movie_list.movie.Movie;

import java.util.List;

/**
 * Created by Daniel on 26-Apr-17.
 */
@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Page<Movie> getAllByApprovedByIsNotNullOrderByNameAsc(Pageable pageable);
    Page<Movie> getAllByApprovedByIsNotNullOrderByNameDesc(Pageable pageable);
    Page<Movie> getAllByApprovedByIsNullOrderByIdAsc(Pageable pageable);
}
