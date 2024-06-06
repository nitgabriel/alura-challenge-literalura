package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> buscarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosEmUmDeterminadoAno(int ano) {
        return autorRepository.findByAnoNascimentoLessThanAndAnoFalecimentoGreaterThan(ano, ano);
    }
}