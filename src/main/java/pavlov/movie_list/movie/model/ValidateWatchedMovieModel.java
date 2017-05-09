package pavlov.movie_list.movie.model;


import org.springframework.format.annotation.DateTimeFormat;
import pavlov.movie_list.movie.anot.IsInThePastOrNull;

import java.util.Date;

/**
 * Created by Daniel on 03-May-17.
 */
public class ValidateWatchedMovieModel {
    private Byte rating;

    @IsInThePastOrNull()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateWatched;

    private boolean isAlreadyInList;

    public boolean getIsAlreadyInList() {
        return isAlreadyInList;
    }

    public void setIsAlreadyInList(boolean alreadyInList) {
        isAlreadyInList = alreadyInList;
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
