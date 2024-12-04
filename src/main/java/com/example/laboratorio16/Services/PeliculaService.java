package com.example.laboratorio16.Services;

import com.example.laboratorio16.Models.Pelicula;
import com.example.laboratorio16.Repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private static final Logger logger = LoggerFactory.getLogger(PeliculaService.class);
    private final PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public Pelicula savePelicula(Pelicula pelicula) {
        logger.info("Iniciando la creación de la película con título: {}", pelicula.getTitulo());

        Optional<Pelicula> existingPelicula = peliculaRepository.findByTitulo(pelicula.getTitulo());
        if (existingPelicula.isPresent()) {
            logger.error("Error al crear película: ya existe una película con el título '{}'", pelicula.getTitulo());
            throw new IllegalArgumentException("Ya existe una película con el mismo título");
        }

        Pelicula savedPelicula = peliculaRepository.save(pelicula);
        logger.info("Película creada con éxito: {}", savedPelicula.getTitulo());
        return savedPelicula;
    }

    public List<Pelicula> findAll() {
        logger.info("Obteniendo todas las películas");
        List<Pelicula> peliculas = peliculaRepository.findAll();
        logger.info("Se han recuperado {} películas", peliculas.size());
        return peliculas;
    }

    public Pelicula findById(Long id) {
        logger.info("Buscando película con ID: {}", id);
        Optional<Pelicula> pelicula = peliculaRepository.findById(id);

        if (pelicula.isPresent()) {
            logger.info("Película encontrada: {}", pelicula.get().getTitulo());
            return pelicula.get();
        } else {
            logger.warn("No se encontró la película con ID: {}", id);
            return null;
        }
    }

    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        logger.info("Iniciando actualización de la película con ID: {}", id);
        Pelicula existingPelicula = findById(id);

        if (existingPelicula == null) {
            logger.warn("No se encontró la película con ID: {}", id);
            return null;
        }

        pelicula.setId(id);
        Pelicula updatedPelicula = peliculaRepository.save(pelicula);
        logger.info("Película con ID: {} actualizada con éxito", id);
        return updatedPelicula;
    }

    public boolean deletePelicula(Long id) {
        logger.info("Iniciando eliminación de película con ID: {}", id);
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
            logger.info("Película con ID: {} eliminada con éxito", id);
            return true;
        } else {
            logger.warn("No se pudo eliminar: No existe una película con ID: {}", id);
            return false;
        }
    }
}
