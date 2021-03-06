package at.jku.seproject.gpsrace.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Participant;
import at.jku.seproject.gpsrace.model.ParticipantRepository;
import at.jku.seproject.gpsrace.model.Race;
import at.jku.seproject.gpsrace.model.RaceParticipant;
import at.jku.seproject.gpsrace.model.RaceParticipantRepository;
import at.jku.seproject.gpsrace.model.RaceRepository;

@RestController
@RequestMapping("/api")
public class ParticipantController {
	private final Logger log = LoggerFactory.getLogger(RaceController.class);
	private ParticipantRepository participantRepository;
	private RaceParticipantRepository raceParticipantRepository;
	private RaceRepository raceRepository;
	
	public ParticipantController(ParticipantRepository participantRepository, RaceRepository raceRepository, RaceParticipantRepository raceParticipantRepository) {
		this.participantRepository = participantRepository;
		this.raceRepository = raceRepository;
		this.raceParticipantRepository = raceParticipantRepository;
	}
	
	@PutMapping("/race/{rId}/updateCoords/{pId}")
	ResponseEntity<?> updateCoords(@PathVariable long rId, @PathVariable long pId, @Valid @RequestBody CoordsModel coords) {
		Race r = raceRepository.findById(rId);
		RaceParticipant participant = null;
		System.out.println("RaceId: " + rId + ", ParticipantId: " + pId);
		for(RaceParticipant par : r.getParticipants()) {
			if(par.getParticipant().getId() == pId) {
				participant = par;
				break;
			}
			System.out.println(par.getParticipant().getId());
		}
		if(participant != null) {
			System.out.println("Found par: " + participant.getId());
			participant.setLatitude(coords.getLatitude());
			participant.setLongitude(coords.getLongitude());
			raceParticipantRepository.save(participant);
			return ResponseEntity.ok().body(coords);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/login")
	ResponseEntity<ParticipantModel> login(@Valid @RequestBody ParticipantModel participant) throws URISyntaxException {
		Participant p = participantRepository.findByName(participant.getName());
		
		if(p != null) {
			ParticipantModel pm = new ParticipantModel(p.getId(), p.getName(), null);
			return ResponseEntity.ok().body(pm);
		}
		else {
			p = new Participant();
			p.setName(participant.getName());
			this.participantRepository.save(p);
			ParticipantModel pm = new ParticipantModel(p.getId(), p.getName(), null);
			return ResponseEntity.ok().body(pm);
		}
	}
	
	@PutMapping("race/{rId}/{pId}/matchPoint/{oId}")ResponseEntity<?> reached(@PathVariable long rId, @PathVariable long pId, @PathVariable long oId) throws URISyntaxException{
		Race r = this.raceRepository.findById(rId);
		RaceParticipant participant = null;
		
		System.out.println("RaceId: " + rId + ", ParticipantId: " + pId);
		for(RaceParticipant par : r.getParticipants()) {
			if(par.getParticipant().getId() == pId) {
				participant = par;
				break;
			}
			System.out.println(par.getParticipant().getId());
		}
		if(participant != null) {
			if(oId == participant.getNextMatchpoint().getOId()) {
				Set<Matchpoint> mps = r.getMatchpoints();
				Matchpoint mp;
				for(Matchpoint m : mps) {
					if(m.getOId() == oId+1) {
						participant.setNextMatchpoint(m);
						this.raceParticipantRepository.save(participant);
						return ResponseEntity.ok().body(m);
					}
				}
				this.raceParticipantRepository.save(null);
				return ResponseEntity.ok().body(null);
			}
			else {
				return ResponseEntity.notFound().build();
			}
			
//			System.out.println("Found par: " + participant.getId());
//			participant.setLatitude(coords.getLatitude());
//			participant.setLongitude(coords.getLongitude());
//			raceParticipantRepository.save(participant);
//			return ResponseEntity.ok().body(coords);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/race/{rId}/register")
	ResponseEntity<ParticipantModel> register(@PathVariable long rId, @Valid @RequestBody ParticipantModel participant) throws URISyntaxException {
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
			
			race.getParticipants().add(rp);
			rp.setRace(race);
			rp.setParticipant(p);
			rp.setNextMatchpoint(mp);
			
			this.raceRepository.save(race);
			
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
