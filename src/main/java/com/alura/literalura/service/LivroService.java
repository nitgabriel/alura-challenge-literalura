package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private ConsumoAPI consumoAPI;
    private ConverteDados converteDados;

    @Autowired
    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, ConsumoAPI consumoAPI, ConverteDados converteDados) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
        this.converteDados = converteDados;
    }

    public List<Livro> listarLivrosRegistrados() {
        return livroRepository.findAll();
    }

    public List<Livro> listarLivrosEmUmDeterminadoIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    public List<Livro> buscarLivroPeloTitulo(String titulo) {
        titulo = titulo.replace(" ", "+");
        var json = consumoAPI.obterDados("https://gutendex.com/books/?search=" + titulo);
        ResultadoDTO resultadoDTO = converteDados.obterDados(json, ResultadoDTO.class);
        List<LivroDTO> livrosDTO = resultadoDTO.results();

        List<Livro> livros = new ArrayList<>();
        for (LivroDTO livroDTO : livrosDTO) {
            Livro livro = new Livro();
            livro.setTitulo(livroDTO.title());
            livro.setIdioma(livroDTO.languages().get(0)); // Ajustado para pegar o primeiro idioma da lista
            livro.setNumeroDownloads(livroDTO.downloadCount());
            List<Autor> autores = new ArrayList<>();
            for (AutorDTO autorDTO : livroDTO.authors()) {
                Autor autor = new Autor();
                autor.setNome(autorDTO.name());
                autor.setAnoNascimento(autorDTO.birthYear());
                autor.setAnoFalecimento(autorDTO.deathYear());
                autores.add(autor);

                // salva os autores no banco de dados.
                autorRepository.save(autor);
            }
            livro.setAutores(autores);

            livroRepository.save(livro);
            livros.add(livro);
        }
        return livros;
    }
}