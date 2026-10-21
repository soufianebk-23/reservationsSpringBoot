package be.iccbxl.pid.reservationsspringboot.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.model.PasswordResetToken;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepo;

    public PasswordResetToken createTokenForUser(User user) {
        String token = UUID.randomUUID().toString();

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUser(user);
        prt.setExpiryDate(LocalDateTime.now().plusHours(1));

        return tokenRepo.save(prt);
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token).orElse(null);

        if (prt == null || prt.isExpired()) {
            return null;
        }

        return prt.getUser();
    }

    @Transactional
    public void deleteToken(String token) {
        tokenRepo.deleteByToken(token);
    }
}