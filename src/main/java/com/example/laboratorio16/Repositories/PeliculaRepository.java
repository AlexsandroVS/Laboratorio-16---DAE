package com.example.laboratorio16.Repositories;

import com.example.laboratorio16.Models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    Optional<Pelicula> findByTitulo(String titulo);
}