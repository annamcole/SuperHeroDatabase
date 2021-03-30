// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;

public interface BackendInterface {
    public int getNumCharacters();
    public List<CharacterInterface> getCharacterByName(String name);
    public void filterCharacters(String minPower, String maxPower, String alignment);
    public List<CharacterInterface> getTenCharacters(int startingIndex);
    public boolean addToRoster(int characterIndex);
    public void removeFromRoster(int characterIndex);
    public List<CharacterInterface> getRoster();
}