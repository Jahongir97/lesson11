package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.User;
import uz.pdp.lesson11.entity.Warehouse;
import uz.pdp.lesson11.payload.Result;
import uz.pdp.lesson11.payload.UserDto;
import uz.pdp.lesson11.repository.UserRepository;
import uz.pdp.lesson11.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    //Create
    public Result addUser(UserDto userDto) {
        boolean exists = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (exists) {
            return new Result("Bunday foydalanuvchi mavjud", false);
        }
        List<Warehouse> warehouseList = warehouseRepository.findAllById(userDto.getWarehousesId());
        if (warehouseList.isEmpty()) {
            return new Result("Omborlar topilmadi", false);
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.isActive());
        user.setCode(UUID.randomUUID().toString());
        user.setPhoneNumber(userDto.getPhoneNumber());

        user.setWarehouses((Set<Warehouse>) warehouseList.subList(0, warehouseList.size()));
        userRepository.save(user);
        return new Result("Foydalanuvchi muvaffaqiyatli qo'shildi", true);
    }


    //Read

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    //Update

    public Result editUser(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<Warehouse> warehouseList = warehouseRepository.findAllById(userDto.getWarehousesId());
        if (optionalUser.isPresent()) {
            User editingUser = optionalUser.get();
            editingUser.setFirstName(userDto.getFirstName());
            editingUser.setLastName(userDto.getLastName());
            editingUser.setCode(UUID.randomUUID().toString());
            editingUser.setPassword(userDto.getPassword());
            editingUser.setPhoneNumber(userDto.getPhoneNumber());
            editingUser.setActive(userDto.isActive());
            if (!warehouseList.isEmpty()) {
                editingUser.setWarehouses((Set<Warehouse>) warehouseList.subList(0, warehouseList.size()));
            }
            userRepository.save(editingUser);
            return new Result("Muvaffaqiyatli tahrirlandi", true);
        }
        return new Result("Foydalanuvchi topilmadi", false);
    }

    //Delete
    public Result deleteUser(Integer id) {
        userRepository.deleteById(id);
        boolean deleted = userRepository.existsById(id);
        if (deleted) {
            return new Result("Foydalanuvchi o'chirildi", true);
        } else {
            return new Result("Foydalanuvchi topilmadi", false);
        }
    }
}
