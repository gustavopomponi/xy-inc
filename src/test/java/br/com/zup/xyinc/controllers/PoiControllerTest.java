package br.com.zup.xyinc.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.xyinc.models.Poi;
import br.com.zup.xyinc.models.PoiRequest;
import br.com.zup.xyinc.responses.Response;
import br.com.zup.xyinc.services.impl.PoiServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PoiControllerTest {
	
	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean
	private PoiServiceImpl poiServiceImpl;

    @InjectMocks
    private PoiController poiController;
    
    @Before
    public void init() throws Exception {
    	
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(poiController)
                .build();
        
    }
    
    @DisplayName("Test Spring - Get All Pois")
    @Test
    public void test_get_all_success() throws Exception {	   
    	
    	Poi poi1 = new Poi("Lanchonete", new GeoJsonPoint(27,12));
    	Poi poi2 = new Poi("Posto", new GeoJsonPoint(31,18));
    	Poi poi3 = new Poi("Joalheria", new GeoJsonPoint(15,12));
    	Poi poi4 = new Poi("Floricultura", new GeoJsonPoint(19,21));
    	Poi poi5 = new Poi("Pub", new GeoJsonPoint(12,8));
    	Poi poi6 = new Poi("Supermercado", new GeoJsonPoint(23,6));
    	Poi poi7 = new Poi("Churrascaria", new GeoJsonPoint(28,2));
    	
    	List<Poi> pois = Arrays.asList(poi1, poi2, poi3, poi4, poi5, poi6, poi7);
    	
    	Mockito.when(poiServiceImpl.getAll()).thenReturn(pois);

    	mockMvc.perform(get("/api/v1/poi"))
    		   .andExpect(status().isOk())
    		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    	
    	verify(poiServiceImpl, times(1)).getAll();
        verifyNoMoreInteractions(poiServiceImpl);
    	
    }
    
    @DisplayName("Test Spring - Add Poi")
    @Test
    public void test_add_poi_success() throws Exception {	
    	
    	PoiRequest json = new PoiRequest();
    	
    	json.setName("Posto de Gasolina");
    	json.setXcoord("27");
    	json.setYcoord("12");
    	
    	Poi poi3 = new Poi("Posto de Gasolina", new GeoJsonPoint(27,12));
    	poi3.setId("12345");
    	
    	System.out.println(json);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    	System.out.println(jsonInString2);
    	
    	//Poi poimaster = new ObjectMapper().readValue(jsonInString2, Poi.class);
    	
    	Mockito.when(poiServiceImpl.add(poi3)).thenReturn(poi3);
    	
    	mockMvc.perform(post("/api/v1/poi")
    		   .content(jsonInString2).contentType(MediaType.APPLICATION_JSON)
    		   .characterEncoding("utf-8"))
    		   .andDo(print())
		   	   .andExpect(status().isOk())
		   	   .andExpect(jsonPath("$.*", Matchers.notNullValue()))
			   .andDo(mvcResult -> {
	                System.out.println(mvcResult);
	           });
	       
    	
    }
    

}
