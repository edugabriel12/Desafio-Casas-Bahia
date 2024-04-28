package br.com.desafiocasasbahia.controllers;

import br.com.desafiocasasbahia.domain.dtos.VendedorDTO;
import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.response.VendedorAtualizadoResponse;
import br.com.desafiocasasbahia.services.VendedorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    private final VendedorService service;

    @Autowired
    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Vendedor>> getVendedores(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ){


        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(service.getVendedores(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedor(@PathVariable(name = "id") Long id)  {
        Vendedor vendedor = service.findVendedorById(id);

        return ResponseEntity.ok(vendedor);
    }

    @PostMapping
    public ResponseEntity<Vendedor> cadastraVendedor(@RequestBody @Valid VendedorDTO vendedorRequest)  {
        service.checaSeVendedorExiste(vendedorRequest.documento(), vendedorRequest.email());
        service.validaDocumentoVendedor(vendedorRequest.documento(), vendedorRequest.tipoContratacao());
        service.cadastraVendedor(vendedorRequest);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<VendedorAtualizadoResponse> atualizaVendedor(@PathVariable(name = "id") Long id
            , @RequestBody @Valid VendedorDTO vendedorRequest)  {

        VendedorAtualizadoResponse response = service.atualizaVendedor(id, vendedorRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Vendedor> removeVendedor(@PathVariable(name = "id") Long id)  {
        Vendedor vendedor = service.removeVendedor(id);

        return ResponseEntity.ok(vendedor);
    }
}
