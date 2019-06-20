package br.com.zup.xyinc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;


import br.com.zup.xyinc.models.Poi;
import br.com.zup.xyinc.repositories.PoiRepository;
import br.com.zup.xyinc.services.PoiService;

@Service
public class PoiServiceImpl implements PoiService {

	@Autowired
	private PoiRepository poiRepository;
	
	@Override
	public List<Poi> getAll() {
		
		return this.poiRepository.findAll();
		
	}

	@Override
	public GeoResults<Poi> findByCoordinateAndDistance(Point point, Distance d_max) {
	
		//return this.poiRepository.findById(String.valueOf(d_max));
		
		return this.poiRepository.findByLocationNear(point, d_max);
	}

	@Override
	public Poi add(Poi poi) {
		
		return this.poiRepository.save(poi);
		
	}

}
