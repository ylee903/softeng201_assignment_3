package nz.ac.auckland.se281;

import java.util.*;

class Country {
  String name;
  String continent;
  int taxFees;

  // constructor and getters/setters
}

public class MapEngine {
  private Map<String, Country> countriesMap;

  public MapEngine() {
    countriesMap = new HashMap<>();
    loadMap();
  }

  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String country : countries) {
      String[] parts = country.split(",");
      Country c = new Country();
      c.setName(parts[0]);
      c.setContinent(parts[1]);
      c.setTaxFees(Integer.parseInt(parts[2]));
      countriesMap.put(c.getName(), c);
    }

    // populate adjacencies
  }

  public void showInfoCountry() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Insert the name of the country:");
    String countryName = scanner.nextLine();
    Country country = countriesMap.get(countryName);
    if (country != null) {
      System.out.println(
          country.getName()
              + " => continent: "
              + country.getContinent()
              + ", tax fees: "
              + country.getTaxFees());
    } else {
      System.out.println("Country not found");
    }
  }

  // other methods
}
