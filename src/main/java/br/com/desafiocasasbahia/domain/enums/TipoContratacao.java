package br.com.desafiocasasbahia.domain.enums;

import br.com.desafiocasasbahia.strategy.TipoVendedorInterface;
import br.com.desafiocasasbahia.strategy.VendedorCLT;
import br.com.desafiocasasbahia.strategy.VendedorPJ;
import br.com.desafiocasasbahia.strategy.VendedorTerceirizado;

public enum TipoContratacao {
    
    OUTSOURCING {
        @Override
        public TipoVendedorInterface getVendedorStrategy() {
            return new VendedorTerceirizado();
        }
    },
    CLT {
        @Override
        public TipoVendedorInterface getVendedorStrategy() {
            return new VendedorCLT();
        }
    },
    PESSOA_JURIDICA {
        @Override
        public TipoVendedorInterface getVendedorStrategy() {
            return new VendedorPJ();
        }
    };

    public abstract TipoVendedorInterface getVendedorStrategy();
}
