import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountriesApp {

	private static Path filePath = Paths.get("country.txt");

	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(System.in);
//		boolean userResponse;

		while (true) {

			int userInput = Validator.getInt(scnr, "For List press 1, to add press 2, to quit press 3.");

			if (userInput == 1) {
				List<Country> countries = readFile();
				for (Country country : countries)
					System.out.println(country.getName() + " with population of " + country.getPopulation());
			} else if (userInput == 2) {
				System.out.print("Enter a Country you wish to add: ");
				String name = scnr.nextLine();
				int population = Validator.getInt(scnr, "Enter the Population Number for that country: ");
				System.out.println();
				Country newCountry = new Country(name, population);
				appendToFile(newCountry);
			} 
			else if (userInput == 3) {
				System.out.println("Goodbye!");
				break;
			}
		}
	}

	private static List<Country> readFile() {
		// ** Example of reading a file into a list
		try {
			List<String> lines = Files.readAllLines(filePath);
			List<Country> countries = new ArrayList<>();
			for (String line : lines) {
				String[] parts = line.split("\t");
				Country c = new Country();
				c.setName(parts[0]);
				c.setPopulation(Integer.parseInt(parts[1]));
				countries.add(c);
			}
			return countries;

		} catch (NoSuchFileException ex) {
			return new ArrayList<>();
		} catch (IOException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static void appendToFile(Country country) throws IOException {
		if (Files.notExists(filePath)) {
			Files.createFile(filePath);
		}

		String line = country.getName() + "\t" + country.getPopulation();

		List<String> linesToAdd = Arrays.asList(line);
		// Write those lines to the end of the file
		Files.write(filePath, linesToAdd, StandardOpenOption.APPEND);
	}

	private static void rewriteFile(List<String> items) throws IOException {
		if (Files.notExists(filePath)) {
			Files.createFile(filePath);
		}

		// ** Example of rewriting a whole file
		Files.write(filePath, items, StandardOpenOption.TRUNCATE_EXISTING);
	}
}