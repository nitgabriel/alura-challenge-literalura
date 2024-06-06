package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContaining(String nome);
    List<Autor> findByAnoNascimentoLessThanAndAnoFalecimentoGreaterThan(int anoNascimento, int anoFalecimento);
}
