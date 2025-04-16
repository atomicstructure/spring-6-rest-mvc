package com.samantha.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samantha.spring6restmvc.model.Beer;
import com.samantha.spring6restmvc.services.BeerService;
import com.samantha.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Captor
    ArgumentCaptor<UUID> argumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;


    @Test
    void testPatchBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().getFirst();

        mockMvc.perform(patch(BeerController.BEER_PATH + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(argumentCaptor.capture(), any(Beer.class));
    }
    @Test
    void testDeleteBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().getFirst();
        mockMvc.perform(delete(BeerController.BEER_PATH + beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(beer.getId());
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().getFirst();

        mockMvc.perform(put(BeerController.BEER_PATH + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());


        verify(beerService).updateBeerById(argumentCaptor.capture(), any(Beer.class));
        assertThat(argumentCaptor.getValue().equals(beer.getId()));
    }

    @Test
    void testCreateNewBeer() throws Exception{
        Beer beer = beerServiceImpl.listBeers().getFirst();

        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    @Test
    void testListBeers() throws  Exception{

        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerById() throws Exception{

        Beer testBeer = beerServiceImpl.listBeers().getFirst();

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH + "/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())))
                .andExpect(jsonPath("$.beerStyle", is(testBeer.getBeerStyle().name())));
    }
}