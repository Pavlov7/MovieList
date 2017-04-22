package pavlov.movie_list.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by Daniel on 22-Apr-17.
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return this.authority;
    }
}
