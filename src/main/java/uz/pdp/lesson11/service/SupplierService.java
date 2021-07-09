package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Supplier;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    //Create
    public Result addSupplier(Supplier supplier) {
        boolean exists = supplierRepository.existsByNameAndPhoneNumber(supplier.getName(), supplier.getPhoneNumber());
        if (exists) {
            return new Result("Bunday ta'minotchi mavjud", false);
        }
        supplierRepository.save(supplier);
        return new Result("Ta'minotchi muvaffaqiyatli qo'shildi", true);
    }


    //Read

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        return optionalSupplier.orElse(null);
    }

    //Update

    public Result editSupplier(Integer id, Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier editingSupplier = optionalSupplier.get();
            editingSupplier.setName(supplier.getName());
            editingSupplier.setPhoneNumber(supplier.getPhoneNumber());
            editingSupplier.setActive(supplier.isActive());
            supplierRepository.save(editingSupplier);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("Ta'minotchi topilmadi", false);
    }

    //Delete
    public Result deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
        boolean deleted = supplierRepository.existsById(id);
        if (deleted) {
            return new Result("Ta'minotchi o'chirildi", true);
        } else {
            return new Result("Ta'minotchi topilmadi", false);
        }
    }
}

