package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.*;
import uz.pdp.lesson11.payload.InputDto;
import uz.pdp.lesson11.payload.OutputDto;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    //Create
    public Result addOutput(OutputDto outputDto) {
        boolean exists = outputRepository.existsByWarehouseIdAndAndFactureNumber(outputDto.getWarehouseId(), outputDto.getFactureNumber());
        if (exists) {
            return new Result("Bunday chiqim mavjud", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday ombor mavjud emas", false);

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new Result("Bunday mijoz mavjud emas", false);

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Bunday pul birligi mavjud emas", false);

        Output output= new Output();
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setCode(UUID.randomUUID().toString());
        optionalWarehouse.ifPresent(output::setWarehouse);
        optionalClient.ifPresent(output::setClient);
        optionalCurrency.ifPresent(output::setCurrency);
        outputRepository.save(output);
        return new Result("Chiqim saqlandi", true);
    }

    //Read
    public List<Output> getOutput() {
        return outputRepository.findAll();
    }

    public Output getOutputById(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        return optionalOutput.orElse(null);
    }

    //Update
    public Result editOutput(Integer id, OutputDto outputDto) {
        Optional<Output> optionalInput = outputRepository.findById(id);
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (optionalInput.isPresent()) {
            Output editingOutput = optionalInput.get();
            Warehouse warehouse = editingOutput.getWarehouse();
            Client supplier = editingOutput.getClient();
            Currency currency = editingOutput.getCurrency();
            editingOutput.setFactureNumber(outputDto.getFactureNumber());
            optionalWarehouse.ifPresent(editingOutput::setWarehouse);
            optionalClient.ifPresent(editingOutput::setClient);
            optionalCurrency.ifPresent(editingOutput::setCurrency);
            warehouseRepository.save(warehouse);
            clientRepository.save(supplier);
            currencyRepository.save(currency);
            outputRepository.save(editingOutput);
            return new Result("Chiqim tahrirlandi", true);
        }
        return new Result("Bunday chiqim mavjud emas", false);
    }

    //Delete
    public Result deleteOutput(Integer id) {
        outputRepository.deleteById(id);
        boolean deleted = outputRepository.existsById(id);
        if (deleted) {
            return new Result("Chiqim o'chirildi", true);
        } else {
            return new Result("Chiqim topilmadi", false);
        }
    }
}
