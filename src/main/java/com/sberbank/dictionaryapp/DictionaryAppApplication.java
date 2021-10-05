package com.sberbank.dictionaryapp;

import com.sberbank.dictionaryapp.Entitis.City;
import com.sberbank.dictionaryapp.data.DataParser;
import com.sberbank.dictionaryapp.repositories.CityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import java.util.*;


@SpringBootApplication
public class DictionaryAppApplication implements CommandLineRunner {
    private final CityRepository cityRepository;
    private final DataParser dataParser;
    private static ApplicationContext applicationContext;

    public DictionaryAppApplication(CityRepository cityRepository, DataParser dataParser) {
        this.cityRepository = cityRepository;
        this.dataParser = dataParser;
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
                    SpringApplication.exit(applicationContext, () -> 0);
                case 1:
                    cityRepository.findAll().forEach(System.out::println);
                    break;
                case 2:
                    cityRepository.saveAll(dataParser.parseData());
                    break;
                case 3:
                    cityRepository.findAll(Sort.by(Sort.Order.asc("name").ignoreCase()))
                            .forEach(System.out::println);
                    break;
                case 4:
                    List<Sort.Order> orders = new ArrayList<>();
                    orders.add(new Sort.Order(Sort.Direction.ASC, "district"));
                    orders.add(new Sort.Order(Sort.Direction.ASC, "name"));

                    cityRepository.findAll(Sort.by(orders))
                            .forEach(System.out::println);
                    break;
                case 5:
                    List<City> cities = cityRepository.findAll();

                    if (!cities.isEmpty()) {
                        City superCity = Collections.max(cityRepository.findAll(), Comparator.comparing(City::getPopulation));
                        System.out.println("[" + superCity.getId() + "] = " + superCity.getPopulation());
                    }

                    break;
                case 6:
                    Map<String, Integer> dictionary = new HashMap<>();
                    cityRepository.findAll().forEach(city -> dictionary.merge(city.getRegion(), 1, (o, n) -> 1 + n));

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
