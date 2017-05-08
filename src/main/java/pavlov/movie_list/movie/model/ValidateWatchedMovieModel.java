package pavlov.movie_list.movie.model;


import org.springframework.format.annotation.DateTimeFormat;
import pavlov.movie_list.movie.anot.IsInThePast;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Created by Daniel on 03-May-17.
 */
public class ValidateWatchedMovieModel {
    @Min(value = 1, message = "Rating can only be between 1 and 10")
    @Max(value = 10, message = "Rating can only be between 1 and 10")
    private Byte rating;

    @IsInThePast(message = "Is not in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateWatched;

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
