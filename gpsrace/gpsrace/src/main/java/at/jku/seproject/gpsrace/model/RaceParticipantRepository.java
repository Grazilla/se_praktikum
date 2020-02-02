package at.jku.seproject.gpsrace.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceParticipantRepository extends JpaRepository<RaceParticipant, Long> {
	RaceParticipant findById(long id);
	RaceParticipant findByRaceAndParticipant(Race race, Participant participant);
}
