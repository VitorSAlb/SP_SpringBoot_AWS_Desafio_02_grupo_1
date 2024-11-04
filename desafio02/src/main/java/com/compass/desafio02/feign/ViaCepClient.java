package com.compass.desafio02.feign;

import com.compass.desafio02.web.dto.feign.CepDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    CepDto getAddressByCep(@PathVariable("cep") String cep);
}
