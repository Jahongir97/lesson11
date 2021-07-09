package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
