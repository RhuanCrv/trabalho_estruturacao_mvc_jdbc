package br.edu.faculdade.service;

import br.edu.faculdade.model.Animal;
import br.edu.faculdade.repository.AnimalRepository;
import br.edu.faculdade.repository.TutorRepository;
import java.util.List;

public class AnimalService {
    private final AnimalRepository animalRepository = new AnimalRepository();
    private final TutorRepository tutorRepository = new TutorRepository();

    public Animal cadastrar(Animal animal) {
        if (tutorRepository.findById(animal.getIdTutor()).isEmpty()) {
            throw new IllegalArgumentException("Tutor inexistente. Cadastre o tutor antes do animal.");
        }
        return animalRepository.save(animal);
    }

    public List<Animal> buscarPorTutor(int idTutor) {
        return animalRepository.findByTutorId(idTutor);
    }
}