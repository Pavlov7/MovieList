package pavlov.movie_list.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pavlov.movie_list.movie.enums.Genre;
import pavlov.movie_list.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Daniel on 25-Apr-17.
 */
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    //@Column(name = "airDate")
    private Date airDate;

    private String director;

    private String producer;

    //@Column(name = "watchedBy")
    @JsonIgnore
    @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<WatchedMovie> watchedBy;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "admin_approved_id", referencedColumnName = "id")
    private User approvedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Set<WatchedMovie> getWatchedBy() {
        return watchedBy;
    }

    public void setWatchedBy(Set<WatchedMovie> watchedBy) {
        this.watchedBy = watchedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
