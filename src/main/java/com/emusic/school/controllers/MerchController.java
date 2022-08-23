package com.emusic.school.controllers;

import com.emusic.school.dtos.EditMerchDTO;
import com.emusic.school.dtos.MerchDTO;
import com.emusic.school.dtos.NewMerchDTO;
import com.emusic.school.models.Merch;
import com.emusic.school.models.MerchWaist;
import com.emusic.school.repositories.MerchRepository;
import com.emusic.school.services.MerchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MerchController {

    @Autowired
    private MerchService merchService;
    @GetMapping("/merch")
    public List<MerchDTO> getMerch(){
        return merchService.getMerch();
    }

    @PostMapping("/create/merch")
    public ResponseEntity<Object> createMerch(@RequestBody NewMerchDTO newMerchDTO){

        if(newMerchDTO.getStock() <=0 || newMerchDTO.getPrice() <=0 || newMerchDTO.getType().isEmpty() || newMerchDTO.getWaist().name().isEmpty()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }
        Merch merch = new Merch(newMerchDTO.getStock(),newMerchDTO.getType(),newMerchDTO.getPrice(),newMerchDTO.getWaist(),true, newMerchDTO.getUrlImage());
        merchService.saveMerch(merch);
        return new ResponseEntity<>("Created",HttpStatus.CREATED);
    }

    @PatchMapping("/delete/merch")
    public ResponseEntity<Object> deleteMerch(@RequestParam long id){
        Merch merch = merchService.findByID(id);
        if(merch == null){
            return new ResponseEntity<>("Missing Data.",HttpStatus.FORBIDDEN);
        }
        merch.setActive(false);
        merchService.saveMerch(merch);
        return new ResponseEntity<>("Successfully deleted.",HttpStatus.ACCEPTED);
    }

    @PatchMapping("/edit/merch")
    public ResponseEntity<Object> editMerch(@RequestBody EditMerchDTO editMerchDTO){

        Merch merchToEdit = merchService.findByID(editMerchDTO.getId());

        if(editMerchDTO.getStock() <0 || editMerchDTO.getPrice() <=0 || editMerchDTO.getType().isEmpty()){
            return new ResponseEntity<>("Missing Data.", HttpStatus.FORBIDDEN);
        }

        if (merchToEdit.getStock() == editMerchDTO.getStock() && merchToEdit.getType().equals(editMerchDTO.getType())
                && merchToEdit.getPrice() == editMerchDTO.getPrice() && merchToEdit.getWaist().equals(editMerchDTO.getWaist())){
            return new ResponseEntity<>("Nothing to edit.",HttpStatus.FORBIDDEN);
        }

        merchToEdit.setStock(editMerchDTO.getStock());
        merchToEdit.setType(editMerchDTO.getType());
        merchToEdit.setPrice(editMerchDTO.getPrice());
        merchToEdit.setWaist(editMerchDTO.getWaist());
        merchService.saveMerch(merchToEdit);
        return new ResponseEntity<>("Changes applied successfully.", HttpStatus.ACCEPTED);
    }
}
