package it.jorge.protectora.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAdoption {
    private String name ;
    private String identification ;
    private String secondName ;
    private String email;
    private String phone;
    private String bornDate ;
    private String country ;
    private String region ;
    private String address ;
    private Boolean kids;
    private Boolean pets;
    private String typeHome ;
    private String home ;
    private String surface ;
    private int idPet ;
   
}
