package ar.com.ada.api.mutant.services;

import java.util.Date;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.entities.*;
import ar.com.ada.api.mutant.repos.HumanRepository;
import ar.com.ada.api.mutant.repos.MutantRepository;
import ar.com.ada.api.mutant.utils.MatrixDNAIterator;
import ar.com.ada.api.mutant.utils.StringUtils;

@Service
public class MutantService {

    @Autowired
    MutantRepository mutantRepo;
    @Autowired
    HumanRepository humanRepo;

    public void create(Mutant mutant) {

        this.mutantRepo.save(mutant);
    }

    public void create(Human human) {

        this.humanRepo.save(human);
    }

    public boolean exists(String[] dna) {

        DNASample sample = new DNASample(dna);
        String uniqueHash = sample.uniqueHash();

        if (mutantRepo.findByUniqueHash(uniqueHash) != null)
            return true;

        if (humanRepo.findByUniqueHash(uniqueHash) != null)
            return true;

        return false;

    }

    public Mutant registerSample(String[] dna, String name) {

        DNASample sample = new DNASample(dna);

        if (this.isMutant(dna)) {
            Mutant mutant = new Mutant();
            //Solo para mutantes lo encripto
            mutant.setDna(sample.encrypt());
            mutant.setUniqueHash(sample.uniqueHash());
            mutant.setName(name);
            this.create(mutant);
            return mutant;
        } else {
            Human human = new Human();
            human.setDna(dna);
            human.setUniqueHash(sample.uniqueHash());
            this.create(human);
            return null;
        }
    }

    public boolean isValid(String[] dna) {

        DNASample sample = new DNASample(dna);
        return sample.isValid();
    }

    public boolean isMutant(String[] dna) {

        MatrixDNAIterator iterator = new MatrixDNAIterator();

        DNASample sample = new DNASample(dna);

        System.out.println(sample.toString());

        int matches = 0;
        int matchesByRows = 0;
        int matchesByColumns = 0;
        int matchesByDiagonals = 0;

        matchesByRows = iterator.matchesByRows(sample);
        System.out.println("Matcheos por Rows " + matchesByRows);
        matchesByColumns = iterator.matchesByColumns(sample);
        System.out.println("Matcheos por Columns " + matchesByColumns);
        matchesByDiagonals = iterator.matchesByDiagonals(sample);
        System.out.println("Matcheos por Diagonals " + matchesByDiagonals);

        matches = matchesByRows + matchesByColumns + matchesByDiagonals;

        return matches > 1;

    }

    public long countMutants() {

        return mutantRepo.count();

    }

    public long countHumans() {

        return humanRepo.count();

    }

    public Long countAll() {
        System.out.println("Count ALL, Thread : " + Thread.currentThread().getId());
        return this.countMutants() + this.countHumans();

    }

    @Async
    public Future<Long> countMutantsAsync() {

        long resultado = mutantRepo.count();
        return new AsyncResult<Long>(resultado);

    }

    @Async
    public void imprimirComoVamos() {
        System.out.println("Creo que vamos bien...Empezando " + Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Creo que vamos bien...Finalizado " + Thread.currentThread().getId());
    }

    public Mutant firstMutant() {
        Mutant m = mutantRepo.findAll().get(0);

        return m;
    }

    /**
     * Valida que el nombre sea solo letras y espacios
     * @param name
     * @return
     */
    public boolean nameIsValid(String name) {

        
        //Con regex
        // [] => significan 1 caracter
        // a-z => significa desde 'a' a 'z'
        // A-Z => igual qeu antes en mayuscula
        // ñ => 'ñ'
        // Ñ => 'Ñ'
        // + => al menos un token de lo que esta a la izquierda (1 a N)
        // \s => es un espacio
        // *  => todo el token sobre la izquierda de 0 a N
        // $ => fin del caracter
        // ? => uno o ninguno
        // \p{L} => permite cualquier cosa que se pueda representar como letra en el mapa Unicode
        // \p{N} => permite cualquier cosa que sea representacion numerica
        String regex = "^[a-zA-ZñÑáíéóú]\'?[a-zA-ZñÑáíéóú]*(\s[a-zA-ZñÑáíéóú]+)*$";
        //Este para super internacional
        //String regex = "^\\p{L}\'?\\p{L}*(\s\\p{L}+)*$";

        return StringUtils.isMatch(regex, name);

    }


    /**
     * Valida que el nombre sea solo letras y espacios
     * @param name
     * @return
     */
    private boolean nameIsValidOldSchool(String name) {

        if (name == "" || name == null)
            return false;

        for (char letter : name.toCharArray()) {

            if (!((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z') || letter == ' '
            || letter == 'ñ' || letter == 'Ñ')
                )
                return false;

        }

        return true;
    }
}