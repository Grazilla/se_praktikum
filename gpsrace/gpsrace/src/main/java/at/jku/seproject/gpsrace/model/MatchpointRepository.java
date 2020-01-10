package at.jku.seproject.gpsrace.model;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchpointRepository extends JpaRepository<Matchpoint, Long> {

	//Matchpoint findByIds(int rId, int oId);
	Collection<Matchpoint> findByRace(Race race);

}
