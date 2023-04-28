package it.jorge.protectora.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.User;
import it.jorge.protectora.Service.PetsService;
import it.jorge.protectora.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Animal protector")
@RequestMapping("/api")
public class ApiController {
    private final String SECRET = "mySecretKey";
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    PetsService service;
    @Autowired
    UserService serviceUser;


    @Operation(summary = "Log in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in app", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Error when trying to connect to the application")
    })
    @PostMapping("/login")
    public ResponseEntity<?> hashLogin (@RequestBody User user){

        User login = serviceUser.hashLogin(user.getEmail());
        if(login != null){
            if (passwordEncoder.matches(user.getPass(), login.getPass())){
                return ResponseEntity.ok(getJWTToken(login));
            }
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

    }
    @Operation(summary = "Get adoption pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting animals for adoption", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "409", description = "Error when getting animals for adoption")
    })

    @GetMapping("/pets")
    public List<Pet> getPets(){
        return service.getPets();
    }
    @Operation(summary = "Get pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get animal by ID", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "409", description = "Error when get animal by ID ")
    })
    @GetMapping("/pets/{id}")
    public Pet findByIdPets (@PathVariable int id){
        return service.findById(id).get();
    }
    @Operation(summary = "Get user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gets the user by token", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when get user by Token")
    })
    @PostMapping("/user")
    public User getUser(@RequestHeader("Authorization") String jwt){
        Claims claims = getClaims(jwt);
        return serviceUser.findById((Integer) claims.get("id")).get();
    }
    @Operation(summary = "Add User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add user from data base", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when add from data base ", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping("/adduser")
    public User insertUser(@RequestBody User user){

        String pass = user.getPass();
        user.setPass(encodePass(pass));
        return serviceUser.save(user);
    }
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modifies user from data base", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when modifies user from data base", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PutMapping("/user")
    public User updateUser(@RequestBody User user){
        return serviceUser.save(user);
    }
    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remove user from database", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error remove user from database")
    })
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        serviceUser.delete(serviceUser.findById(id).get());
    }
    @Operation(summary = "Pet Adoption")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva hecha!!", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "404", description = "Error a reservar", content = @Content(schema = @Schema(implementation = Pet.class)))
    })
    @PostMapping("adoption")
    public ResponseEntity<?> insertAdoption(@RequestHeader("Authorization") String token ,@RequestParam("id") int petId){

        try{
            Claims claims = getClaims(token);
            Pet pet = service.findById(petId).get();
            User user = serviceUser.findById((Integer)claims.get("id")).get();
            if (pet.isAdoption()){
                return new ResponseEntity(1, HttpStatus.CONFLICT);
            }

            pet.setAdoption(true);
            List<Pet>pets = user.getPets();
            pets.add(pet);
            user.setPets(pets);


            List<User> users = pet.getUsers();
            users.add(user);
            pet.setUsers(users);

            service.save(pet);
            serviceUser.save(user);
            return ResponseEntity.ok("Adoption Completed");

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(2, HttpStatus.CONFLICT);
        }


    }
    @PostMapping("/user/pets")
    public List<Pet> getUserPets(@RequestHeader("Authorization") String jwt){
        System.out.printf(jwt);
        Claims claims = getClaims(jwt);
        User user = serviceUser.findById((Integer) claims.get("id")).get();
        return user.getPets();
    }

    @GetMapping("pets/types")
    public List<String> getTypes(){
       return service.getPetType();
    }

    @GetMapping("pets/type/{type}")
    public ResponseEntity<?> getPetsType(@PathVariable("type") String type){
        try{
            return  ResponseEntity.ok(service.getPetsType(type));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity("Conflict", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("pets/location/{location}")
    public ResponseEntity<?> getPetsLocation(@PathVariable("location") String location){
        try{
            return  ResponseEntity.ok(service.getPetsLocation(location));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity("Conflict", HttpStatus.CONFLICT);
        }
    }

    private String getJWTToken(User user) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        Claims claims = Jwts.claims();
        claims.put("id",user.getId());

        String token = Jwts
                .builder()
                .setId("JWT")
                .setClaims(claims)
                .setSubject("USER TOKEN")
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
    private String encodePass (String pass){

        return passwordEncoder.encode(pass);
    }
    private Claims getClaims (String token){
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token).getBody();
    }

}
