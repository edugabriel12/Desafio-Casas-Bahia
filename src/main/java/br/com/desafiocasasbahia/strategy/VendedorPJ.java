package br.com.desafiocasasbahia.strategy;

import br.com.desafiocasasbahia.helpers.VendedorValidator;

public class VendedorPJ implements TipoVendedorInterface {
    @Override
    public void validaDocumento(String documento) throws Exception {
        if (!VendedorValidator.validaCNPJ(documento)) throw new Exception("CNPJ Inválido!");
    }

    @Override
    public String getTipoVendedor() {
        return "PJ";
    }
}
