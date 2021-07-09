package uz.pdp.lesson11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson11.entity.Client;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;
    ;

    @PostMapping
    public Result addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    public Result editClient(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.editClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Result deleteClient(@PathVariable Integer id) {
        return clientService.deleteClient(id);
    }
}
