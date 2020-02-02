package at.jku.seproject.gpsrace.model;

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
@Table(name = "matchpoint")
@EqualsAndHashCode
public class Matchpoint {

	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	private String name;
	
	@NonNull
	private int oId;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude 
	private Race race;
	
	@NonNull
	private double longitude;
	@NonNull
	private double latitude;
}
