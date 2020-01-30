package at.jku.seproject.gpsrace;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Race;
import at.jku.seproject.gpsrace.model.RaceRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

	private final RaceRepository repository;
	
	public Initializer(RaceRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		//create test data
		//Stream.of("Race 000", "Race 02", "Race 03", "Race 04").forEach(name -> repository.save(new Race(name)));
		
		Race race1 = new Race("JKU Schnaps Race");
		race1.setMatchpoints(new HashSet<Matchpoint>());
		race1.getMatchpoints().add(new Matchpoint("GRATURLIEREEEE! Du hast den fünten Punkt erreicht! Gönn dir ein Schnapserl! "
				+ "Auf gehts, ab gehts zum nächsten Spot, in Richtung Süden mega flott. Ein gelbes Kastl is the place to be... "
				+ "so leicht war der Hinweis überhaupt noch nie.", 4, 1.0, 1.0));
		race1.getMatchpoints().add(new Matchpoint("Test Point 3", 3, 0.1, 1.0));
		race1.getMatchpoints().add(new Matchpoint("Test Point 2", 2, 1.2, 1.3));
		race1.getMatchpoints().add(new Matchpoint("Test Point 2", 1, 1.2, 1.3));
		race1.getMatchpoints().add(new Matchpoint("Test Point 0", 0, 0.7, 1.0));
		repository.save(race1);
		
		Race race2 = new Race("Die Race gibt noch nicht");
		race1.setMatchpoints(new HashSet<Matchpoint>());
		race1.getMatchpoints().add(new Matchpoint("Test Point 4", 4, 1.0, 1.0));
		race1.getMatchpoints().add(new Matchpoint("Test Point 3", 3, 0.1, 1.0));
		race1.getMatchpoints().add(new Matchpoint("Test Point 2", 2, 1.2, 1.3));
		race1.getMatchpoints().add(new Matchpoint("Test Point 0", 0, 0.7, 1.0));
		repository.save(race2);
		
		
		repository.findAll().forEach(System.out::println);
	}

}
