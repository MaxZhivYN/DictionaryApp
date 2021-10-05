package com.sberbank.dictionaryapp.servicies;

import com.sberbank.dictionaryapp.Entitis.City;
import com.sberbank.dictionaryapp.data.DataParser;
import com.sberbank.dictionaryapp.repositories.CityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final DataParser dataParser;

    public CityService(CityRepository cityRepository, DataParser dataParser) {
        this.cityRepository = cityRepository;
        this.dataParser = dataParser;
    }


    public void exitApplication(ApplicationContext applicationContext) {
        SpringApplication.exit(applicationContext, () -> 0);
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public void saveCitiesFromFile() {
        try {
            cityRepository.saveAll(dataParser.parseData());
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List<City> getCitiesOrderByName() {
        return cityRepository.findAll(Sort.by(Sort.Order.asc("name").ignoreCase()));
    }

    public List<City> getCitiesOrderByDistrictAndName() {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "district"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));

        return cityRepository.findAll(Sort.by(orders));
    }

    public City getCityWithMaxPopulation() {
        List<City> cities = cityRepository.findAll();

        if (!cities.isEmpty()) {
            return Collections.max(cities, Comparator.comparing(City::getPopulation));
        }

        return null;
    }

    public Map<String, Integer> getRegionAndCountOfCity() {
        Map<String, Integer> dictionary = new HashMap<>();
        cityRepository.findAll().forEach(city -> dictionary.merge(city.getRegion(), 1, (o, n) -> 1 + n));

        return dictionary;
    }
}
