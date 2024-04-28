package br.com.desafiocasasbahia.strategy;

import br.com.desafiocasasbahia.exceptions.InvalidDocumentException;
import br.com.desafiocasasbahia.helpers.VendedorValidator;

public class VendedorCLT implements TipoVendedorInterface {
    @Override
    public void validaDocumento(String documento)  {
        if (!VendedorValidator.validaCPF(documento))
            throw new InvalidDocumentException("CPF Inválido!");
    }

    @Override
    public String getTipoVendedor() {
        return "CLT";
    }
}
