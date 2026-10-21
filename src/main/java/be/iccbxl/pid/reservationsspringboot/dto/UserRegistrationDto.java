package be.iccbxl.pid.reservationsspringboot.dto;

import be.iccbxl.pid.reservationsspringboot.validation.PasswordConfirmable;
import be.iccbxl.pid.reservationsspringboot.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import be.iccbxl.pid.reservationsspringboot.validation.StrongPassword;

@Data
@PasswordMatches
public class UserRegistrationDto implements PasswordConfirmable {

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 4, max = 20, message = "Le login doit contenir entre 4 et 20 caractères")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @StrongPassword
    private String password;

    @NotBlank(message = "Veuillez confirmer votre mot de passe")
    private String confirmPassword;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "La langue est obligatoire")
    private String langue;
}