package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.iccbxl.pid.reservationsspringboot.model.PasswordResetToken;
import be.iccbxl.pid.reservationsspringboot.model.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser(User user);
}