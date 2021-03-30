// --== CS400 File Header Information ==--
// Name: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Team: Purple
// Group: LB
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.List;

public interface CharacterInterface extends Comparable<CharacterInterface> {
  
  public String getName();
  public Integer getTotalPower();
  public Integer getIntelligence();
  public Integer getStrength();
  public Integer getSpeed();
  public Integer getDurability();
  public Integer getPower();
  public Integer getCombat();
  public String getAlignment();
  public List<String> getSuperPowers();
  
  // from super interface Comparable
  public int compareTo(CharacterInterface otherCharacter);

}