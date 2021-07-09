package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.*;
import uz.pdp.lesson11.payload.InputDto;
import uz.pdp.lesson11.payload.InputProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    InputRepository inputRepository;
    @Autowired
    ProductRepository productRepository;

    //Create
    public Result addInputProduct(InputProductDto inputProductDto) {
        boolean exists = inputProductRepository.existsByProductIdAndInputId(inputProductDto.getProductId(), inputProductDto.getInputId());
        if (exists) {
            return new Result("Bunday tovar kirimi mavjud", false);
        }

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Bunday kirim mavjud emas", false);

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday tovar mavjud emas", false);


        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        optionalInput.ifPresent(inputProduct::setInput);
        optionalProduct.ifPresent(inputProduct::setProduct);
        inputProductRepository.save(inputProduct);
        return new Result("Tovar kirim saqlandi", true);
    }

    //Read
    public List<InputProduct> getInputProducts() {
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProductById(Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        return optionalInputProduct.orElse(null);
    }

    //Update
    public Result editInputProduct(Integer id, InputProductDto inputProductDto) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (optionalInputProduct.isPresent()) {
            InputProduct editingInputProduct = optionalInputProduct.get();
            Input input = editingInputProduct.getInput();
            Product product = editingInputProduct.getProduct();
            editingInputProduct.setAmount(inputProductDto.getAmount());
            editingInputProduct.setPrice(inputProductDto.getPrice());
            editingInputProduct.setExpireDate(inputProductDto.getExpireDate());
            optionalInput.ifPresent(editingInputProduct::setInput);
            optionalProduct.ifPresent(editingInputProduct::setProduct);
            inputRepository.save(input);
            productRepository.save(product);
            inputProductRepository.save(editingInputProduct);
            return new Result("Tovar kirimi tahrirlandi", true);
        }
        return new Result("Bunday tovar kirimi mavjud emas", false);
    }

    //Delete
    public Result deleteInputProduct(Integer id) {
        inputProductRepository.deleteById(id);
        boolean deleted = inputProductRepository.existsById(id);
        if (deleted) {
            return new Result("Tovar kirimi o'chirildi", true);
        } else {
            return new Result("Tovar kirimi topilmadi", false);
        }
    }
}
