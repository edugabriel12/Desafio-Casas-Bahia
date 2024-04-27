package br.com.desafiocasasbahia.responseFeign;


public record FilialResponse(String nome, String documento, String cidade,
                             String UF, String tipo,
                             String dataCadastro, String ultimaAtualizacao) {
}
