package nz.ac.auckland.se281;

public class CountryNotFoundException extends RuntimeException {
  public CountryNotFoundException(String message) {
    super(message);
  }
}
