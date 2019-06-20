package br.com.zup.xyinc.services;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;

import br.com.zup.xyinc.models.Poi;

public interface PoiService {

	List<Poi> getAll();
	
	Poi add(Poi poi);
	
	GeoResults<Poi> findByCoordinateAndDistance(Point point, Distance d_max);

}

