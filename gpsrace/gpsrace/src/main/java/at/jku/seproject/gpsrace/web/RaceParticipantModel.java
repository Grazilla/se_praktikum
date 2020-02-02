package at.jku.seproject.gpsrace.web;

import java.sql.Timestamp;



import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.Participant;
import at.jku.seproject.gpsrace.model.Race;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceParticipantModel {
	
	private Long id;
	
	private double longitude;	
	
	private double latitude;
	
	private RaceModel race;
	
	private ParticipantModel participant;
	
	private MatchpointModel nextMatchpoint;
	
	private double distance;
	
	private Timestamp time;
	
	

}
