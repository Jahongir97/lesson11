package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.lesson11.entity.Output;

import java.time.LocalDateTime;
import java.util.List;

public interface OutputRepository extends JpaRepository<Output, Integer> {
    boolean existsByWarehouseIdAndAndFactureNumber(Integer warehouse_id, String facture_number);

    @Query(value = "select * from output where date BETWEEN :startDate AND :endDate",nativeQuery = true)
    List<Output> getOutputIdBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
