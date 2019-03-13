package fr.excilys.rmomprive.ui.console.menu;

import java.util.Optional;

import org.springframework.dao.DataAccessException;

import fr.excilys.rmomprive.exception.ValidationException;
import fr.excilys.rmomprive.model.Computer;

public class MenuCreateComputer extends MenuComputerForm {
  private static MenuCreateComputer instance;

  /**
   * Private constructor for singleton.
   */
  private MenuCreateComputer() {

  }

  @Override
  public void show() {
    // Asks the user to fill a computer
    Computer computer = form();

    // Insert the computer into the database
    try {
      Optional<Computer> createdComputer = this.computerService.add(computer);

      // If the computer was inserted successfully
      if (createdComputer.isPresent()) {
        getLogger().info("Successfullly added {}\n", createdComputer.get());
      } else { // Else, an error happened
        getLogger().error("Error creating {}\n", computer);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return The instance of MenuCreateComputer in memory
   */
  public static MenuCreateComputer getInstance() {
    if (instance == null) {
      instance = new MenuCreateComputer();
    }

    return instance;
  }
}
