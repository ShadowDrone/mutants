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

		assertTrue(mutantService.isMutant(dnaMutant));

	}

	@Test
	void ADN_isHuman() {
		String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};							

		assertFalse(mutantService.isMutant(dnaMutant));
	}
}
