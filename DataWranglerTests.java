// --== CS400 File Header Information ==--
// Name: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Team: Purple
// Group: LB
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import org.junit.Before;
import org.junit.Test;

public class DataWranglerTests {

  @Before
  public void setUp() {

  }

  /**
   * This test checks that CharacterDataReader returns a List of Character objects correctly
   * corresponding to a given CSV file.
   */
  @Test
  public void testCharacterDataReader() {
    FileReader inputFileReader = null;
    try {
      inputFileReader = new FileReader("characters_test.csv");
    } catch (FileNotFoundException e1) {
      fail("FileNotFoundException");
    }
    CharacterDataReader reader = new CharacterDataReader();
    try {
      List<Character> result = reader.readData(inputFileReader);
      // //code for printing out result list - uncomment to see what the list contains if test fails
      // for (int i = 0; i < result.size(); i++) {
      // System.out.println(result.get(i).getName() + "\t\t" + result.get(i).getAlignment() + "\t\t"
      // + result.get(i).getTotalPower() + "\t\t");
      // }
      assertEquals(4, result.size());
      assertEquals("3-D Man", result.get(0).getName());
      assertEquals("Banshee", result.get(1).getName());
      assertEquals("Catwoman", result.get(2).getName());
      assertEquals("Deadpool", result.get(3).getName());
      assertEquals(("Agility"), result.get(3).getSuperPowers().get(0));
      assertEquals(0, result.get(1).getSuperPowers().size());
    } catch (IOException e) {
      fail("IOException");
    } catch (DataFormatException e) {
      fail("DataFormatException");
    }
  }

  /**
   * This test checks that the Character object can instantiate with null.
   */
  @Test
  public void testCharacterObjectNull() {
    Character char1 = new Character(null, null);
    assertEquals(null, char1.getName());
  }

  /**
   * This test checks that the Character object correctly instantiates and stores data in its
   * variables, and that the Character object’s accessor methods return the expected values
   */
  @Test
  public void testCharacterObject() {
    Character char1 = new Character(
      new String[] {"3-D Man", "good", "50", "31", "43", "32", "25", "52", "233", "TRUE", "FALSE"},
      new String[] {"", "", "", "", "", "", "", "", "", "6-D Vision", "20/20 Vision"});
    assertEquals("3-D Man", char1.getName());
    assertEquals("good", char1.getAlignment());
    assertEquals(50, (int) char1.getIntelligence());
    assertEquals(31, (int) char1.getStrength());
    assertEquals(43, (int) char1.getSpeed());
    assertEquals(32, (int) char1.getDurability());
    assertEquals(25, (int) char1.getPower());
    assertEquals(52, (int) char1.getCombat());
    assertEquals(233, (int) char1.getTotalPower());
    assertEquals(("6-D Vision"), char1.getSuperPowers().get(0));
    assertEquals(1, char1.getSuperPowers().size());
  }

  /**
   * This test checks that Character’s compareTo() method is correctly comparing whether or not a
   * Character is greater or less than another Character based on total power.
   */
  @Test
  public void testCharacterCompareTo() {
    Character char1 = new Character(
      new String[] {"Banshee", "good", "50", "10", "58", "42", "60", "70", "290"}, new String[] {});
    Character char2 =
      new Character(new String[] {"Catwoman", "good", "63", "13", "33", "28", "24", "85", "246"},
        new String[] {});
    Character char3 = new Character(
      new String[] {"Bullseye", "bad", "50", "11", "25", "70", "20", "70", "246"}, new String[] {});
    assertTrue(char1.compareTo(char2) == 1);
    assertTrue(char2.compareTo(char1) == -1);
    assertTrue(char3.compareTo(char2) == 0);
  }

  /**
   * This test checks if the Character list returned from the readData() method is correct when
   * reading from the full character_stats.csv file.
   */
  @Test
  public void testCharacterDataReaderFull() {
    CharacterDataReader reader = new CharacterDataReader();
    try {
      FileReader inputFileReader = new FileReader("characters_stats.csv");
      List<Character> result = reader.readData(inputFileReader);
      // //code for printing out result list - uncomment to see what the list contains if test fails
      // for (int i = 0; i < 20; i++) {
      // System.out.println(result.get(i).getName() + "\t\t" + result.get(i).getAlignment() + "\t\t"
      // + result.get(i).getTotalPower() + "\t\t" + result.get(i).getSuperPowers().get(0));
      // }
      assertEquals(124, result.size()); // total # of characters in .csv = 727,
                                        // total # of characters w/ totalPower > 10 = 124
    } catch (IOException e) {
    } catch (DataFormatException e2) {
    }
  }

}
