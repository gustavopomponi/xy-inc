package br.com.zup.xyinc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Poi {

	@Id
	@GeneratedValue
	private String id;
	@Field("name")
	@NotEmpty(message="Poi's name cannot be empty !!")
	private String name;
	@Valid
	@NotNull(message="Location must not be empty !!")
	private GeoJsonPoint location;

	
}
