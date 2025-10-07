package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.repositories.BeerRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.samantha.spring6restmvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
class BeerOrderControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;
    @Autowired
    private BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testListBeerOrders() throws Exception {
        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH)
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", greaterThan(0)));
    }

    @Test
    void testGetBeerOrderById() throws Exception {
        val beerOrder = beerRepository.findAll().get(0);
        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH + "/" + beerOrder.getId().toString())
                .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id", is(beerOrder.getId().toString())));
    }
}