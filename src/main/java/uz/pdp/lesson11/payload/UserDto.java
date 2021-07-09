package uz.pdp.lesson11.payload;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private boolean active;
    private Set<Integer> warehousesId;
}
