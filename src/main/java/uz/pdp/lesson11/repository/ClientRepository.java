package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Client;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
}
