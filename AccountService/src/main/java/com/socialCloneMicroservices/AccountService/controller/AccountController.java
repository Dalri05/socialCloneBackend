package com.socialCloneMicroservices.AccountService.controller;

import com.socialCloneMicroservices.AccountService.Enums.ResponseEnum;
import com.socialCloneMicroservices.AccountService.model.AccountModel;
import com.socialCloneMicroservices.AccountService.service.AccountService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService service;

    @PostMapping("/conta/novaConta")
    public ResponseEntity criarConta(@RequestBody AccountModel conta){
        try {
            service.salvarConta(conta);
            return ResponseEntity.ok().body(conta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/conta/bloquearConta/{idConta}/{userBloquear}")
        public String bloquearConta(@PathVariable int idConta, @PathVariable String userBloquear){
        try {
            return service.bloquearConta(idConta, userBloquear);
        } catch (Exception e){
            e.printStackTrace();
            return "Erro ao bloquear conta";
        }
    }

    @GetMapping("/conta/listaContas")
    public ResponseEntity listarTodasContas(){
        try {
            List<AccountModel> allAccounts = service.getTodasContas();
            if(allAccounts.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(allAccounts);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/conta/deletarconta/{id}")
    public ResponseEntity deletarConta(@PathVariable int id){
        try{
            ResponseEnum response = service.deletarConta(id);
            if (response.getResponseCode() == 200) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
