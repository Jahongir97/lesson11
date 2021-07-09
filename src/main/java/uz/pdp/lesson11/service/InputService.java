package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Currency;
import uz.pdp.lesson11.entity.Input;
import uz.pdp.lesson11.entity.Supplier;
import uz.pdp.lesson11.entity.Warehouse;
import uz.pdp.lesson11.payload.InputDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.CurrencyRepository;
import uz.pdp.lesson11.repository.InputRepository;
import uz.pdp.lesson11.repository.SupplierRepository;
import uz.pdp.lesson11.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    //Create
    public Result addInput(InputDto inputDto) {
        boolean exists = inputRepository.existsByWarehouseIdAndAndFactureNumber(inputDto.getWarehouseId(), inputDto.getFactureNumber());
        if (exists) {
            return new Result("Bunday kirim mavjud", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor mavjud emas", false);

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Bunday ta'minotchi mavjud emas", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi mavjud emas", false);

        Input input= new Input();
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setCode(UUID.randomUUID().toString());
        optionalWarehouse.ifPresent(input::setWarehouse);
        optionalSupplier.ifPresent(input::setSupplier);
        optionalCurrency.ifPresent(input::setCurrency);
        inputRepository.save(input);
        return new Result("Kirim saqlandi", true);
    }

    //Read
    public List<Input> getInputs() {
        return inputRepository.findAll();
    }

    public Input getInputById(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        return optionalInput.orElse(null);
    }

    //Update
    public Result editInput(Integer id, InputDto inputDto) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (optionalInput.isPresent()) {
            Input editingInput = optionalInput.get();
            Warehouse warehouse = editingInput.getWarehouse();
            Supplier supplier = editingInput.getSupplier();
            Currency currency = editingInput.getCurrency();
            editingInput.setFactureNumber(inputDto.getFactureNumber());
            optionalWarehouse.ifPresent(editingInput::setWarehouse);
            optionalSupplier.ifPresent(editingInput::setSupplier);
            optionalCurrency.ifPresent(editingInput::setCurrency);
            warehouseRepository.save(warehouse);
            supplierRepository.save(supplier);
            currencyRepository.save(currency);
            inputRepository.save(editingInput);
            return new Result("Kirim tahrirlandi", true);
        }
        return new Result("Bunday kirim mavjud emas", false);
    }

    //Delete
    public Result deleteInput(Integer id) {
        inputRepository.deleteById(id);
        boolean deleted = inputRepository.existsById(id);
        if (deleted) {
            return new Result("Kirim o'chirildi", true);
        } else {
            return new Result("Kirim topilmadi", false);
        }
    }
}
