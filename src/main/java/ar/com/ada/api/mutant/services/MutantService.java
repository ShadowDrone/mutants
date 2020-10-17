package ar.com.ada.api.mutant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.entities.*;
import ar.com.ada.api.mutant.repos.HumanRepository;
import ar.com.ada.api.mutant.repos.MutantRepository;
import ar.com.ada.api.mutant.utils.MatrixDNAIterator;

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

    public Mutant registerSample(String[] dna) {

        if (this.isMutant(dna)) {
            Mutant mutant = new Mutant();
            mutant.setDna(dna);
            this.create(mutant);
            return mutant;
        } else {
            Human human = new Human();
            human.setDna(dna);
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
}
