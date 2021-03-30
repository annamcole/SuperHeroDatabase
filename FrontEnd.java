// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>



import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Frontend implementation of Hero Database which gives users the ability to discover 
 * information about their favorite Marvel characters. Participants can assemble an 
 * imaginary team of Superheroes and Supervillains and score points based on those 
 * players' individual stats.
 * 
 */
public class FrontEnd {

  private static final Scanner READIN = new Scanner(System.in);
  private static final String[] ALIGNMENT = {"GOOD", "BAD"};
  private static int FREEPOWER = 1500;
  private static int pageOffset = 0;

  private Backend backend;
  private List<CharacterInterface> roster;
  private List<CharacterInterface> selection;
  private Integer numCharacters = 0;
  private boolean displayTable = false;


  private final String APP_WELCOME = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
      + "\n	WELCOME TO SUPER DATABASE		" + "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
      + "\nThis application gives Marvel fans the ability"
      + "\nto discover their favorite superheroes and " + "\nsupervillains!\n"
      + "\nSearch for Marvel characters by Name and Power"
      + "\nLevel. Add your favorites to your Fantasy team" + "\nand compete against other players.";

  private final String BASE_WELCOME_MESSAGE = "\n___________________________________________" 
      + "\n\n	CHARACTER DISPLAY MODE" + "\n___________________________________________" ;

  private final String BASE_MENU_DISPLAY = "\n<<<<<<  MENU  >>>>>>:" + "\nf - Fantasy League"
      + "\nn - Search By Name" + "\np - Search By Power" + "\nm - Display Menu" + "\nx - Exit";

  private final String NAME_WELCOME_MESSAGE = "\n___________________________________________" 
      + "\n\n\t\tSEARCH BY NAME" + "\n___________________________________________" 
      + "\n\nEnter the first five characters or fullname"
      + "\nof a Marvel character.\n\n";

  private static String POWER_WELCOME_MESSAGE = "\n___________________________________________" 
      + "\n\n\t\tSEARCH BY POWER" + "\n___________________________________________" 
      + "\nSearch for Superheroes or SuperVillains by"
      + "\nTotal Power. Use the results to add characters" + "\nto your Fantasy roster."
      + "\n\n[Enter 'x' to display results]\n";

  private static String FANTASY_WELCOME_MESSAGE = "\n___________________________________________" 
      + "\n\n\t\tFANTASY MODE" + "\n___________________________________________" 
      + "\nAdd your favorite superheroes and supervillains "
      + "\nto compete against other players. Your roster is"
      + "\nlimited to five characters and Total Power: " + FREEPOWER
      + "\n\nEnter the roster No. + 'r' to remove a character " + "\nfrom your roster."
      + "\n\nEXAMPLE: Enter '3r' to remove third member of roster.";


  /**
   * Display frontend Welcome page. Takes an instance of Backend, which stores Marvel character
   * data. The instance of Backend is stored as as a global variable be accessed for the duration of
   * the program.
   * 
   * @param backend an instance of Backend.
   */
  public void run(Backend backend) {
    this.backend = backend;
    // TODO: Create method that returns number of characters that meet range
    // criteria
    System.out.println(APP_WELCOME);
    this.baseDisplayMode();

    return;

  }

  /**
   * The base display mode is the primary location the user interacts with the app The Search by
   * Name, Search by Power, and Fantasy Modes are all accessed from the base display. The user will
   * see selections based on the search parameters in the Search by Power mode. They are then able
   * to scroll through the selection of characters 10 at-a-time, by typing in numbers as commands
   * that represent the index of the returned characters to display.
   */

  public void baseDisplayMode() {
    for (;;) {

      this.baseMenu();
      if (numCharacters != 0 && displayTable) {
        // Print table
        this.baseTable(selection, pageOffset);
      }
      System.out.print("> ");
      try {
        String command = READIN.nextLine().trim().toLowerCase();
        switch (command) {
          case "x":
            // exit application
            READIN.close();
            return;
          case "m":
            // display actions menu
            displayTable = false;
            break;
          case "n":
            // go to search by name mode
            searchByName();
            break;
          case "p":
            // go to search by power mode
            searchByPower();
            pageOffset = 1; // Reset page index
            displayTable = true;
            break;
          case "f":
            // go to fantasy mode
            fantasyMode();
            break;
          default:
            if (isDigit(command)) {
              // Page through search results
              int userInput = Integer.parseInt(command);
              if (userInput <= numCharacters && userInput > 0) {
                // Current offset within the table
                pageOffset = userInput;
                selection = backend.getTenCharacters(userInput - 1);
                break;
              }
              // Add character to fantasy roster
            } else if (isDigitAndCharacter(command)) {
              addToRoster(command);
              break;
            }

            System.out.println("<<<< Invalid Input >>>>");
            System.out.println("[Press any key to continue]");
            READIN.nextLine();

        }

      } catch (Exception e) {
        e.printStackTrace();
      } ;
    }
  }


