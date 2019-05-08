package br.com.zup.xyinc.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Poi {

	@Id
	@GeneratedValue
	private String id;
	@Field("name")
	@NotEmpty(message="Poi's name cannot be empty !!")
	private String name;
	@Field
	@NotNull(message="Location must not be empty !!")
	private GeoJsonPoint location;
	
	protected Poi() {}
	
	public Poi(final String name, final GeoJsonPoint location) {
		this.name = name;
		this.location = location;
	}
	
}
