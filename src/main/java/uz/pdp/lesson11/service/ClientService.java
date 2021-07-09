package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Client;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    //Create
    public Result addClient(Client client) {
        boolean exists = clientRepository.existsByNameAndPhoneNumber(client.getName(), client.getPhoneNumber());
        if (exists) {
            return new Result("Bunday mijoz mavjud", false);
        }
        clientRepository.save(client);
        return new Result("Mijoz muvaffaqiyatli qo'shildi", true);
    }


    //Read

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    //Update

    public Result editClient(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client editingClient = optionalClient.get();
            editingClient.setName(client.getName());
            editingClient.setPhoneNumber(client.getPhoneNumber());
            editingClient.setActive(client.isActive());
            clientRepository.save(editingClient);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("Mijoz topilmadi", false);
    }

    //Delete
    public Result deleteClient(Integer id) {
        clientRepository.deleteById(id);
        boolean deleted = clientRepository.existsById(id);
        if (deleted) {
            return new Result("Mijoz o'chirildi", true);
        } else {
            return new Result("Mijoz topilmadi", false);
        }
    }
}

