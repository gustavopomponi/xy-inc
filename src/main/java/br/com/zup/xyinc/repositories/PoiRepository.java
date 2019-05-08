package br.com.zup.xyinc.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import br.com.zup.xyinc.models.Poi;

@Repository
public interface PoiRepository extends MongoRepository<Poi, String> {
	
	@Query
	Optional<List<Poi>> findByLocationNear(Point point, Distance dist);
	
}



