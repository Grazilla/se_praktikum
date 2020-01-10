package at.jku.seproject.gpsrace.model;

import lombok.Data;
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
public class Race {
	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Column(unique=true)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<RaceParticipant> participants;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<Matchpoint> matchpoints;
}

