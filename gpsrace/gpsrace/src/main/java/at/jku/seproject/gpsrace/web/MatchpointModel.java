package at.jku.seproject.gpsrace.web;



import at.jku.seproject.gpsrace.model.Race;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchpointModel {
	private Long id;
	private String name;
	
	private int oId;
	private RaceModel race;
	
	private double longitude;	
	private double latitude;
}
