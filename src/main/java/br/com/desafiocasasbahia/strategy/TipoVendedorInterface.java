package br.com.desafiocasasbahia.strategy;

import org.springframework.stereotype.Component;

public interface TipoVendedorInterface {

    void validaDocumento(String documento) ;

    String getTipoVendedor();
}
