package aed.spring.mtgrestapi.controller;

import aed.spring.mtgrestapi.model.Card;
import aed.spring.mtgrestapi.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardRepository repository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCards(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCardById(@PathVariable("id") String id){
        var cardDB = repository.findById(id);
        if(cardDB.isEmpty())
            return new ResponseEntity<>("no existe carta con ese id",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(cardDB.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCard(@RequestBody Card card){
        var cardDB = repository.findById(card.getUUID());
        if(cardDB.isPresent())
            return new ResponseEntity<>("ya existe esa carta",HttpStatus.CONFLICT);
        repository.save(card);
        return new ResponseEntity<>("agregada correctamente",HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> changeCardDetails(@PathVariable("id") String id,@RequestBody Card card){
        var cardDB = repository.findById(id);

        if(cardDB.isEmpty())
            return new ResponseEntity<>("esa carta no existe",HttpStatus.NOT_FOUND);
        var guardar = copiedDetails(cardDB.get(),card);
        repository.save(guardar);
        return new ResponseEntity<>("actualizada correctamente",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCardById(@PathVariable("id") String id){
        var cardDB = repository.findById(id);
        if(cardDB.isEmpty())
            return new ResponseEntity<>("esa carta no existe",HttpStatus.NOT_FOUND);
        repository.delete(cardDB.get());
        return  new ResponseEntity<>("eliminada correctamente",HttpStatus.OK);
    }

    private Card copiedDetails(Card inDeck, Card outDeck){
        if(outDeck.getCardName() != null)
            inDeck.setCardName(outDeck.getCardName());
        if(outDeck.getImg() != null)
            inDeck.setImg(outDeck.getImg());
        if(outDeck.getColor() != null)
            inDeck.setColor(outDeck.getColor());
        if(outDeck.getDecks() != null)
            inDeck.setDecks(outDeck.getDecks());
        return inDeck;
    }


}
