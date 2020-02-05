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

		Race race1 = new Race("JKU Schnaps Race");
		race1.setMapLatitude(48.3368654);
		race1.setMapLongitude(14.3196514);
		race1.setMatchpoints(new HashSet<Matchpoint>());
		race1.setParticipants(new HashSet<RaceParticipant>());
		
		race1.getMatchpoints().add(new Matchpoint("Der erste Punkt ist leicht und nicht weit. "
				+ "In Richtung Mensa gibt dir ein gelb-schwarzes Schild Bescheid.", 1, 14.324191, 48.335411));//the constructor uses longitude, latitude
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den zweiten Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. Der nächste Spot naht, beim Juridicum "
				+ "rufst du 147-Rat auf Draht!", 2, 14.322453, 48.336573));
		
		race1.getMatchpoints().add( new Matchpoint("GRATULIERE! Du hast den dritten Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. Hoppel hin und her und bleibe fit, "
				+ "den nächsten Hinweis gibts beim garbage-collector nahe LIT!", 3, 14.318526, 48.337615 ));
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den vierten Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. "
				+ "Die Parties dort sind legendär, die Memories leider ohne Gewähr! "
				+ "Du solltest den Ort mit vier Buchstaben kennen, um den nächsten QR-Code zu scannen!", 4, 14.315426, 48.336944));
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den fünften Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. "
				+ "Auf gehts, ab gehts zum nächsten Spot, in Richtung Süden mega flott. "
				+ "Ein gelbes Kastl is the place to be... "
				+ "so leicht war der Hinweis überhaupt noch nie!", 5, 14.312523, 48.336662));
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den sechsten Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. "
				+ "Wenn du müde bist auf deinen Beinen, "
				+ "Lass dich einfach transportieren "
				+ "Gönn dir ne Bosna bei der Endstation.",6,14.312391,48.332425));
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den siebten Punkt erreicht! "
				+ "Gönn dir ein Schnapserl. "
				+ "Der letzte Spot ist dir bekannt, bist du von dort doch weggerannt. "
				+ "Nahe dem schwarzen Gold ist das Ziel, mit einem Perspektivenwechsel beendest du das Spiel.",7,14.320356,48.334959));
		
		race1.getMatchpoints().add(new Matchpoint("GRATULIERE! Du hast den letzten Punkt erreicht "
				+ "und somit das Spiel erfolgreich abgeschlossen. "
				+ "Wenn du noch kannst... GÖNN DIR EIN SCHNAPSERL!",8,14.324191,48.335411));
					
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
		
	}

}
