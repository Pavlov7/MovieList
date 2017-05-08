package pavlov.movie_list.movie.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import pavlov.movie_list.movie.anot.IsInThePast;
import pavlov.movie_list.movie.enums.Genre;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Daniel on 25-Apr-17.
 */
public class ValidateMovieModel {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 20, message = "Invalid name size")
    private String name;

    @NotNull(message = "Should be selected")
    private Genre genre;

    @IsInThePast(message = "Is not in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date airDate;

    @NotBlank(message = "Director cannot be blank")
    @Size(min = 3, max = 15, message = "Invalid director name size")
    private String director;

    @NotBlank(message = "Producer cannot be blank")
    @Size(min = 3, max = 15, message = "Invalid producer name size")
    private String producer;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 200,message = "Invalid description size")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
