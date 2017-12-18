package com.batata.dualDb.controller;

import com.batata.dualDb.model.BatataDto;
import com.batata.dualDb.service.BatataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("batataDb")
public class BatataController {
    private static Logger logger = LoggerFactory.getLogger(BatataController.class);

    private BatataService batataService;

    @Autowired
    public BatataController(BatataService batataService) {
        this.batataService = batataService;
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
                      method = RequestMethod.GET)
    public ResponseEntity<List<BatataDto>> getAll(){
        logger.info("Get all Batatas");
        return ResponseEntity.ok(batataService.findAll());
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            method = RequestMethod.GET ,path = "/count")
    public ResponseEntity<Long> count(){
        logger.info("Get how many Batatas");
        return ResponseEntity.ok(batataService.count());
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
                      method = RequestMethod.GET,
                      path   = "/ids/{ids}" )
    public ResponseEntity<List<BatataDto>> getByIds(@PathVariable(value = "ids", required = true) List<Integer> ids) {
        logger.info("Get all Batatas by ids");
        return ResponseEntity.ok(batataService.findIds(ids));
    }

    @RequestMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
            method = RequestMethod.GET,
            path   = "/type/{type}" )
    public ResponseEntity<List<BatataDto>> getByType(@PathVariable(value = "type", required = true) String type) {
        logger.info("Get all Batatas by type");
        return ResponseEntity.ok(batataService.findType(type));
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
                      method = RequestMethod.GET,
                        path = "/{id}")
    public ResponseEntity<BatataDto> getById(@PathVariable(value = "id", required = true) Integer id) {
        logger.info("Get a batata by Id:" + id);
        BatataDto result = batataService.findOne(id);

        if(result != null){
            return ResponseEntity.ok(result);
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                                  .build();
        }
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
                 consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public BatataDto create(@Validated @RequestBody BatataDto batata) {

        return batataService.save(batata);
    }

    @PutMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },
                consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE},
                    path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BatataDto> update(@PathVariable(value = "id", required = true) Integer id,
                                            @Validated
                                            @RequestBody BatataDto batata) {

        BatataDto result =  batataService.update(id, batata);
        if(result != null){
            return ResponseEntity.ok(result);
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(path = "/{id}",
               produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable(value = "id", required = true) Integer id) {
        batataService.delete(id);
        return ResponseEntity.ok().build();
    }
}
