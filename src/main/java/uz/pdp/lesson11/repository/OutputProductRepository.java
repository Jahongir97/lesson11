package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.OutputProduct;

import java.util.List;

public interface OutputProductRepository extends JpaRepository<OutputProduct, Integer> {
    boolean existsByProductIdAndOutputId(Integer product_id, Integer output_id);

    List<OutputProduct> findAllByOutputIdOrderByAmountDesc(Integer output_id);

}
