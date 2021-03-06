package at.jku.seproject.gpsrace.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "participant")
@EqualsAndHashCode
public class Participant {
	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	@Column(unique=true)
	private String name;
	
	
	@OneToMany
	@EqualsAndHashCode.Exclude 
	private Set<RaceParticipant> participations;
}
