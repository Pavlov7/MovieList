package pavlov.movie_list.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pavlov.movie_list.user.Role;

/**
 * Created by Daniel on 20-May-17.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
