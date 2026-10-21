package be.iccbxl.pid.reservationsspringboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import be.iccbxl.pid.reservationsspringboot.dto.UserRegistrationDto;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.model.UserRole;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;
import be.iccbxl.pid.reservationsspringboot.dto.UserProfileDto;
import org.springframework.transaction.annotation.Transactional;
import be.iccbxl.pid.reservationsspringboot.repository.PasswordResetTokenRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isLoginAndEmailAvailable(String login, String email) {
        return !userRepository.existsByLogin(login) && !userRepository.existsByEmail(email);
    }

    public void registerFromDto(UserRegistrationDto dto) {
        User user = new User();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setLogin(dto.getLogin());
        user.setEmail(dto.getEmail());
        user.setLangue(dto.getLangue());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.MEMBER);
        userRepository.save(user);
    }

    public void updateUser(long id, User user) {
        userRepository.save(user);
    }
    
    public void updateUserFromDto(UserProfileDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setLangue(dto.getLangue());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void deleteByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            tokenRepository.deleteByUser(user);
            userRepository.delete(user);
        }
    }
}