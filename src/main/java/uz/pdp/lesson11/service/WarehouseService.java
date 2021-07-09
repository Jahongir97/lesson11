package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Warehouse;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    //Create

    public Result addWarehouse(Warehouse warehouse) {
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName) {
            return new Result("Bunday ombor mavjud", false);
        }
        warehouseRepository.save(warehouse);
        return new Result("Muvaffaqiyatli qo'shildi", true);

    }

    //Read

    public List<Warehouse> getWarehouse() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        return optionalWarehouse.orElse(null);
    }

    //Update

    public Result editWarehouse(Integer id, Warehouse warehouse) {
        Optional<Warehouse> optionalCurrency = warehouseRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Warehouse editingCurrency = optionalCurrency.get();
            editingCurrency.setName(warehouse.getName());
            editingCurrency.setActive(warehouse.isActive());
            warehouseRepository.save(editingCurrency);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("Ombor topilmadi", false);

    }

    //Delete
    public Result deleteWarehouse(Integer id) {
        warehouseRepository.deleteById(id);
        boolean deleted = warehouseRepository.existsById(id);
        if (deleted) {
            return new Result("Ombor o'chirildi", true);
        } else {
            return new Result("Ombor topilmadi", false);
        }
    }

}
