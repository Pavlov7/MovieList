package pavlov.movie_list.movie;

import pavlov.movie_list.user.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Daniel on 02-May-17.
 */
@Entity
@Table(name = "watched_movies")
public class WatchedMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    private Byte rating;

    private Date dateWatched;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public Date getDateWatched() {
        return dateWatched;
    }

    public void setDateWatched(Date dateWatched) {
        this.dateWatched = dateWatched;
    }
}
