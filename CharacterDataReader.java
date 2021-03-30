// --== CS400 File Header Information ==--
// Name: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Team: Purple
// Group: LB
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.Reader;
import java.util.zip.DataFormatException;
import java.io.BufferedReader;

public class CharacterDataReader {

  List<Character> characters;

  /**
   * This method reads the data from the file and instantiates Character objects accordingly.
   * 
   * @param inputFileReader
   * @return
   * @throws IOException
   * @throws DataFormatException
   */
  public List<Character> readData(Reader inputFileReader) throws IOException, DataFormatException {
    if (inputFileReader == null)
      throw new NullPointerException("Reader is null");
    BufferedReader buffReader = new BufferedReader(inputFileReader);
    String dataLine = "";
    String[] data = null;
    String[] categories = null;
    Character tempCharacter = null;
    List<Character> characterList = new ArrayList<Character>();
    boolean headerFlag = true;
    while ((dataLine = buffReader.readLine()) != null) {
      if (headerFlag) {
        categories = dataLine.split(","); // get String array for header (header is used in
                                          // constructor of Character to get superPowers
        headerFlag = false;
      } else {
        data = dataLine.split(",");
        tempCharacter = new Character(data, categories);
        if (tempCharacter.getTotalPower() > 10)
          characterList.add(tempCharacter);
      }
    }
    buffReader.close();
    return characterList;
  }

}
