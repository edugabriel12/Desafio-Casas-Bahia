package br.com.desafiocasasbahia.controllers;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    private final VendedorService service;

    @Autowired
    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> getVendedores(){
        List<Vendedor> vendedores = service.getVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @PostMapping
    public ResponseEntity<Vendedor> cadastraVendedor(@RequestBody VendedorDTO vendedorRequest) throws Exception {
        service.checaSeVendedorExiste(vendedorRequest.documento(), vendedorRequest.email());
        service.validaDocumentoVendedor(vendedorRequest.documento(), vendedorRequest.tipoContratacao());
        service.cadastraVendedor(vendedorRequest);

        return ResponseEntity.ok().build();
    }
}
