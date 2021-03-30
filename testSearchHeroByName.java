// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

class testSearchHeroByName {

  /**
   * This test runs select-by-name mode. A valid character name is input before returning to base
   * mode. The character and its stats are returned on the screen. The test succeeds if the
   * selection screen contains the selection from the data. It fails if none or the wrong selections
   * are present, the front end has not been initialized.
   *
   * @return true if the test passed, false if it failed.
   *
   */

  @Test
  public void testSearchHero() {
    PrintStream standardOut2 = System.out;
    InputStream standardIn2 = System.in;
    try {

      String input = "n" + System.lineSeparator() + "General Zod" + System.lineSeparator() + "x"
          + System.lineSeparator() + "x" + System.lineSeparator();
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));
      // instantiate when front end is implemented

      FrontEnd frontend = new FrontEnd();
      Backend backend = new Backend(new StringReader(
          "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
              + "Martian Manhunter,good,100,100,96,100,100,85,581\n"
              + "Match,bad,75,95,83,85,90,70,498\n" + "General Zod,bad,94,100,96,100,94,95,579\n"
              + "Silver Surfer,good,63,100,84,1010,100,32,480"));
      frontend.run(backend);

      System.setOut(standardOut2);
      // same for standard in
      System.setIn(standardIn2);
      // add all tests to this method
      String appOutput = outputStreamCaptor.toString();

      assertTrue(appOutput.contains("General Zod"));
      inputStreamSimulator = null;

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut2);
      System.setIn(standardIn2);
      e.printStackTrace();

    }
  }
}
