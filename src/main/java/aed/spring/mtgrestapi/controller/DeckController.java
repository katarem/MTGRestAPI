package aed.spring.mtgrestapi.controller;

import aed.spring.mtgrestapi.model.Card;
import aed.spring.mtgrestapi.model.Deck;
import aed.spring.mtgrestapi.repository.CardRepository;
import aed.spring.mtgrestapi.repository.DeckRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    private DeckRepository repository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeck(@PathVariable("id") Integer id){
        var deckFromDB = repository.findById(id);
        if(deckFromDB.isEmpty())
            return new ResponseEntity<>("LOS CAMPOS DEBEN ESTAR COMPLETOS", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(deckFromDB, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadDeck(@RequestBody Deck deck){
        var exists = repository.existsDeckByDeckNameEqualsAndUser_Id(deck.getDeckName(),deck.getUser().getId());
        if(exists) return new ResponseEntity<>("Ya existe ese deck con usuario",HttpStatus.CONFLICT);
        repository.save(deck);
        return new ResponseEntity<>("insertada con éxito",HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<?> getDecksByUserId(@PathVariable("id") Integer id){
        var decks = repository.findByUser_Id(id);
        if(decks.isEmpty()) return new ResponseEntity<>("No hay decks para ese usuario o no existe",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(decks,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDecks(@PathVariable(name = "color", required = false) String color,
                                         @PathVariable(value = "userId", required = false) Integer userId){
        if(color != null && userId != null)
            return new ResponseEntity<>(repository.findDecksByColorContainsAndUser_Id(color,userId),HttpStatus.OK);
        else if(color != null){
            var listaDecks = repository.findDecksByColorContains(color);
            if(listaDecks.isEmpty()) return new ResponseEntity<>("No se encontraron decks con ese color",HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(listaDecks, HttpStatus.OK);
        } else if (userId != null) {
            var listaDecks = repository.findByUser_Id(userId);
            if(listaDecks.isEmpty()) return new ResponseEntity<>("No se encuentran decks con ese user", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(listaDecks, HttpStatus.OK);
        }
        return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDeck(@PathVariable("id") Integer id){
        var deckDB = repository.findById(id);
        if(deckDB.isEmpty())
            return new ResponseEntity<>("La entidad no existía",HttpStatus.NOT_FOUND);
        repository.delete(deckDB.get());
        return new ResponseEntity<>(HttpEntity.EMPTY,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeDeckDetails(@PathVariable("id") Integer id,
                                               @RequestBody Deck deck){
        var deckDB = repository.findById(id);
        if(deckDB.isEmpty())
            return new ResponseEntity<>("no existe ese deck",HttpStatus.NOT_FOUND);
        var guardar = copiedDetails(deckDB.get(),deck);
        repository.save(guardar);
        return new ResponseEntity<>(deckDB.get(),HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity<?> addCardsToDeck(@PathVariable("id") Integer id,
                                           @RequestBody List<Card> cards){

        var deckDB = repository.findById(id);
        if(deckDB.isEmpty())
            return new ResponseEntity<>("no existe ese deck",HttpStatus.NOT_FOUND);

        cards.forEach(card -> {
            var cardDB = cardRepository.findByCardNameEqualsIgnoreCase(card.getCardName());
            if(cardDB != null)
                deckDB.get().getCards().add(cardDB);
        });
        repository.save(deckDB.get());
        return new ResponseEntity<>("cartas agregadas con éxito",HttpStatus.OK);
    }


    private Deck copiedDetails(Deck inDeck, Deck outDeck){
        if(outDeck.getDeckName() != null)
            inDeck.setDeckName(outDeck.getDeckName());
        if(outDeck.getCards() != null)
            inDeck.setCards(outDeck.getCards());
        if(outDeck.getColor() != null)
            inDeck.setColor(outDeck.getColor());
        if(outDeck.getUser() != null)
            inDeck.setUser(outDeck.getUser());
        return inDeck;
    }


}
