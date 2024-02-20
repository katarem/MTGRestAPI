package aed.spring.mtgrestapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {



    public ResponseEntity<?> logUser(@PathParam("username") String userName,
                                     @PathParam("password") String password){

        if(userName == null || password == null)
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("login con éxito",HttpStatus.OK);
    }


}
