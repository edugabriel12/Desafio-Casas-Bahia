package br.com.desafiocasasbahia.services;

import br.com.desafiocasasbahia.domain.vendedor.Vendedor;
import br.com.desafiocasasbahia.helpers.VendedorValidator;
import br.com.desafiocasasbahia.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class VendedorService {

    private final VendedorRepository repository;

    @Autowired
    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }

    public List<Vendedor> getVendedores() {
        return repository.findAll();
    }

    public void validarVendedor()

    public LocalDate formataData(String data) throws Exception {
        if (!VendedorValidator.validaData(data)) throw new Exception("Data inválida");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(data, formato);
    }

    private String geraMatriculaSequencial(String tipoContratacao) {
        Random random = new Random();
        StringBuilder sequencial = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digito = random.nextInt(10);
            sequencial.append(digito);
        }

        return sequencial + "-" + tipoContratacao;
    }
}