  /**
   * Returns whether user command is an integer
   * 
   * @param userInput user command line input
   * @return true when the input is any digit, false otherwise
   */
  public boolean isDigit(String userInput) {
    if (userInput.matches("\\d+")) {
      // if (numCharacters == 0) {
      // return false;
      // }
      return true;
    }
    return false;
  }

  /**
   * Returns whether user command is an integer + 'a'.
   * 
   * @param userInput user command line input
   * @return true when input is a digit + 'a', false otherwise
   */
  public boolean isDigitAndCharacter(String userInput) {
    if (userInput.matches("\\d+[a]")) {
      return true;
    }
    return false;
  }

  /**
   * This class implements the functionality to add a character to the user's fantasy roster from
   * the base display.
   * 
   * @param userInput user command line input
   */

  public void addToRoster(String userInput) {
    int index = Integer.parseInt(userInput.replaceAll("[a-z]", "")) - pageOffset;
    int selIndex = Integer.parseInt(userInput.replaceAll("[a-z]", "")) - 1;


    // Check selection to ensure that Available Power is not less than '0'
    int characterPower = selection.get(index).getTotalPower();


    if ((FREEPOWER - characterPower) < 0) {
      System.out.println("Fail! Available Power Exceeded.");
      System.out.println("Available Power:" + FREEPOWER);
      return;
    }

    if (backend.addToRoster(selIndex)) {
      FREEPOWER -= characterPower;
      System.out.println("Success! Added to roster.");

    } else {
      System.out.println("Fail! Roster is full or character is already a member of your roster.");
    }
    System.out.println("[Press any key to continue]");
    READIN.nextLine();

  }

