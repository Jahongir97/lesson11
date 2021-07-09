package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.*;
import uz.pdp.lesson11.payload.OutputProductDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.OutputProductRepository;
import uz.pdp.lesson11.repository.OutputRepository;
import uz.pdp.lesson11.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    ProductRepository productRepository;

    //Create
    public Result addOutputProduct(OutputProductDto outputProductDto) {
        boolean exists = outputProductRepository.existsByProductIdAndOutputId(outputProductDto.getProductId(), outputProductDto.getOutputId());
        if (exists) {
            return new Result("Bunday tovar chiqimi mavjud", false);
        }

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Bunday chiqim mavjud emas", false);

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Bunday tovar mavjud emas", false);


        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        optionalOutput.ifPresent(outputProduct::setOutput);
        optionalProduct.ifPresent(outputProduct::setProduct);
        outputProductRepository.save(outputProduct);
        return new Result("Tovar chiqimi saqlandi", true);
    }

    //Read
    public List<OutputProduct> getOutputProducts() {
        return outputProductRepository.findAll();
    }

    public OutputProduct getOutputProductById(Integer id) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        return optionalOutputProduct.orElse(null);
    }

    //Update
    public Result editOutputProduct(Integer id, OutputProductDto  outputProductDto) {
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (optionalOutputProduct.isPresent()) {
            OutputProduct editingOutputProduct = optionalOutputProduct.get();
            Output output = editingOutputProduct.getOutput();
            Product product = editingOutputProduct.getProduct();
            editingOutputProduct.setAmount(outputProductDto.getAmount());
            editingOutputProduct.setPrice(outputProductDto.getPrice());
            optionalOutput.ifPresent(editingOutputProduct::setOutput);
            optionalProduct.ifPresent(editingOutputProduct::setProduct);
            outputRepository.save(output);
            productRepository.save(product);
            outputProductRepository.save(editingOutputProduct);
            return new Result("Tovar chiqimi tahrirlandi", true);
        }
        return new Result("Bunday tovar chiqimi mavjud emas", false);
    }

    //Delete
    public Result deleteOutputProduct(Integer id) {
        outputProductRepository.deleteById(id);
        boolean deleted = outputProductRepository.existsById(id);
        if (deleted) {
            return new Result("Tovar chiqimi o'chirildi", true);
        } else {
            return new Result("Tovar chiqimi topilmadi", false);
        }
    }
}
