package be.iccbxl.pid.reservationsspringboot.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import be.iccbxl.pid.reservationsspringboot.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCleanup {

    private final PasswordResetTokenRepository tokenRepo;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void removeExpiredTokens() {
        tokenRepo.findAll().stream()
            .filter(token -> token.getExpiryDate().isBefore(LocalDateTime.now()))
            .forEach(tokenRepo::delete);
    }
}