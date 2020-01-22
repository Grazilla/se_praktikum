package at.jku.seproject.gpsrace.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Participant;
import at.jku.seproject.gpsrace.model.ParticipantRepository;
import at.jku.seproject.gpsrace.model.Race;
import at.jku.seproject.gpsrace.model.RaceParticipant;
import at.jku.seproject.gpsrace.model.RaceRepository;

@RestController
@RequestMapping("/api")
public class ParticipantController {

	
	
	private final Logger log = LoggerFactory.getLogger(RaceController.class);
	private ParticipantRepository participantRepository;
	private RaceRepository raceRepository;
	
	public ParticipantController(ParticipantRepository participantRepository, RaceRepository raceRepository) {
		this.participantRepository = participantRepository;
		this.raceRepository = raceRepository;
	}
	
	@PostMapping("/race/{rId}/login")
	ResponseEntity<ParticipantModel> login(@PathVariable long rId, @Valid @RequestBody ParticipantModel participant) throws URISyntaxException {
		Participant p = participantRepository.findByName(participant.getName());
		
		if(p != null) {
			ParticipantModel pm = new ParticipantModel(p.getId(),p.getName(),null);

			for(RaceParticipant rp : p.getParticipations()) {
				if(rp.getRace().getId() == rId) {
					return ResponseEntity.ok().body(pm);
				}
			}
			
			RaceParticipant rp = new RaceParticipant();
			Race race = raceRepository.findById(rId);
			Matchpoint mp = null;
			for(Matchpoint m : race.getMatchpoints()) {
				if(mp == null) 
					mp = m;
				else if(mp.getOId() > m.getOId()) 
					mp = m;
			} 
			
			rp.setParticipant(p);
			rp.setRace(race);
			rp.setNextMatchpoint(mp);
			
			p.getParticipations().add(rp);
			this.participantRepository.save(p);
			
			
			return ResponseEntity.ok().body(pm);
			
			
			
		}
		else {
			p = new Participant();
			
			p.setName(participant.getName());		
			
			RaceParticipant rp = new RaceParticipant();
			Race race = raceRepository.findById(rId);
			Matchpoint mp = null;
			for(Matchpoint m : race.getMatchpoints()) {
				if(mp == null) 
					mp = m;
				else if(mp.getOId() > m.getOId()) 
					mp = m;
			} 
			
			rp.setParticipant(p);
			rp.setRace(race);
			rp.setNextMatchpoint(mp);
			
			p.setParticipations(new HashSet<RaceParticipant>());
			p.getParticipations().add(rp);
			this.participantRepository.save(p);
			
			ParticipantModel pm = new ParticipantModel(p.getId(),p.getName(),null);
			
	        return ResponseEntity.created(new URI("/api/participant/" + pm.getId()))
	                .body(pm);
		}
		
	}

	
}
