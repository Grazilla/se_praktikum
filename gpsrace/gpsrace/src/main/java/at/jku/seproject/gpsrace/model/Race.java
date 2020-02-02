package at.jku.seproject.gpsrace.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "race")
@EqualsAndHashCode
public class Race {
	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Column(unique=true)
	private String name;
	
	private double mapLatitude;
	private double mapLongitude;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@EqualsAndHashCode.Exclude 
	private Set<RaceParticipant> participants;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@EqualsAndHashCode.Exclude 
	private Set<Matchpoint> matchpoints;
}

