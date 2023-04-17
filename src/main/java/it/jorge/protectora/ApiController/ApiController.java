package it.jorge.protectora.ApiController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.Adopcion;
import it.jorge.protectora.Model.JWT;
import it.jorge.protectora.Model.Mascota;
import it.jorge.protectora.Model.Usuario;
import it.jorge.protectora.Service.AdoptionService;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Protectora" , description = "Controlador para usuarios y animales")
@RequestMapping("/api")
public class ApiController {
    private final String SECRET = "mySecretKey";
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    PetsService service;
    @Autowired
    UserService serviceUser;

    @Autowired
    AdoptionService adoptionService;
    @Operation(summary = "Log In de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprueba que el usuario esta dado de alta en la dase de datos", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no exite con ese nombre o contraseña", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping("/users/login")
    public ResponseEntity<?> hashLogin (@RequestBody Usuario user){

        Usuario login = serviceUser.hashlogin(user.getEmail());
        if(login != null){
            if (passwordEncoder.matches(user.getPass(), login.getPass())){
                return ResponseEntity.ok(new JWT(getJWTToken(login)));
            }
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

    }
    @Operation(summary = "Obtiene todos los animales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprueba que hay animales en la dase de datos", content = @Content(schema = @Schema(implementation = Mascota.class))),
            @ApiResponse(responseCode = "404", description = "No hay animales en la base de datos", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })

    @GetMapping("/pets/{id}")
    public Mascota findByIdPets (@PathVariable int id){
        return service.findById(id).get();
    }
    @Operation(summary = "Obtiene un usuario por el token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el usuario", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping("/user")
    public Usuario getUser(@RequestHeader("Authorization") JWT jwt){
        Claims claims = getClaims(jwt.getToken());
        return serviceUser.findById((Integer) claims.get("id")).get();
    }
    @Operation(summary = "Insertar usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Insertar correctamente", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Error al Insertar", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PostMapping("/adduser")
    public Usuario insertUser(@RequestBody Usuario user){

        String pass = user.getPass();
        user.setPass(encodePass(pass));
        return serviceUser.save(user);
    }
    @Operation(summary = "Modifica usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modificado correctamente", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Error al Modificar", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @PutMapping("/users")
    public Usuario updateUser(@RequestBody Usuario user){
        return serviceUser.save(user);
    }
    @Operation(summary = "Eliminar Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SOY PODEROSOOOO JAJAJAJAJA", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Error al Eliminar", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        serviceUser.delete(serviceUser.findById(id).get());
    }
    @Operation(summary = "Reservar animales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva hecha!!", content = @Content(schema = @Schema(implementation = Mascota.class))),
            @ApiResponse(responseCode = "404", description = "Error a reservar", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })
    @PostMapping("adoption")
    public Adopcion insertAdoption(@RequestBody Adopcion adopcion){
        Mascota pet = service.findById(adopcion.getPet_id()).get();
        pet.setReserva(true);
        service.save(pet);
        return adoptionService.save(adopcion);
    }
    @PostMapping("/user/pets")
    public List<Mascota> getUserPets(@RequestHeader("Authorization") JWT jwt){
        System.out.printf(jwt.getToken());
        Claims claims = getClaims(jwt.getToken());
        Usuario user = serviceUser.findById((Integer) claims.get("id")).get();
        return user.getPets();
    }
    @GetMapping("/pets")
    public List<Mascota> getPets(){
        return service.getPets();
    }
    @GetMapping("pets/types")
    public List<String> getTypes(){
       return service.getPetType();
    }

    private String getJWTToken(Usuario user) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        Claims claims = Jwts.claims();
        claims.put("id",user.getUser_id());
        claims.put("name",user.getName());
        claims.put("email",user.getEmail());
        claims.put("rol",user.getRol());

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
