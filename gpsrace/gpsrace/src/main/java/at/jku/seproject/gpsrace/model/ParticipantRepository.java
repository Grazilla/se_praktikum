package at.jku.seproject.gpsrace.model;

import org.springframework.data.jpa.repository.JpaRepository;




public interface ParticipantRepository extends JpaRepository<Participant, Long>{	
	Participant findByName(String name);
}