  /**
   * Base display when the user enters the application and when no characters have been
   * selected/returned in a search.
   * 
   */
  public void baseMenu() {
    numCharacters = backend.getNumCharacters();

    System.out.println(BASE_WELCOME_MESSAGE);
    System.out.print(BASE_MENU_DISPLAY);

    System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + "\nFilter Results: "
        + numCharacters + " characters\n");
  }


  /**
   * Formatted command line table of character data if character's have been selected in Search by
   * Power mode.
   * 
   * @param selection list of characters matching search parameters
   * @param index     page offset for list of ten characters
   */

  private void baseTable(List<CharacterInterface> selection, int index) {
    int rangeStart = index;
    int rangeEnd = index + selection.size() - 1;
    selection = backend.getTenCharacters(index - 1);
    CommandLineTable table = toTable(selection, rangeStart);

    System.out.printf("\nCurrently Viewing %3d - %3d\n", rangeStart, rangeEnd);
    // Output table results
    table.print();
  }

  // private static boolean validateCommand(String command) {
  // for (String s : COMMANDS) {
  // if (command.equals(s))
  // return true;
  // }
  // return false;
  // }

  /**
   * Prompts a user to enter a full character name or partial name.
   * Returns a formatted string of character stats if Marvel character
   * csv contains a character name that matches the string or substring.
   * 
   */
  public void searchByName() {
    String userInput = "";
    List<CharacterInterface> result;
    String display = "\n[Press 'x' to exit]";

    System.out.println(NAME_WELCOME_MESSAGE);

    boolean exit = userInput.equals("x") ? true : false;

    do {
      System.out.print("Enter a name: ");
      userInput = READIN.nextLine().toLowerCase().trim();

      // Try input
      while (userInput.length() < 5) {
        System.out.println("Invalid number of characters! Please try again.");
        System.out.print("Enter a name: ");
        userInput = READIN.nextLine().trim().toLowerCase();
      }

      result = backend.getCharacterByName(userInput);

      if (result.size() == 0)
        System.out.println("No match found.");
      else
        for (CharacterInterface r : result) {
          System.out.println(personToString(r));
        }

      System.out.println(display);
      System.out.print("> ");
      userInput = READIN.nextLine().trim().toLowerCase();
      exit = userInput.equals("x") ? true : false;
    } while (!exit);

    if (exit)
      return;
  }

  /**
   * Gives user the capability to search csv dataset of Marvel characters 
   * by Total Power and allegiance. If a character or set of characters 
   * matches the search criteria they are stored to be viewed in character 
   * display mode.
   * 
   */
  public void searchByPower() {
    String command = "";
    Integer min = 0;
    Integer max = 0;

    System.out.println(POWER_WELCOME_MESSAGE);
    boolean exit = command.equals("x") ? true : false;

    do {
      System.out.print("\nGOOD or BAD? ");
      String party = READIN.nextLine().trim().toLowerCase();
      boolean valid = validParty(party);

      while (!valid) {
        System.out.println("Invalid selection.");
        System.out.print("GOOD or BAD? ");
        party = READIN.nextLine().trim().toLowerCase();
        valid = validParty(party);
      }
      try {
        System.out.print("Enter minimum power level: ");
        while (!READIN.hasNextInt()) {
          System.out.print("Enter minimum power level: ");
          READIN.nextLine();
        }
        min = Integer.parseInt(READIN.nextLine());
        if (min < 0) {
          throw new IllegalArgumentException("ERROR: Value must be non-negative.");
        }

        System.out.print("Enter maximum power level: ");
        while (!READIN.hasNextInt()) {
          System.out.print("Enter maximum power level: ");
          READIN.nextLine();
        }
        max = Integer.parseInt(READIN.nextLine());

        if (max < min) {
          throw new IllegalArgumentException("ERROR: Maximum must be greater than minimum.");
        }

      } catch (IllegalArgumentException throwObj) {
        System.out.println(throwObj.getMessage());
        this.searchByPower();
      }
    
      backend.filterCharacters(min.toString(), max.toString(), party);
      numCharacters = backend.getNumCharacters();
      selection = backend.getTenCharacters(0);
     
      System.out.println("\nx - Save & Exit");
      
      System.out.print("> ");
      command = READIN.nextLine().trim().toLowerCase();
      exit = command.equals("x") ? true : false;

    } while (!exit);

    if (exit)
      return;
  }

  // Checks that the command from the user is a valid allegiance
  private static boolean validParty(String userInput) {
    for (String s : ALIGNMENT) {
      if (userInput.equalsIgnoreCase(s))
        return true;
    }
    return false;

  }

  /**
   * Allows a user to create a fantasy roster for a game in which participants act as a general
   * manager of a team of Marvel characters. Members are selected from the Character display mode.
   * They participant is limited to five Marvel characters and a Total Power threshold. Participants
   * can remove a member from their roster by entering the number corresponding to the character's
   * roster position + 'r'.
   * 
   */

  public void fantasyMode() {
    // TODO: Make class field usedPower and freePower
    System.out.println(FANTASY_WELCOME_MESSAGE);
    roster = backend.getRoster();
    fantasyModeDisplay();

    String command = "";


    for (;;) {
      System.out.print("> ");
      command = READIN.nextLine().trim().toLowerCase();
      switch (command) {
        case "x":
          // exit application
          return;
        case "m":
          // display actions menu
          System.out.println(FANTASY_WELCOME_MESSAGE);
          break;
        default:
          if (command.matches("\\d+[r]")) {
            int index = Integer.parseInt(command.substring(0, 1));
            // Remove character from roster and update Available Power
            FREEPOWER += roster.get(index - 1).getTotalPower();
            roster.remove(index - 1);
          }
          System.out.println("Please enter a valid input");
      }
      fantasyModeDisplay();
    }
  }

  /**
   * Formatted display for the user's fantasy roster. Displays power stats and Available Power for
   * adding additional characters.
   * 
   */
  private void fantasyModeDisplay() {
    int usedPower = 0;
    String display = "m - Display Menu" + "\nx - Save & Exit";

    if (roster != null) {
      CommandLineTable table = toTable(roster, 1);
      for (CharacterInterface character : roster) {
        usedPower += character.getTotalPower();
      }
      System.out.println();
      table.print();
    }

    System.out.println();
    System.out.println("***************************************************");
    System.out.printf("Total used power = %-5d\n", usedPower);
    System.out.printf("Total available power = %-5d\n", FREEPOWER);
    System.out.println("***************************************************");
    System.out.println(display);

    return;

  }

  /**
   * Returns a formatted string of character stats
   * 
   * @param person a Marvel character
   * @return formatted string containing character name and stats
   */

  private String personToString(CharacterInterface person) {
    String name = person.getName();
    String allegiance = person.getAlignment();
    int intelligence = person.getIntelligence();
    int strength = person.getStrength();
    int speed = person.getSpeed();
    int durability = person.getDurability();
    int power = person.getPower();
    int combat = person.getCombat();
    int totalpwrlevel = person.getTotalPower();

    String stats = "\nNAME:\t" + name + "\nALLEGIANCE:\t" + allegiance + "\nSTRENGTH\t" + strength
        + "\nINTELLIGENCE:\t" + intelligence + "\nSPEED:\t" + speed + "\nDURABILITY:\t" + durability
        + "\nPOWER:\t" + power + "\nCOMBAT:\t" + combat + "\nTOTAL POWER LEVEL:\t" + totalpwrlevel;

    String powers = "\nPOWERS:\t" + person.getSuperPowers().toString();

    return stats + powers;
  }

  /**
   * Returns a formatted table with character information for at most ten characters.
   * 
   * @param listOfCharacters list of characters to be printed to stdout
   * @param index            the index of the selected character to be printed
   * @return a formatted command line table
   */
  private CommandLineTable toTable(List<CharacterInterface> listOfCharacters, int index) {
    CommandLineTable table = new CommandLineTable();
    String[] categories = new String[] {"No.", "Name", "Affiliation", "Intelligence", "Strength",
        "Speed", "Durability", "Power", "Combat", "Total Power"};

    table.setHeader(categories);

    for (CharacterInterface person : listOfCharacters) {
      String relIndex = Integer.toString(index++);
      String name = person.getName();
      String affiliation = person.getAlignment();
      String intelligence = person.getIntelligence().toString();
      String strength = person.getStrength().toString();
      String speed = person.getSpeed().toString();
      String durability = person.getDurability().toString();
      String power = person.getPower().toString();
      String combat = person.getCombat().toString();
      String totalPower = person.getTotalPower().toString();

      table.addRow(relIndex, name, affiliation, intelligence, strength, speed, durability, power,
          combat, totalPower);
    }

    return table;
  }

  /**
   * Creates a generic table to display from the command line.
   * 
   */

  class CommandLineTable {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private List<String[]> rows = new ArrayList<>();

    /**
     * Default constructor
     */
    public CommandLineTable() {
      verticalSep = "|";
      joinSep = "+";
    }

    /**
     * Store the table headers.
     * 
     * @param headers any object of type String
     */

    public void setHeader(String... headers) {
      this.headers = headers;
    }

    /**
     * Add a row to the table
     * 
     * @param cells any object of type String
     */
    public void addRow(String... cells) {
      rows.add(cells);
    }

    /**
     * Prints table to standard out
     * 
     */
    public void print() {
      int[] maxWidth = Arrays.stream(headers).mapToInt(str -> str.length()).toArray();

      for (String[] cells : rows) {
        if (maxWidth == null) {
          maxWidth = new int[cells.length];
        }

        for (int i = 0; i < cells.length; i++) {
          maxWidth[i] = Math.max(maxWidth[i], cells[i].length());
        }
      }

      if (headers != null) {
        printLine(maxWidth);
        printRow(headers, maxWidth);
        printLine(maxWidth);
      }

      for (String[] cells : rows) {
        printRow(cells, maxWidth);
      }

      if (headers != null) {
        printLine(maxWidth);
      }
    }

    /**
     * Prints table borders
     * 
     * @param columnWidths array of column widths
     */

    private void printLine(int[] columnWidths) {
      for (int i = 0; i < columnWidths.length; i++) {
        String line = String.join("",
            Collections.nCopies(columnWidths[i] + verticalSep.length() + 1, HORIZONTAL_SEP));
        System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
      }
      System.out.println();
    }

    /**
     * Prints a row of the table.
     * 
     * @param cells    an array of row data
     * @param maxWidth width of the largest element in each column of the table
     */

    private void printRow(String[] cells, int[] maxWidth) {
      for (int i = 0; i < cells.length; i++) {
        String s = cells[i];
        String verStrTemp = i == cells.length - 1 ? verticalSep : "";
        System.out.printf("%s %-" + maxWidth[i] + "s %s", verticalSep, s, verStrTemp);
      }

      System.out.println();
    }

  }



}
