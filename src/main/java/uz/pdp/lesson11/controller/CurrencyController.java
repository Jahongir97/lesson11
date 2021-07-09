package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Currency;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result addCurrency(@RequestBody Currency currency) {
        return currencyService.addCurrency(currency);
    }

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getCurrency();
    }

    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable Integer id) {
        return currencyService.getCurrencyById(id);
    }

    @PutMapping("/{id}")
    public Result editCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        return currencyService.editCurrency(id, currency);
    }

    @DeleteMapping("/{id}")
    public Result deleteCurrency(@PathVariable Integer id) {
        return currencyService.deleteCurrency(id);
    }
}
