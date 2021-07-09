package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.lesson11.entity.InputProduct;

import java.util.Date;
import java.util.List;


public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {
    boolean existsByProductIdAndInputId(Integer product_id, Integer input_id);

    List<InputProduct> findAllByInputId(Integer input_id);

    @Query(value = "select * from input_product where expire_date <= :cer_date", nativeQuery = true)
    List<InputProduct> findAllExpiring(Date cer_date);


}
