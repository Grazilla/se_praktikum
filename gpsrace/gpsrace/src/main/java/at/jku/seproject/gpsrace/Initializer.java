package at.jku.seproject.gpsrace;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Participant;
import at.jku.seproject.gpsrace.model.ParticipantRepository;
import at.jku.seproject.gpsrace.model.Race;
import at.jku.seproject.gpsrace.model.RaceParticipant;
import at.jku.seproject.gpsrace.model.RaceParticipantRepository;
import at.jku.seproject.gpsrace.model.RaceRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

	private final RaceRepository repository;
	private final ParticipantRepository partRepository;
	private final RaceParticipantRepository racePartRepository;
	
	public Initializer(RaceRepository repository, ParticipantRepository partRepository, RaceParticipantRepository racePartRepository) {
		this.repository = repository;
		this.partRepository = partRepository;
		this.racePartRepository = racePartRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		//create test data
		//Stream.of("Race 000", "Race 02", "Race 03", "Race 04").forEach(name -> repository.save(new Race(name)));
		
		Participant part1 = new Participant("Luki");
		partRepository.save(part1);
		Participant part2 = new Participant("Lisa");
		partRepository.save(part2);
		
		Participant part3 = new Participant("Sandra");
		partRepository.save(part3);
		
		Participant part4 = new Participant("Sara");
		partRepository.save(part4);
		
		Race race1 = new Race("JKU Schnaps Race");
		race1.setMapLatitude(48.3368654);
		race1.setMapLongitude(14.3196514);
		race1.setMatchpoints(new HashSet<Matchpoint>());
		race1.setParticipants(new HashSet<RaceParticipant>());
		race1.getMatchpoints().add(new Matchpoint("GRATURLIEREEEE! Du hast den fünten Punkt erreicht! Gönn dir ein Schnapserl! "
				+ "Auf gehts, ab gehts zum nächsten Spot, in Richtung Süden mega flott. Ein gelbes Kastl is the place to be... "
				+ "so leicht war der Hinweis überhaupt noch nie.", 5, 14.320697, 48.337363));//the constructor uses longitude, latitude
		race1.getMatchpoints().add(new Matchpoint("Test Point 3", 3, 14.318426, 48.336812));
		Matchpoint start1 = new Matchpoint("Test Point 2", 2, 14.318641, 48.337526 );
		race1.getMatchpoints().add(start1);
		race1.getMatchpoints().add(new Matchpoint("Test Point 2", 1, 14.320697, 48.337363));
		race1.getMatchpoints().add(new Matchpoint("Test Point 0", 4, 14.320562, 48.336636));
		repository.save(race1);
		
		RaceParticipant racePart1 = new RaceParticipant(race1, part1);
		racePart1.setNextMatchpoint(start1);
		racePart1.setLatitude(48.337526);
		racePart1.setLongitude(14.318641);
		
		RaceParticipant racePart2 = new RaceParticipant(race1, part2);
		racePart2.setNextMatchpoint(start1);
		racePart2.setLatitude(48.337363);
		racePart2.setLongitude(14.320697);
		
		RaceParticipant racePart3 = new RaceParticipant(race1, part3);
		racePart3.setNextMatchpoint(start1);
		racePart3.setLatitude(48.337430);
		racePart3.setLongitude(14.320697);
		
		RaceParticipant racePart4 = new RaceParticipant(race1, part4);
		racePart4.setNextMatchpoint(start1);
		racePart4.setLatitude(48.337632);
		racePart4.setLongitude(14.320694);
		
		race1.getParticipants().add(racePart1);
		race1.getParticipants().add(racePart2);
		race1.getParticipants().add(racePart3);
		race1.getParticipants().add(racePart4);
		
		repository.save(race1);
		
		Race race2 = new Race("Die Race gibt noch nicht");
		race2.setMatchpoints(new HashSet<Matchpoint>());
		race2.setMapLatitude(48.3368654);
		race2.setMapLongitude(14.3196514);
		race2.getMatchpoints().add(new Matchpoint("Test Point 4", 4, 1.0, 1.0));
		race2.getMatchpoints().add(new Matchpoint("Test Point 3", 3, 0.1, 1.0));
		race2.getMatchpoints().add(new Matchpoint("Test Point 2", 2, 1.2, 1.3));
		race2.getMatchpoints().add(new Matchpoint("Test Point 0", 0, 0.7, 1.0));
		repository.save(race2);
		
		//Optional<Race> race1_2 = repository.findById(race1.getId());
		//System.out.println(race1_2.get().getParticipants());
		
		//repository.findAll().forEach(System.out::println);
	}

}
