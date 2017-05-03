package pavlov.movie_list.user.model;

import org.hibernate.validator.constraints.NotBlank;
import pavlov.movie_list.user.anot.EmailTaken;
import pavlov.movie_list.user.anot.UsernameTaken;

import javax.validation.constraints.Size;

/**
 * Created by Daniel on 28-Apr-17.
 */
public class RegisterModel {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 10, message = "Invalid username size")
    @UsernameTaken
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, max = 10, message = "Invalid password size")
    private String password;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, max = 10, message = "Invalid password size")
    private String confirmPassword;

    @NotBlank(message = "Email cannot be blank")
    @Size(min = 6, max = 20, message = "Invalid email size")
    @EmailTaken
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
