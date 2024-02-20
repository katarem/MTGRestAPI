package aed.spring.mtgrestapi.controller;

import aed.spring.mtgrestapi.repository.DeckRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeck(@PathParam("id") int id){
        var deckFromDB = repository.findById(id);
        if(deckFromDB.isEmpty())
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(deckFromDB, HttpStatus.OK);
    }



}
