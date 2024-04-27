package br.com.desafiocasasbahia.strategy;

import br.com.desafiocasasbahia.helpers.VendedorValidator;

public class VendedorCLT implements TipoVendedorInterface {
    @Override
    public void validaDocumento(String documento) throws Exception {
        if (!VendedorValidator.validaCPF(documento)) throw new Exception("CPF Inválido!");
    }

    @Override
    public String getTipoVendedor() {
        return "CLT";
    }
}
