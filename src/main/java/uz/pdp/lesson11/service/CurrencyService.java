package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Currency;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    //Create

    public Result addCurrency(Currency currency) {
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            return new Result("Bunday pul birligi mavjud", false);
        }
        currencyRepository.save(currency);
        return new Result("Muvaffaqiyatli qo'shildi", true);

    }

    //Read

    public List<Currency> getCurrency() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        return optionalCurrency.orElse(null);
    }

    //Update

    public Result editCurrency(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency editingCurrency = optionalCurrency.get();
            editingCurrency.setName(currency.getName());
            editingCurrency.setActive(currency.isActive());
            currencyRepository.save(editingCurrency);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("Pul birligi topilmadi", false);

    }

    //Delete
    public Result deleteCurrency(Integer id) {
        currencyRepository.deleteById(id);
        boolean deleted = currencyRepository.existsById(id);
        if (deleted) {
            return new Result("Pul birligi o'chirildi", true);
        } else {
            return new Result("Pul birligi topilmadi", false);
        }
    }

}
