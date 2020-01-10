package at.jku.seproject.gpsrace.web;

import java.util.List;


import at.jku.seproject.gpsrace.model.Matchpoint;
import at.jku.seproject.gpsrace.model.RaceParticipant;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceModel {

	private Long id;
	private String name;

	private List<RaceParticipantModel> participants;
	private List<MatchpointModel> matchpoints;
	
}
