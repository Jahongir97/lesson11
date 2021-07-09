package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
}
