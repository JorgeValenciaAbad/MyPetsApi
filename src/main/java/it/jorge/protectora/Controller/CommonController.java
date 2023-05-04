package it.jorge.protectora.Controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.User;
import it.jorge.protectora.Service.PetService;
import it.jorge.protectora.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.jorge.protectora.util.Functions.getClaims;

@RestController
@Tag(name = "Common Controller")
@RequestMapping("/api")
public class CommonController {

    @Autowired
    UserService userService;
    @Autowired
    PetService petService;


    @Operation(summary = "Pet Adoption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet's adoption realized", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "404", description = "Error to adoption", content = @Content(schema = @Schema(implementation = Pet.class)))
    })

    @PostMapping("demand")
    public ResponseEntity<?> insertAdoption(@RequestHeader("Authorization") String token, @RequestParam("id") int petId) {

        try {
            Claims claims = getClaims(token);
            Pet pet = petService.findById(petId).get();
            User user = userService.findById((Integer) claims.get("id")).get();

            if (pet.isAdoption()) {

                return new ResponseEntity(1, HttpStatus.CONFLICT);

            } else if (user.getPhone().isEmpty()) {

                return new ResponseEntity(2, HttpStatus.CONFLICT);

            }

            List<Pet> pets = user.getPets();
            pets.add(pet);
            user.setPets(pets);


            List<User> users = pet.getUsers();
            users.add(user);
            pet.setUsers(users);

            petService.save(pet);
            userService.save(user);
            return ResponseEntity.ok("Adoption Completed");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(3, HttpStatus.CONFLICT);
        }


    }
    @PostMapping("/user/pets")
    public List<Pet> getUserPets(@RequestHeader("Authorization") String jwt) {
        System.out.printf(jwt);
        Claims claims = getClaims(jwt);
        User user = userService.findById((Integer) claims.get("id")).get();
        return user.getPets();
    }

}
