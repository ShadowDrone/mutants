package ar.com.ada.api.mutant;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};							

		System.out.println("************");
		System.out.println("Testing Mutants ");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant));


		String[] dnaMutant2 = {"AAAAAA","CAGTAC","TTATGT","AGAAGG","CACCTA","TCACTG"};							

		System.out.println("************");
		System.out.println("Testing Mutants 2 secuences");
		System.out.println("************");
		assertTrue(mutantService.isMutant(dnaMutant2));

	}

	@Test
	void ADN_isHuman() {
		String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCTTA","TCACTG"};							

		System.out.println("************");
		System.out.println("Testing Human");
		System.out.println("************");

		assertFalse(mutantService.isMutant(dnaMutant));
	}
}
