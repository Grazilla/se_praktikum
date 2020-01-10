package at.jku.seproject.gpsrace.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Long> {
	Race findByName(String name); // 
	Race findById(long id);
}
