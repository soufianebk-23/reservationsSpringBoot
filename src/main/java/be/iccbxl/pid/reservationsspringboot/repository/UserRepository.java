package be.iccbxl.pid.reservationsspringboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.reservationsspringboot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    List<User> findByLastname(String lastname);
    User findById(long id);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
}