package ar.com.ada.api.mutant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.services.MutantService;

@SpringBootTest
class MutantApplicationTests {

	@Autowired
	MutantService mutantService;

	@Test
	void contextLoads() {
	}

	@Test
	void ADN_isValid() {

	}

	@Test
	void ADN_isMutant() {
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Mutants ");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant));

		String[] dnaMutant2 = { "AAAAAA", "CAGTAC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Mutants 2 secuences diagonal");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant2));

	}

	@Test
	void ADN_isHuman() {
		String[] dnaMutant = { "ATGCGA", "CTGTGC", "TTATGT", "AGAATG", "CCCTTA", "TCACTG" };

		System.out.println("************");
		System.out.println("Testing Human");
		System.out.println("************");

		assertFalse(mutantService.isMutant(dnaMutant));
	}

	@Test
	void MatrixTest() {
		String[] dnaMutant = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCTTA", "TCACTG" };
		DNASample sample = new DNASample(dnaMutant);
		char[][] matrix = sample.toMatrix();
		char[][] invertedMatrix = sample.toMatrixInverted();
		String[] dnaInverted = new String[dnaMutant.length];

		for (int i = 0; i < invertedMatrix.length; i++) {
			StringBuilder sb = new StringBuilder();

			for (char caracter : invertedMatrix[i]) {

				sb.append(caracter);
			}
			dnaInverted[i] = sb.toString();
		}
		DNASample sampleInverted = new DNASample(dnaInverted);
		char[][] restoredMatrix = sampleInverted.toMatrixInverted();
		boolean iguales = matrix.length > 0;
		for (int i = 0; i < matrix.length; i++) {

			for (int j = 0; j < matrix.length; j++) {
				iguales = iguales && matrix[i][j] == restoredMatrix[i][j];
			}
		}
		System.out.println("************");
		System.out.println("Testing Matrix");
		System.out.println("************");

		assertTrue(iguales, "las matrices no son iguales :(");
	}

}
