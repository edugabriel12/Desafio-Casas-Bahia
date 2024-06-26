package br.com.desafiocasasbahia.proxy;

import br.com.desafiocasasbahia.response.feign.FilialResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "desafio-casas-bahia-filial", url = "localhost:8081")
public interface FilialProxy {

    @GetMapping("/filiais/{cnpj}")
    ResponseEntity<FilialResponse> getFilial(@PathVariable(name = "cnpj") String cnpj);
}
