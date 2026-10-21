package be.iccbxl.pid.reservationsspringboot.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.reservationsspringboot.model.PasswordResetToken;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.service.EmailService;
import be.iccbxl.pid.reservationsspringboot.service.PasswordResetTokenService;
import be.iccbxl.pid.reservationsspringboot.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final PasswordResetTokenService tokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) final Boolean loginRequired,
            @RequestParam(required = false) final Boolean loginError,
            @RequestParam(required = false) final Boolean logoutSuccess,
            final Model model) {

        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Vous devez vous connecter pour avoir accès.");
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Échec de la connexion !");
        }
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute("successMessage", "Vous êtes déconnecté avec succès.");
        }

        return "authentication/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "authentication/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);

        if (user != null) {
            PasswordResetToken token = tokenService.createTokenForUser(user);
            emailService.sendPasswordResetMail(user.getEmail(), token.getToken());
        }

        model.addAttribute("successMessage",
                "Si un compte existe pour cet e-mail, vous recevrez un lien de réinitialisation.");
        return "authentication/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        User user = tokenService.validatePasswordResetToken(token);

        if (user == null) {
            model.addAttribute("errorMessage", "Lien invalide ou expiré.");
            return "authentication/reset-password";
        }

        model.addAttribute("token", token);
        return "authentication/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            return "authentication/reset-password";
        }

        User user = tokenService.validatePasswordResetToken(token);
        if (user == null) {
            model.addAttribute("errorMessage", "Lien invalide ou expiré.");
            return "authentication/reset-password";
        }

        user.setPassword(passwordEncoder.encode(password));
        userService.updateUser(user.getId(), user);
        tokenService.deleteToken(token);

        return "authentication/reset-success";
    }
}