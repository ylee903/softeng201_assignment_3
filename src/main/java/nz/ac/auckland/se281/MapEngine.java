package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  // Maps a country name to its Country object (contains name, continent, and tax).
  private HashMap<String, Country> countries = new HashMap<>();

  // Graph representation using an adjacency list.
  // Maps a country name to a set of names representing its direct neighbors.
  private HashMap<String, HashSet<String>> graph = new HashMap<>();

  public MapEngine() {
    // add other code here if you want

    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  /**
   * Loads the map by reading countries and adjacencies data from files, and populates the countries
   * and graph data structures accordingly.
   */
  private void loadMap() {
    // This method is responsible for loading the map data
    // Read the list of countries from a file
    List<String> countriesData = Utils.readCountries();
    // Read the list of adjacencies (neighboring countries) from a file
    List<String> adjacenciesData = Utils.readAdjacencies();

    // Loop through each line of the countries data
    for (String line : countriesData) {
      // Split the line into an array of strings
      // Each line is in the format "Country,Continent,Tax"
      String[] details = line.split(",");
      String name = details[0]; // Extract the country name
      String continent = details[1]; // Extract the continent name
      int tax = Integer.parseInt(details[2]); // Extract the tax value

      // Create a new Country object and add it to the countries HashMap
      // The country's name is the key, and the Country object is the value
      countries.put(name, new Country(name, continent, tax));
      // Initialize an empty HashSet for the country in the graph HashMap
      // The country's name is the key, and the HashSet will store the names of its neighbors
      graph.put(name, new HashSet<>());
    }

    // Loop through each line of the adjacencies data
    for (String line : adjacenciesData) {
      // Split the line into an array of strings
      // The first element is a country's name, and the remaining elements are its neighbors
      String[] details = line.split(",");
      String country = details[0]; // Extract the country name

      // Retrieve the country's adjacency set from the graph HashMap
      HashSet<String> neighbors = graph.get(country); // Get the adjacency set for the country

      // Loop through the remaining elements in the array (the names of the neighbors)
      for (int i = 1; i < details.length; i++) {
        // Add each neighbor to the adjacency set
        neighbors.add(details[i]);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    while (true) {
      // Prompt the user to enter the name of the country
      System.out.print(MessageCli.INSERT_COUNTRY.getMessage());
      // Read the user's input
      String countryName = Utils.scanner.nextLine();

      try {
        // Capitalize the first letter of each word in the country name
        countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);

        // Retrieve the Country object for the specified country name
        Country country = countries.get(countryName);

        // Check if the country exists in the countries HashMap
        if (country != null) {
          // If the country exists, display its information using MessageCli
          MessageCli.COUNTRY_INFO.printMessage(
              country.getName(), country.getContinent(), Integer.toString(country.getTax()));
          break; // Exit the loop after successful operation
        } else {
          // If the country does not exist, display an error message
          System.out.println(MessageCli.INVALID_COUNTRY.getMessage(countryName));
        }
      } catch (Exception e) {
        // If an exception occurs, print an error message
        System.out.println("An error occurred: " + e.getMessage());
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
