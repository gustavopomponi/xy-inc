package br.com.zup.xyinc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.xyinc.models.Poi;
import br.com.zup.xyinc.models.PoiRequest;
import br.com.zup.xyinc.responses.Response;
import br.com.zup.xyinc.services.impl.PoiServiceImpl;

@RestController
@RequestMapping("/api/v1/poi")
@ResponseBody
public class PoiController {
	
	PoiServiceImpl poiServiceImpl;
	
	public PoiController(PoiServiceImpl poiServiceImpl) {
		this.poiServiceImpl = poiServiceImpl;
	}
	
	@GetMapping(path = "")
	public ResponseEntity<Response<List<Poi>>> getAllPoi() {
		return ResponseEntity.ok(new Response<List<Poi>>(this.poiServiceImpl.getAll()));
	}
	
	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Poi>> addPoi(@Valid @RequestBody PoiRequest poiRequest, BindingResult result){
		
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> errors.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Poi>(errors));
		}
		
		final GeoJsonPoint locationPoint = new GeoJsonPoint(
		        Double.valueOf(poiRequest.getXcoord()),
		        Double.valueOf(poiRequest.getYcoord()));
		
		
		return ResponseEntity.ok(new Response<Poi>(this.poiServiceImpl.add(new Poi(poiRequest.getName(),locationPoint))));
		
	}
	
	@GetMapping(path = "/{x_coordinate}/{y_coordinate}/{d_max}")
	public ResponseEntity<Response<GeoResults<Poi>>> findPoiByCoordinatesAndDistance(@PathVariable int x_coordinate, @PathVariable int y_coordinate, @PathVariable double d_max){
		return ResponseEntity.ok(new Response<GeoResults<Poi>>(this.poiServiceImpl.findByCoordinateAndDistance(new Point(Double.valueOf(x_coordinate), Double.valueOf(y_coordinate)), new Distance(d_max, Metrics.KILOMETERS))));
	}
	

}
