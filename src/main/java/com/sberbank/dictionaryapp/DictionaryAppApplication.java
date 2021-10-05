package com.sberbank.dictionaryapp;

import com.sberbank.dictionaryapp.Entitis.City;
import com.sberbank.dictionaryapp.data.DataParser;
import com.sberbank.dictionaryapp.repositories.CityRepository;
import com.sberbank.dictionaryapp.servicies.CityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import java.util.*;


@SpringBootApplication
public class DictionaryAppApplication implements CommandLineRunner {
    private final CityService cityService;

    private static ApplicationContext applicationContext;

    public DictionaryAppApplication(CityService cityService) {
        this.cityService = cityService;
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(DictionaryAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            int cmd = sc.nextInt();
            System.out.println();

            switch (cmd) {
                case 0:
                    cityService.exitApplication(applicationContext);
                case 1:
                    cityService.getCities()
                            .forEach(System.out::println);
                    break;
                case 2:
                    cityService.saveCitiesFromFile();
                    break;
                case 3:
                    cityService.getCitiesOrderByName()
                            .forEach(System.out::println);
                    break;
                case 4:
                    cityService.getCitiesOrderByDistrictAndName()
                            .forEach(System.out::println);
                    break;
                case 5:
                    City city = cityService.getCityWithMaxPopulation();

                    if (city != null) {
                        System.out.println("[" + city.getId() + "] = " + city.getPopulation());
                    }

                    break;
                case 6:
                    Map<String, Integer> dictionary = cityService.getRegionAndCountOfCity();

                    for (Map.Entry<String, Integer> elem : dictionary.entrySet()) {
                        System.out.println(elem.getKey() + " - " + elem.getValue());
                    }

                    break;
                default:
                    System.out.println("Не правильный ключ.");
                    System.out.println();
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("1) Список городов.");
        System.out.println("2) Запарсить данные.");
        System.out.println("3) Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра.");
        System.out.println("4) Сортировка списка городов по федеральному округу и наименованию города внутри каждого федерального округа в алфавитном порядке по убыванию с учетом регистра.");
        System.out.println("5) Поиск города с наибольшим количеством жителей");
        System.out.println("6) Поиск количества городов в разрезе регионов");
        System.out.println("0) Выход");
        System.out.print("Ввш выбор: ");
    }
}
