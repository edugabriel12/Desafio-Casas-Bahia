package br.com.desafiocasasbahia.controllers;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.response.VendedorAtualizadoResponse;
import br.com.desafiocasasbahia.services.VendedorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Vendedor> cadastraVendedor(@RequestBody @Valid VendedorDTO vendedorRequest) throws Exception {
        service.checaSeVendedorExiste(vendedorRequest.documento(), vendedorRequest.email());
        service.validaDocumentoVendedor(vendedorRequest.documento(), vendedorRequest.tipoContratacao());
        service.cadastraVendedor(vendedorRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<VendedorAtualizadoResponse> atualizaVendedor(@PathVariable(name = "id") Long id
            , @RequestBody @Valid VendedorDTO vendedorRequest) throws Exception {

        VendedorAtualizadoResponse response = service.atualizaVendedor(id, vendedorRequest);
        return ResponseEntity.ok(response);
    }
}
