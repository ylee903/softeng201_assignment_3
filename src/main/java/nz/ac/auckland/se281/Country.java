package nz.ac.auckland.se281;

/** Represents a single country with its name, continent, and tax information. */
/** Represents a country with its name, continent, and tax information. */
class Country {
  String name;
  String continent;
  int tax;

  /**
   * Constructor for the Country class.
   *
   * @param name the name of the country
   * @param continent the continent where the country is located
   * @param tax the tax rate of the country
   */
  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  // Getters and setters

  /**
   * Returns the name of the country.
   *
   * @return the name of the country
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the continent where the country is located.
   *
   * @return the continent of the country
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Returns the tax rate of the country.
   *
   * @return the tax rate of the country
   */
  public int getTax() {
    return tax;
  }
}
