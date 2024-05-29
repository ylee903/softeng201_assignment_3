package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  // Maps a country name to its Country object (contains name, continent, and tax).
  private HashMap<String, Country> countries = new HashMap<>();

  // Graph representation using an adjacency list.
  // Maps a country name to a set of names representing its direct neighbors.
  private HashMap<String, List<String>> graph = new HashMap<>();

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
      graph.put(name, new ArrayList<>());
    }

    // Loop through each line of the adjacencies data
    for (String line : adjacenciesData) {
      // Split the line into an array of strings
      // The first element is a country's name, and the remaining elements are its neighbors
      String[] details = line.split(",");
      String country = details[0]; // Extract the country name

      // Retrieve the country's adjacency set from the graph HashMap
      List<String> neighbors = graph.get(country); // Get the neighbors list for the country

      // Loop through the remaining elements in the array (the names of the neighbors)
      for (int i = 1; i < details.length; i++) {
        // Add each neighbor to the neighbors list
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
    String sourceCountry = promptForCountry("Enter the start country:");
    String destinationCountry = promptForCountry("Enter the destination country:");

    if (sourceCountry.equals(destinationCountry)) {
      System.out.println("No cross-border travel is required!");
      return;
    }

    List<String> shortestPath = findShortestPath(sourceCountry, destinationCountry);
    if (shortestPath.isEmpty()) {
      System.out.println("No path found from " + sourceCountry + " to " + destinationCountry);
      return;
    }

    List<String> continents = extractContinents(shortestPath);
    int taxes = calculateTaxes(shortestPath);

    System.out.println("The fastest route is: " + shortestPath);
    System.out.println("You will visit the following continents: " + String.join(", ", continents));
    System.out.println("You will spend this amount " + taxes + " for cross-border taxes");
  }

  private String promptForCountry(String message) {
    System.out.print(message);
    String countryName = Utils.scanner.nextLine();
    countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
    try {
      Country country = getCountry(countryName);
      return country.getName();
    } catch (CountryNotFoundException e) {
      System.out.println(e.getMessage());
      return promptForCountry(message); // Recurse until a valid country is entered
    }
  }

  private List<String> findShortestPath(String start, String destination) {
    Map<String, String> parentCountry = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    List<String> shortestPath = new ArrayList<>();

    queue.add(start);
    parentCountry.put(start, null);

    while (!queue.isEmpty()) {
      String currentCountry = queue.poll();
      if (currentCountry.equals(destination)) {
        String country = destination;
        while (country != null) {
          shortestPath.add(0, country);
          country = parentCountry.get(country);
        }
        return shortestPath;
      }

      for (String neighbor : graph.getOrDefault(currentCountry, new ArrayList<>())) {
        if (!parentCountry.containsKey(neighbor)) {
          queue.add(neighbor);
          parentCountry.put(neighbor, currentCountry);
        }
      }
    }
    return shortestPath;
  }

  private List<String> extractContinents(List<String> path) {
    Set<String> continents = new HashSet<>();
    for (String countryName : path) {
      Country country = countries.get(countryName);
      continents.add(country.getContinent());
    }
    return new ArrayList<>(continents);
  }

  private int calculateTaxes(List<String> path) {
    int totalTaxes = 0;
    for (int i = 1;
        i < path.size();
        i++) { // Start from the second country to exclude the tax of the starting country
      Country country = countries.get(path.get(i));
      totalTaxes += country.getTax();
    }
    return totalTaxes;
  }
}
