package be.iccbxl.pid.reservationsspringboot.dto;

import be.iccbxl.pid.reservationsspringboot.validation.PasswordConfirmable;
import be.iccbxl.pid.reservationsspringboot.validation.PasswordMatches;
import be.iccbxl.pid.reservationsspringboot.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatches(message = "Les mots de passe ne correspondent pas")
public class UserProfileDto implements PasswordConfirmable {

    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "La langue est obligatoire")
    private String langue;

    private String login;
    private String role;

    @StrongPassword
    private String password;

    private String confirmPassword;
}