package at.jku.seproject.gpsrace.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "raceParticipant")
@EqualsAndHashCode
public class RaceParticipant {
	@Id
	@GeneratedValue
	private Long id;
	
	
	@SuppressWarnings("unused")
	private double longitude;	
	
	@SuppressWarnings("unused")
	private double latitude;
	
	@ManyToOne
	@NonNull
	@EqualsAndHashCode.Exclude 
	private Race race;
	
	@ManyToOne
	@NonNull
	@EqualsAndHashCode.Exclude 
	private Participant participant;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude 
	private Matchpoint nextMatchpoint;
	
	private Timestamp time;
	
	
}
