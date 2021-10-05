package com.sberbank.dictionaryapp.data;

import com.sberbank.dictionaryapp.Entitis.City;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DataParser {
    public List<City> parseData() throws FileNotFoundException, ParseException {
        Scanner sc = new Scanner(new File("src/main/resources/data/Cities.txt"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        List<City> cities = new ArrayList<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] strings = line.split(";");

            Long id = Long.parseLong(strings[0]);
            String name = strings[1];
            String region = strings[2];
            String district = strings[3];
            Integer population = Integer.parseInt(strings[4]);
            Date foundation = formatter.parse(strings[5]);

            City city = City.builder()
                    .id(id)
                    .name(name)
                    .region(region)
                    .district(district)
                    .population(population)
                    .foundation(foundation)
                    .build();

            cities.add(city);

        }

        return cities;
    }


}


