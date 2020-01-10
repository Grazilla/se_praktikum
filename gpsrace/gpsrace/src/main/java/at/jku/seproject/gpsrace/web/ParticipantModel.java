package at.jku.seproject.gpsrace.web;

import java.util.List;
import lombok.*;



import at.jku.seproject.gpsrace.model.RaceParticipant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantModel {

	private Long id;	
	private String name;
	
	private List<RaceParticipantModel> participations;
	
}
