package ar.com.ada.api.mutant.services;

import org.springframework.stereotype.Service;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.utils.MatrixDNAIterator;

@Service
public class MutantService {

    public boolean isMutant(String[] dna){
        
   
        MatrixDNAIterator iterator = new MatrixDNAIterator();

        DNASample sample = new DNASample(dna);

        int matches = 0;
        int matchesByRows = 0;
        int matchesByColumns = 0;
        int matchesByDiagonals = 0;

        matchesByRows = iterator.matchesByRows(sample);
        System.out.println("Matcheos por Rows "+ matchesByRows);
        matchesByColumns = iterator.matchesByColumns(sample);
        System.out.println("Matcheos por Columns "+ matchesByColumns);
        matchesByDiagonals = iterator.matchesByDiagonals(sample);
        System.out.println("Matcheos por Diagonals "+ matchesByDiagonals);

        matches = matchesByRows + matchesByColumns + matchesByDiagonals;

        return matches > 1;

    }
}
