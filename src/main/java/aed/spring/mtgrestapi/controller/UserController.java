package aed.spring.mtgrestapi.controller;

import aed.spring.mtgrestapi.dto.UserRegisterDTO;
import aed.spring.mtgrestapi.model.User;
import aed.spring.mtgrestapi.dto.UserDTO;
import aed.spring.mtgrestapi.repository.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;
    @GetMapping("/login")
    public ResponseEntity<?> logUser(@RequestHeader(name = "user-name") String username,
                                     @RequestHeader(name = "password") String password){

        UserRegisterDTO user = new UserRegisterDTO(username, password);
        if(user.username() == null || user.password() == null)
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        var userFromDB = repository.getUserByUsernameIsAndPasswordIs(username,password);
        if(userFromDB == null) return new ResponseEntity<>("Usuario o contraseña inválidos",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userFromDB,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO user){
        if(user.username() == null || user.password() == null)
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);

        var userFromDB = repository.getUserByUsernameIsAndPasswordIs(user.username(), user.password());
        if(userFromDB != null) return new ResponseEntity<>("Usuario ya existe",HttpStatus.NOT_ACCEPTABLE);
        repository.save(new User(user.username(),user.password()));
        return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        var userFromDB = repository.findById(id);
        if(userFromDB.isEmpty())
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        repository.delete(userFromDB.get());
        return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
        var userDB = repository.findById(id);
        if(userDB.isEmpty())
            return new ResponseEntity<>("no hay user con ese id",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userDB.get(),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeProfileImage(@PathVariable("id") Integer id,
                                                @RequestHeader String img){
        if(img == null)
            return new ResponseEntity<>("Debes incluir la URL de la imagen", HttpStatus.BAD_REQUEST);
        var userDB = repository.findById(id);
        if(userDB.isEmpty())
            return new ResponseEntity<>("Ese usuario no existe",HttpStatus.NOT_FOUND);
        userDB.get().setProfileImg(img);
        repository.save(userDB.get());
        return new ResponseEntity<>(userDB.get(),HttpStatus.OK);
    }

}
