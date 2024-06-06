package com.alura.literalura.principal;

import com.alura.literalura.model.Livro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal implements CommandLineRunner {

    private LivroService livroService;
    private AutorService autorService;
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    public Principal(LivroService livroService, AutorService autorService) {

        this.livroService = livroService;
        this.autorService = autorService;
    }

    @Override
    public void run(String... args) throws Exception {

        int opcao = -1;
        while(opcao != 0) {
            System.out.println("Escolha o número de sua opção:");
            System.out.println("1- buscar livro pelo título");
            System.out.println("2- listar livros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos em um determinado ano");
            System.out.println("5- listar livros em um determinado idioma");
            System.out.println("0 - sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPeloTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosEmUmAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> {
                    return;
                }

            }
        }

    }

    public void buscarLivroPeloTitulo() {
        System.out.println("Insira o nome do livro que você deseja procurar: ");
        String titulo = scanner.nextLine();
        List<Livro> livros = livroService.buscarLivroPeloTitulo(titulo);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título: " + titulo);
        } else {
            for (Livro livro : livros) {
                System.out.println("\n----- LIVRO -----");
                System.out.println("Titulo: " + livro.getTitulo());
                livro.getAutores().forEach(a -> System.out.println("Autores: " + a.getNome()));
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Número de downloads: " + livro.getNumeroDownloads());
                System.out.println("-----------------\n");
            }
        }
    }

    public void listarLivros() {
        livroService.listarLivrosRegistrados().forEach(l -> {
            System.out.println("\n----- LIVRO -----");
            System.out.println("Titulo: " + l.getTitulo());
            l.getAutores().forEach(a -> System.out.println("Autores: " + a.getNome()));
            System.out.println("Idioma: " + l.getIdioma());
            System.out.println("Número de downloads: " + l.getNumeroDownloads());
            System.out.println("-----------------\n");
        });
    }

    public void listarAutores() {
        autorService.buscarAutores().forEach(a -> {
            System.out.println("\nAutor: " + a.getNome());
            System.out.println("Ano de nascimento: " + a.getAnoNascimento());
            System.out.println("Ano de falecimento: " + a.getAnoFalecimento());
            System.out.println("Livros: " + a.getLivros().stream()
                    .map(l -> String.format("[%s]", l.getTitulo()))
                    .collect(Collectors.joining(" "))
            );
            System.out.println("-----------------\n");
        });
    }

    public void listarAutoresVivosEmUmAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        autorService.buscarAutoresVivosEmUmDeterminadoAno(ano).forEach(a -> {
            System.out.println("\nAutor: " + a.getNome());
            System.out.println("Ano de nascimento: " + a.getAnoNascimento());
            System.out.println("Ano de falecimento: " + a.getAnoFalecimento());
            System.out.println("Livros: " + a.getLivros().stream()
                    .map(l -> String.format("[%s]", l.getTitulo()))
                    .collect(Collectors.joining(" "))
            );
            System.out.println("-----------------\n");
        });
    }

    public void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca: ");
        System.out.println("""
                es- espanhol
                en- ingles
                fr- francês
                pt- português
                """);
        String linguagem = scanner.nextLine();
        livroService.listarLivrosEmUmDeterminadoIdioma(linguagem).forEach(l -> {
            System.out.println("\n----- LIVRO -----");
            System.out.println("Titulo: " + l.getTitulo());
            l.getAutores().forEach(a -> System.out.println("Autores: " + a.getNome()));
            System.out.println("Idioma: " + l.getIdioma());
            System.out.println("Número de downloads: " + l.getNumeroDownloads());
            System.out.println("-----------------\n");
        });

    }

}