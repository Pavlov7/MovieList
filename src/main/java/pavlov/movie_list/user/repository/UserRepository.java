package pavlov.movie_list.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlov.movie_list.user.User;

/**
 * Created by Daniel on 29-Apr-17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findOneByUsername(String username);
    User findOneByEmail(String email);
}
