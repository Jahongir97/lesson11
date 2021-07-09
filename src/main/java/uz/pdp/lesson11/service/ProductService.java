package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Attachment;
import uz.pdp.lesson11.entity.Category;
import uz.pdp.lesson11.entity.Measurement;
import uz.pdp.lesson11.entity.Product;
import uz.pdp.lesson11.payload.ProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.AttachmentRepository;
import uz.pdp.lesson11.repository.CategoryRepository;
import uz.pdp.lesson11.repository.MeasurementRepository;
import uz.pdp.lesson11.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    //Create
    public Result addProduct(ProductDto productDto) {
        boolean exists = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (exists) {
            return new Result("Bunday mahsulot ushbu kategoriyada mavjud", false);
        }

        //Kategoriyani tekshirish
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya mavjud emas", false);

        //Photoni tekshirish
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday rasm mavjud emas", false);

        //Measurementni tekshirish
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday o'lchov mavjud emas", false);

        //Saqlash
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(UUID.randomUUID().toString());
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("Maxsulot saqlandi", true);
    }

    //Read
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    //Update
    public Result editProduct(Integer id, ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent()) {
            Product editingProduct = optionalProduct.get();
            Category category = editingProduct.getCategory();
            Measurement measurement = editingProduct.getMeasurement();
            Attachment photo = editingProduct.getPhoto();
            editingProduct.setName(productDto.getName());
            optionalCategory.ifPresent(editingProduct::setCategory);
            optionalMeasurement.ifPresent(editingProduct::setMeasurement);
            optionalAttachment.ifPresent(editingProduct::setPhoto);
            categoryRepository.save(category);
            measurementRepository.save(measurement);
            attachmentRepository.save(photo);
            productRepository.save(editingProduct);
            return new Result("Maxsulot tahrirlandi", true);
        }
        return new Result("Bunday mahsulot mavjud emas", false);
    }

    //Delete
    public Result deleteProduct(Integer id) {
        productRepository.deleteById(id);
        boolean deleted = productRepository.existsById(id);
        if (deleted) {
            return new Result("Maxsulot o'chirildi", true);
        } else {
            return new Result("Maxsulot topilmadi", false);
        }
    }


}



