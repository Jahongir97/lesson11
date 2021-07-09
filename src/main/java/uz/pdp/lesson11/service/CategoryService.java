package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Attachment;
import uz.pdp.lesson11.entity.Category;
import uz.pdp.lesson11.entity.Measurement;
import uz.pdp.lesson11.entity.Product;
import uz.pdp.lesson11.payload.CategoryDto;
import uz.pdp.lesson11.payload.ProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    //Create
    public Result addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent())
                return new Result("Bunday kategoriya mavjud emas", false);
            category.setParentCategory(optionalCategory.get());
        }
        categoryRepository.save(category);
        return new Result("Muvaffaqiyatli saqlandi", true);
    }

    //Read
    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }


    //Update
    public Result editCategory(Integer id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
        if (optionalCategory.isPresent()) {
            Category editingCategory = optionalCategory.get();
            Category parentCategory = editingCategory.getParentCategory();
            editingCategory.setName(categoryDto.getName());
            optionalParentCategory.ifPresent(editingCategory::setParentCategory);
            categoryRepository.save(parentCategory);
            categoryRepository.save(editingCategory);
            return new Result("Kategotiya tahrirlandi", true);
        }

        return new Result("Bunday kategoriya mavjud emas", false);
    }

    //Delete
    public Result deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        boolean deleted = categoryRepository.existsById(id);
        if (deleted) {
            return new Result("Kategoriya o'chirildi", true);
        } else {
            return new Result("Kategoriya topilmadi", false);
        }
    }

}
