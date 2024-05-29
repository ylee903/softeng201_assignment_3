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

  /** */
  public MapEngine() {
    // add other code here if you want

    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  /**
   * Loads the map data from files.
   *
   * <p>This method reads country and adjacency data from files, then populates the 'countries' and
   * 'graph' HashMaps with this data. Each line of the countries data should be in the format
   * "Country,Continent,Tax".
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

  /**
   * this method is invoked when the user run the command info-country. Prompts the user to enter a
   * country name and displays information about the country.
   *
   * <p>This method continuously prompts the user to enter a country name until a valid country is
   * entered. It then retrieves the corresponding Country object from the 'countries' HashMap and
   * displays the country's name, continent, and tax using the MessageCli.COUNTRY_INFO message. If
   * the country does not exist in the 'countries' HashMap, it displays an error message using the
   * MessageCli.INVALID_COUNTRY message. If an exception occurs during the process, it prints an
   * error message using the MessageCli.COMMAND_NOT_FOUND message.
   */
  public void showInfoCountry() {
    while (true) {
      System.out.print(MessageCli.INSERT_COUNTRY.getMessage());
      String countryName = Utils.scanner.nextLine();

      try {
        countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
        Country country =
            getCountry(countryName); // Use a method that might throw CountryNotFoundException

        // If the country exists, display its information using MessageCli
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), Integer.toString(country.getTax()));
        break; // Exit the loop after successful operation

      } catch (CountryNotFoundException e) {
        System.out.println(MessageCli.INVALID_COUNTRY.getMessage(countryName));
      } catch (Exception e) {
        System.out.println("You somehow created an exceptional exception: " + e.getMessage());
      }
    }
  }

  /**
   * Retrieves a Country object from the 'countries' HashMap.
   *
   * <p>This method attempts to retrieve a Country object with the given country name from the
   * 'countries' HashMap. If a country with the given name does not exist in the HashMap, it throws
   * a CountryNotFoundException.
   *
   * @param countryName the name of the country to retrieve
   * @return the Country object corresponding to the given country name
   * @throws CountryNotFoundException if a country with the given name does not exist in the
   *     'countries' HashMap
   */
  private Country getCountry(String countryName) {
    Country country = countries.get(countryName);
    if (country == null) {
      throw new CountryNotFoundException(MessageCli.INVALID_COUNTRY.getMessage(countryName));
    }
    return country;
  }

  /**
   * this method is invoked when the user run the command route.
   *
   * @throws CountryNotFoundException
   */
  public void showRoute() {

    // prompt the user to enter the source country and verify it exists using the getCountry method
    System.out.print(MessageCli.INSERT_SOURCE.getMessage());
    String sourceCountry = Utils.scanner.nextLine();
    sourceCountry = Utils.capitalizeFirstLetterOfEachWord(sourceCountry);
    Country source = getCountry(sourceCountry);

    // prompt the user to enter the destination country and verify it exists using the getCountry
    // method
    System.out.print(MessageCli.INSERT_DESTINATION.getMessage());
    String destinationCountry = Utils.scanner.nextLine();
    destinationCountry = Utils.capitalizeFirstLetterOfEachWord(destinationCountry);
    Country destination = getCountry(destinationCountry);
  }

  private void findShortestPath(String start, String destination) {
    // Implement BFS here to find the shortest path
  }

  private List<String> extractContinents(List<String> path) {
    return path;
    // Extract unique continents from the path
  }

  private int calculateTaxes(List<String> path) {
    return 0;
    // Calculate total taxes, excluding the tax for the starting country
  }
}
