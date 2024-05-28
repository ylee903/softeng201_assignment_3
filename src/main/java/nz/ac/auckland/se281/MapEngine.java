package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // Create a map to store countries
    Map<String, Country> countryMap = new HashMap<>();

    // Parse countries
    for (String country : countries) {
      String[] parts = country.split(",");
      Country c = new Country();
      c.setName(parts[0]);
      c.setContinent(parts[1]);
      c.setTaxFees(Integer.parseInt(parts[2]));
      countryMap.put(c.getName(), c);
    }

    // Parse adjacencies
    for (String adjacency : adjacencies) {
      String[] parts = adjacency.split(",");
      Country country1 = countryMap.get(parts[0]);
      Country country2 = countryMap.get(parts[1]);
      if (country1 != null && country2 != null) {
        country1.addAdjacentCountry(country2);
        country2.addAdjacentCountry(country1);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
