package br.com.desafiocasasbahia.services;

import br.com.desafiocasasbahia.helpers.VendedorValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class VendedorService {





    private LocalDate formataData(String data) throws Exception {
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
