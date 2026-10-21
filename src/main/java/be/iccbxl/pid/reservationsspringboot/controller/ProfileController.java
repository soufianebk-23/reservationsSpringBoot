package be.iccbxl.pid.reservationsspringboot.controller;

import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import be.iccbxl.pid.reservationsspringboot.dto.UserProfileDto;
import be.iccbxl.pid.reservationsspringboot.model.Language;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;
import be.iccbxl.pid.reservationsspringboot.service.UserService;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setLangue(user.getLangue());
        dto.setLogin(user.getLogin());
        dto.setRole(user.getRole().getValue());

        String languageName = Arrays.stream(Language.values())
                .filter(lang -> lang.toString().equalsIgnoreCase(user.getLangue()))
                .map(Language::getDescription)
                .findFirst()
                .orElse("Inconnue");

        model.addAttribute("user_language", languageName);
        model.addAttribute("user", dto);

        return "authentication/profile";
    }
}