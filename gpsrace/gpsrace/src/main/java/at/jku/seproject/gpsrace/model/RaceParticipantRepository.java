package at.jku.seproject.gpsrace.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceParticipantRepository extends JpaRepository<RaceParticipant, Long> {
	Participant findById(long id);
}
