package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    public Map<Integer, Kangaroo> kangaroos;


    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
        kangaroos.put(111, new Kangaroo(111,"kangaroo1",2d,80d, "MALE", true));
        kangaroos.put(222, new Kangaroo(222,"kangaroo2",1.3d,50d, "FEMALE", false));
        kangaroos.put(333, new Kangaroo(333,"kangaroo3",1.7d,70d, "FEMALE", true));
    }

    /*

    [POST]/workintech/kangaroos => Bir adet kangaroo objesini kangaroos listesine ekler
    [PUT]/workintech/kangaroos/{id} => İlgili id deki kangaroo objesinin değerlerini yeni gelen data ile değiştirir.
    [DELETE]/workintech/developers/{id} => İlgili id değerindeki kangaroo objesini listeden siler.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Kangaroo> getAllKangaroos(){
        if(kangaroos.isEmpty()){
            log.error("No data found in the requested dataset. Please add data and try the request again.");
            throw new ZooException("No data found in the requested dataset. Please add data and try the request again.", HttpStatus.NOT_FOUND);
        }
        return kangaroos.values().stream().toList();
    }

    // Error Mekanizması eklenecek
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Kangaroo getById(@PathVariable int id){
        if(id<=0){
            log.error("Invalid parameter. ID ");

            throw new ZooException("Invalid parameter. ID : " + id , HttpStatus.BAD_REQUEST);
        }
        if(!kangaroos.containsKey(id)){
            log.error("Data with the provided ID not found");

            throw new ZooException("Data with the provided ID: ("+ id +") not found", HttpStatus.NOT_FOUND);
        }

        return kangaroos.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Kangaroo createKangaroo(@RequestBody  Kangaroo kangaroo){

        if(kangaroos.containsValue(kangaroo)){
            log.error("Conflict : kangaroo already exists.");

           throw  new ZooException("Conflict : " + kangaroo.getName() +" already exists.", HttpStatus.CONFLICT);
        }
        if(kangaroo.getHeight() == null || kangaroo.getWeight() == null || kangaroo.getName() == null || kangaroo.getIsAggressive() == null || kangaroo.getGender() == null){
            log.error("Fields cannot be null.");
            throw new ZooException("Fields cannot be null.", HttpStatus.BAD_REQUEST);
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Kangaroo updateById(@PathVariable int id, @RequestBody Kangaroo kangaroo){

        if(kangaroos.containsKey(id)){
            if(kangaroo.getId() != id){
                log.error("ID mismatch: The ID provided in the URL  does not match the ID in the request body. Please ensure both IDs are identical and try again.");

                throw  new ZooException("ID mismatch: The ID provided in the URL ( " + id + " ) does not match the ID in the request body ( " + kangaroo.getId() + " ). Please ensure both IDs are identical and try again.", HttpStatus.BAD_REQUEST);
            }
            kangaroos.replace(id, kangaroo);
            return kangaroos.get(id);


        }
        else{
            // Mantıksal olarak sağlıyor.Ama denenecek.
            log.error("Invalid id! Please enter valid Id..");
            throw  new ZooException("Invalid id! Please enter valid Id..", HttpStatus.BAD_REQUEST);

        }


    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Kangaroo deleteById(@PathVariable int id){
        //Kontrol Mekanizması çalışması lazım.
        if(kangaroos.containsKey(id)){
            Kangaroo kangaroo = new Kangaroo(kangaroos.get(id));
            kangaroos.remove(id);
            log.info("Deleted the Object");
            return kangaroo;
        }
        else{
            log.error("No data found for the provided ID. Please check if the ID is correct.");
            throw new ZooException( "No data found for the provided ID : " + id + " . Please check if the ID is correct.", HttpStatus.NOT_FOUND);
            //
        }
    }
}
