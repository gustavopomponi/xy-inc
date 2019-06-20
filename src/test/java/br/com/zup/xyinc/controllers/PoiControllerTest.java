package br.com.zup.xyinc.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.xyinc.models.Poi;
import br.com.zup.xyinc.models.PoiRequest;
import br.com.zup.xyinc.services.impl.PoiServiceImpl;

@RunWith(MockitoJUnitRunner.class) 
//@SpringBootTest
//@AutoConfigureMockMvc
//@EnableWebMvc
public class PoiControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
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
    
    @Test
    public void test_get_all_success() throws Exception {	   
    	
    	Poi poi1 = new Poi(null, "Lanchonete", new GeoJsonPoint(27,12));
    	Poi poi2 = new Poi(null, "Posto", new GeoJsonPoint(31,18));
    	Poi poi3 = new Poi(null, "Joalheria", new GeoJsonPoint(15,12));
    	Poi poi4 = new Poi(null, "Floricultura", new GeoJsonPoint(19,21));
    	Poi poi5 = new Poi(null, "Pub", new GeoJsonPoint(12,8));
    	Poi poi6 = new Poi(null, "Supermercado", new GeoJsonPoint(23,6));
    	Poi poi7 = new Poi(null, "Churrascaria", new GeoJsonPoint(28,2));
    	
    	List<Poi> pois = Arrays.asList(poi1, poi2, poi3, poi4, poi5, poi6, poi7);
    	
    	Mockito.when(poiServiceImpl.getAll()).thenReturn(pois);

    	mockMvc.perform(get("/api/v1/poi"))
    		   .andExpect(status().isOk())
    		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    		   .andDo(print());
    	
    	verify(poiServiceImpl, times(1)).getAll();
        verifyNoMoreInteractions(poiServiceImpl);
    	
    }
    
    @Test
    public void test_add_poi_success() throws Exception {	
    	
    	PoiRequest json = PoiRequest.builder()
    				.name("Posto de Gasolina")
    				.xcoord("27")
    				.ycoord("12")
    				.build();
    	
    	Poi poi = Poi.builder()
    			.id("12345")
    			.name("Posto de Gasolina")
    			.location(new GeoJsonPoint(27,12))
    			.build();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonInString2 = mapper.writeValueAsString(json);
    	
    	when(poiServiceImpl.add(any(Poi.class))).thenReturn(poi);
    	
    	mockMvc.perform(post("/api/v1/poi")
    		   .content(jsonInString2).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    		   .andDo(print())
		   	   .andExpect(status().isOk())
    		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	       
    	verify(poiServiceImpl, times(1)).add((any(Poi.class)));
        verifyNoMoreInteractions(poiServiceImpl);
    	
    }
    

}
