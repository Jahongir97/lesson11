package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.lesson11.entity.Input;

import java.time.LocalDateTime;
import java.util.List;


public interface InputRepository extends JpaRepository<Input, Integer> {
    boolean existsByWarehouseIdAndAndFactureNumber(Integer warehouse_id, String facture_number);

    @Query(value = "select * from input where date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Input> getAllInputIdBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
