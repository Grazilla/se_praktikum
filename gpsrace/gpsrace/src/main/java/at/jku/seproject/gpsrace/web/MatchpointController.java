package at.jku.seproject.gpsrace.web;

import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.MatchpointRepository;
import at.jku.seproject.gpsrace.model.Race;


public class MatchpointController {

	private final Logger log = LoggerFactory.getLogger(RaceController.class);
	private MatchpointRepository matchpointRepository;
	
	public MatchpointController(MatchpointRepository matchpointRepository) {
		this.matchpointRepository = matchpointRepository;
	}
	
	@GetMapping("/matchpoint")
	Collection<Matchpoint> getMachpointsForRace(@PathVariable Race race) {
		//Collection<Matchpoint> matchpoints = matchpointRepository.findByRid(rId);
		
		
		return matchpointRepository.findByRace(race);
	}
	
	Collection<Matchpoint> getMachpointsForRace() {
		return matchpointRepository.findAll();
	}
	
	@GetMapping("/matchpoint")
	Matchpoint getMatchpoint(@PathVariable int rId, int oId) {
		//return matchpointRepository.findByIds(rId, oId);
		// collection = repo.getEntities
		// sortedCollection = collection.sort
		// return sortedCollection
		return null;
	}
	
    @PostMapping("/matchpoint")
    ResponseEntity<Matchpoint> createRace(@Valid @RequestBody Matchpoint matchpoint) throws URISyntaxException {
        log.info("Request to create matchpoint: {}", matchpoint);
        Matchpoint result = matchpointRepository.save(matchpoint);
        return ResponseEntity.created(new URI("/api/matchpoint/" + result.getId()))
                .body(result);
    }
    
    @PutMapping("/matchpoint/{id}")
    ResponseEntity<Matchpoint> updateRace(@Valid @RequestBody Matchpoint matchpoint) {
        log.info("Request to update matchpoint: {}", matchpoint);
        Matchpoint result = matchpointRepository.save(matchpoint);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/matchpoint/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable Long id) {
        log.info("Request to delete matchpoint: {}", id);
        matchpointRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
