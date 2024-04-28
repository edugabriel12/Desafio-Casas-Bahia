package br.com.desafiocasasbahia.strategy;

import br.com.desafiocasasbahia.exceptions.InvalidDocumentException;
import br.com.desafiocasasbahia.helpers.VendedorValidator;

public class VendedorPJ implements TipoVendedorInterface {
    @Override
    public void validaDocumento(String documento)  {
        if (!VendedorValidator.validaCNPJ(documento))
            throw new InvalidDocumentException("CNPJ Inválido!");
    }

    @Override
    public String getTipoVendedor() {
        return "PJ";
    }
}
