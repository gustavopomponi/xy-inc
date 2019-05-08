package br.com.zup.xyinc.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class PoiRequest {

	@Id
	@GeneratedValue
	private String id;
	@NotEmpty(message="Poi's name cannot be empty !!")
	private String name;
	@NotEmpty(message="X Coordinate cannot be null !!")
	private String xcoord;
	@NotEmpty(message="Y Coordinate cannot be null !!")
	private String ycoord;
	
	
}
