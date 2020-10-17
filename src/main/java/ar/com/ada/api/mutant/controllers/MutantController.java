package ar.com.ada.api.mutant.controllers;

import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.mutant.entities.DNASample;
import ar.com.ada.api.mutant.entities.Mutant;
import ar.com.ada.api.mutant.models.request.SampleRequest;
import ar.com.ada.api.mutant.models.response.GenericResponse;
import ar.com.ada.api.mutant.models.response.StatsResponse;
import ar.com.ada.api.mutant.services.MutantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MutantController {

    @Autowired
    MutantService mutantService;

    @PostMapping("/mutant")
    public ResponseEntity<?> createMutant(@RequestBody SampleRequest req) {

        GenericResponse gr = new GenericResponse();
        if (!this.mutantService.isValid(req.dna)) {
            gr.isOk = false;
            gr.message = "Invalid format";
            return ResponseEntity.badRequest().body(gr);
        }

        if (this.mutantService.exists(req.dna)) {
            gr.isOk = false;
            gr.message = "Already registered";
            return ResponseEntity.badRequest().body(gr);
        }

        Mutant mutant = this.mutantService.registerSample(req.dna);

        if (mutant != null) {
            gr.isOk = true;
            gr.message = "Hello mutant!";
            return ResponseEntity.ok(gr);
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = new StatsResponse();
        stats.countHumanDNA = this.mutantService.countHumans();
        stats.countMutantDNA = this.mutantService.countMutants();
        stats.ratio = stats.countMutantDNA * 1.00d / stats.countHumanDNA * 1.00d;

        int ratio = (int)(stats.ratio * 100);
        stats.ratio = ratio * 1.0d / 100;

        return ResponseEntity.ok(stats);
    }

}
