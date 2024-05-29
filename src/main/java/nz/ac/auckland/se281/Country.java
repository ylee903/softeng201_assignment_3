package nz.ac.auckland.se281;

// Represents a single country with its name, continent, and tax information.
class Country {
  String name;
  String continent;
  int tax;

  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  // Getters and setters
  // get name of the country
  public String getName() {
    return name;
  }

  // get continent of the country
  public String getContinent() {
    return continent;
  }

  // get tax of the country
  public int getTax() {
    return tax;
  }
}
