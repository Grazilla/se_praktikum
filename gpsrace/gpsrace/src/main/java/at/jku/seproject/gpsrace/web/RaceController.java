package at.jku.seproject.gpsrace.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Participant;
import at.jku.seproject.gpsrace.model.Race;
import at.jku.seproject.gpsrace.model.RaceParticipant;
import at.jku.seproject.gpsrace.model.RaceRepository;

@RestController
@RequestMapping("/api")
public class RaceController {
	private final Logger log = LoggerFactory.getLogger(RaceController.class);
	private RaceRepository raceRepository;
	
	public RaceController(RaceRepository raceRepository) {
		this.raceRepository = raceRepository;
	}
	
	@GetMapping("/race")
	Collection<RaceModel> races() {
		Collection<Race> races = raceRepository.findAll();
		ArrayList<RaceModel> allRaces = new ArrayList<RaceModel>();
		
		for(Race r : races) {
			RaceModel rm = new RaceModel(r.getId(),r.getName(), 0.0, 0.0,null,null);
			allRaces.add(rm);			
		}	
		return allRaces;
	}
	
	@GetMapping("/race/{id}")
	ResponseEntity<?> getRace(@PathVariable Long id) {
		log.info("Request to load race: {}", id);
        Optional<Race> race = raceRepository.findById(id);
        if(race.isPresent()) {
        	RaceModel rm = new RaceModel(race.get().getId(), race.get().getName(), race.get().getMapLatitude(), race.get().getMapLongitude(), null, null);
        	
            ArrayList<RaceParticipantModel> p = new ArrayList<RaceParticipantModel>();
            ArrayList<MatchpointModel> m = new ArrayList<MatchpointModel>();
            
            for(RaceParticipant rp : race.get().getParticipants()) {
            	Matchpoint mp = rp.getNextMatchpoint();
            	Participant part = rp.getParticipant();
            	ParticipantModel partM = new ParticipantModel(part.getId(), part.getName(), null);
            	MatchpointModel nextPoint = null;
            	
            	double distance = 0;
            	if(mp != null) {
	            	 nextPoint = new MatchpointModel(mp.getId(), mp.getName(), mp.getOId(), null, mp.getLongitude(), mp.getLatitude());
	            	 distance = Math.round(calcDistance(rp.getLatitude(), rp.getLongitude() , nextPoint.getLatitude(), nextPoint.getLongitude()) * 1000);
            	}
            	RaceParticipantModel rpm = new RaceParticipantModel(rp.getId(), rp.getLongitude(), rp.getLatitude(), null, partM, nextPoint, distance, null);
            	
            	p.add(rpm);
            }
            
            p.sort((RaceParticipantModel r1, RaceParticipantModel r2) -> {
            	if(r1.getNextMatchpoint() == null && r2.getNextMatchpoint() == null) {
            		//TODO compare timestamps;
            		return 0;
            	}
            	if(r1.getNextMatchpoint() == null) return 1;
            	if(r2.getNextMatchpoint() == null) return -1;
            	int comp = new Integer(r1.getNextMatchpoint().getOId()).compareTo(new Integer(r2.getNextMatchpoint().getOId()));
            	if(comp != 0) return comp;
            	comp = new Double(r1.getDistance()).compareTo(new Double(r2.getDistance()));
            	return comp;
            });
            
            for(Matchpoint mp : race.get().getMatchpoints()) {
            	MatchpointModel model = new MatchpointModel(mp.getId(), mp.getName(), mp.getOId(), null, mp.getLongitude(), mp.getLatitude()); 
            	m.add(model);
            }
            
            m.sort((MatchpointModel m1, MatchpointModel m2) -> new Integer(m1.getOId()).compareTo(new Integer(m2.getOId())));
            
            rm.setParticipants(p);
            rm.setMatchpoints(m);
            
            return ResponseEntity.ok().body(rm);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	private static double calcDistance(double lat1, double lon1, double lat2, double lon2) {
		return calcDistance(lat1, lon1, lat2, lon2, "K");
	}
	private static double calcDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
	
    /*@PostMapping("/race")
    ResponseEntity<Race> createRace(@Valid @RequestBody Race race) throws URISyntaxException {
        log.info("Request to create race: {}", race);
        Race result = raceRepository.save(race);
        return ResponseEntity.created(new URI("/api/race/" + result.getId()))
                .body(result);
    }
    
    @PutMapping("/race/{id}")
    ResponseEntity<Race> updateRace(@Valid @RequestBody Race race) {
        log.info("Request to update race: {}", race);
        Race result = raceRepository.save(race);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/race/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable Long id) {
        log.info("Request to delete race: {}", id);
        raceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }*/
}

