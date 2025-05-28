package com.samantha.spring6restmvc.bootstrap;

import com.samantha.spring6restmvc.entities.Beer;
import com.samantha.spring6restmvc.entities.Customer;
import com.samantha.spring6restmvc.model.BeerCSVRecord;
import com.samantha.spring6restmvc.model.BeerStyle;

import com.samantha.spring6restmvc.repositories.BeerRepository;
import com.samantha.spring6restmvc.repositories.CustomerRepository;
import com.samantha.spring6restmvc.services.BeerCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {

        if (beerRepository.count() < 10){

                File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

                List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);
                recs.forEach(beerCSVRecord -> {
                    BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                        case "American Pale Lager" -> BeerStyle.LAGER;
                        case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                                BeerStyle.ALE;
                        case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                        case "American Porter" -> BeerStyle.PORTER;
                        case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                        case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                        case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                        case "English Pale Ale" -> BeerStyle.PALE_ALE;
                        default -> BeerStyle.PILSNER;
                    };

                    beerRepository.save(Beer.builder()
                            .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                            .beerStyle(beerStyle)
                            .price(BigDecimal.TEN)
                            .upc(beerCSVRecord.getRow().toString())
                            .quantityOnHand(beerCSVRecord.getCount())
                            .build());
                });

        }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()

                    .beerName("Trophy")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123456789012")
                    .quantityOnHand(100)
                    .price(new BigDecimal("10.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()

                    .beerName("Heineken")
                    .beerStyle(BeerStyle.IPA)
                    .upc("123456789013")
                    .quantityOnHand(50)
                    .price(new BigDecimal("12.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()

                    .beerName("Legend Extra Stout")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("123456789014")
                    .quantityOnHand(75)
                    .price(new BigDecimal("11.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0){
            Customer customer1 = Customer.builder()

                    .customerName("Samantha")
                    .email("customer1@example.com")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()

                    .customerName("John")
                    .email("customer2@example.com")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Customer customer3 = Customer.builder()

                    .customerName("Jane")
                    .email("customer3@example.com")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();


            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
