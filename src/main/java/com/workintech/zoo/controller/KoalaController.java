package com.workintech.zoo.controller;


import com.workintech.zoo.entity.Gender;
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
@RequestMapping("/koalas")
public class KoalaController {
    public Map<Integer, Koala> koalas;


    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
        koalas.put(1, new Koala(1,"koala1",80d, 12.2d, "MALE"));
        koalas.put(2, new Koala(2,"koala2",70d, 11.2d, "FEMALE"));
        koalas.put(3, new Koala(3,"koala3",60d, 14d, "FEMALE"));
    }

    /*

    [POST]/workintech/koalas => Bir adet Koala objesini koalas listesine ekler
    [PUT]/workintech/koalas/{id} => İlgili id deki Koala objesinin değerlerini yeni gelen data ile değiştirir.
    [DELETE]/workintech/developers/{id} => İlgili id değerindeki Koala objesini listeden siler.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Koala> getAllKoalas(){
        if(koalas.isEmpty()){
            throw new ZooException("No data found in the requested dataset. Please add data and try the request again.", HttpStatus.NOT_FOUND);
        }
        return koalas.values().stream().toList();
    }

    // Error Mekanizması eklenecek
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Koala getById(@PathVariable int id){
        if(id<=0){
            log.error("Invalid parameter. ID : " + id);

            throw new ZooException("Invalid parameter. ID : " + id, HttpStatus.BAD_REQUEST );
        }
        if(!koalas.containsKey(id)){
            log.error("Data with the provided ID: ("+ id +") not found");

            throw new ZooException("Data with the provided ID: ("+ id +") not found", HttpStatus.NOT_FOUND);
        }

        return koalas.get(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Koala createKoala(@RequestBody  Koala koala){
        if(koalas.containsValue(koala)){
            log.error("Conflict : " + koala.getName() +" already exists.");

            throw  new ZooException( "Conflict : " + koala.getName() +" already exists.", HttpStatus.CONFLICT);
        }

        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());

    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Koala updateById(@PathVariable int id, @RequestBody Koala koala){
        if(koalas.containsKey(id)){
            if(koala.getId() != id){
                log.error("ID mismatch: The ID provided in the URL ( " + id + " ) does not match the ID in the request body ( " + koala.getId() + " ). Please ensure both IDs are identical and try again.");

                throw  new ZooException("ID mismatch: The ID provided in the URL ( " + id + " ) does not match the ID in the request body ( " + koala.getId() + " ). Please ensure both IDs are identical and try again.", HttpStatus.BAD_REQUEST);
            }
            koalas.replace(id, koala);
            return koalas.get(id);


        }
        else{
            // Mantıksal olarak sağlıyor.Ama denenecek.
            return getById(id);
        }
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Koala> deleteById(@PathVariable int id){
        //Kontrol Mekanizması çalışması lazım.
        if(koalas.containsKey(id)){
            koalas.remove(id);
            return getAllKoalas();
        }
        else{
            log.error("No data found for the provided ID : "+ id +". Please check if the ID is correct.");

            throw new ZooException( "No data found for the provided ID : "+ id +". Please check if the ID is correct.", HttpStatus.NOT_FOUND);
        }
    }
}
