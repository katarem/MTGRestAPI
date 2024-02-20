package aed.spring.mtgrestapi.controller;

import aed.spring.mtgrestapi.model.User;
import aed.spring.mtgrestapi.model.UserDTO;
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
    public ResponseEntity<?> logUser(@RequestBody User user){
        if(user.getUsername() == null || user.getPassword() == null)
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        var userName = user.getUsername();
        var password = user.getPassword();
        var userFromDB = repository.getUserByUsernameIsAndPasswordIs(userName,password);
        if(userFromDB == null) return new ResponseEntity<>("Usuario o contraseña inválidos",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(userFromDB.getId(), userName, true),HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        if(user.getUsername() == null || user.getPassword() == null)
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);

        var userFromDB = repository.getUserByUsernameIsAndPasswordIs(user.getUsername(), user.getPassword());
        if(userFromDB != null) return new ResponseEntity<>("Usuario ya existe",HttpStatus.BAD_REQUEST);
        repository.save(user);
        return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathParam("id") int id){
        var userFromDB = repository.findById(id);
        if(userFromDB.isEmpty())
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.ACCEPTED);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

}
