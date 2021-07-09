package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
