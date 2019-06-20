package br.com.zup.xyinc.repositories;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import br.com.zup.xyinc.models.Poi;

@Repository
public interface PoiRepository extends MongoRepository<Poi, String> {
	
	//@Query
	//Optional<List<Poi>> findByLocationNear(Point point, Distance dist);
	
	 GeoResults<Poi> findByLocationNear(Point location, Distance distance);
	
}



