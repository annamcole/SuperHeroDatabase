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

// --== CS400 File Header Information ==--
// Name: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Team: Purple
// Group: LB
// TA: Keren Chen
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

public class Character implements CharacterInterface {

  private String name;
  private String alignment;
  private Integer intelligence;
  private Integer strength;
  private Integer speed;
  private Integer durability;
  private Integer power;
  private Integer combat;
  private Integer totalPower;
  private List<String> superPowers = new ArrayList<String>();

  /**
   * Constructor for Character object.
   * 
   * @param data       String array of information for Character
   * @param categories String array of the header of CSV file, used for getting list of superPowers.
   *                   If categories is empty or null, no list of superPowers will be generated
   *                   (Character object will still be created with main data).
   */
  public Character(String[] data, String[] categories) {
    // if data is null, Character object is initialized with all null fields
    if (data == null || data.length == 0)
      return;
    this.name = data[0];
    this.alignment = data[1];
    this.intelligence = Integer.parseInt(data[2]);
    this.strength = Integer.parseInt(data[3]);
    this.speed = Integer.parseInt(data[4]);
    this.durability = Integer.parseInt(data[5]);
    this.power = Integer.parseInt(data[6]);
    this.combat = Integer.parseInt(data[7]);
    this.totalPower = Integer.parseInt(data[8]);
    // get superPowers by parsing whether or not that superPower is true for that Character
    if (categories == null || categories.length == 9)
      return;
    for (int i = 9; i < data.length; i++) {
      if (Boolean.parseBoolean(data[i])) {
        superPowers.add(categories[i]);
      }
    }
  }

  /**
   * This method returns the Character's name.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * This method returns the Character's total power.
   */
  @Override
  public Integer getTotalPower() {
    return totalPower;
  }

  /**
   * This method returns the Character's intelligence.
   */
  @Override
  public Integer getIntelligence() {
    return intelligence;
  }

  /**
   * This method returns the Character's strength.
   */
  @Override
  public Integer getStrength() {
    return strength;
  }

  /**
   * This method returns the Character's speed.
   */
  @Override
  public Integer getSpeed() {
    return speed;
  }

  /**
   * This method returns the Character's durability.
   */
  @Override
  public Integer getDurability() {
    return durability;
  }

  /**
   * This method returns the Character's power.
   */
  @Override
  public Integer getPower() {
    return power;
  }

  /**
   * This method returns the Character's combat.
   */
  @Override
  public Integer getCombat() {
    return combat;
  }

  /**
   * This method returns the Character's alignment.
   */
  @Override
  public String getAlignment() {
    return alignment;
  }

  /**
   * This method compares Character objects by their totalPower. It returns 1 if the current
   * Character's totalPower is greater than the otherCharacter, -1 if it's less than, and 0 if both
   * Character's have equal totalPower.
   */
  @Override
  public int compareTo(CharacterInterface otherCharacter) {
    if (this.getTotalPower() > otherCharacter.getTotalPower())
      return 1;
    else if (this.getTotalPower() < otherCharacter.getTotalPower())
      return -1;
    else
      return 0;
  }

  /**
   * This method compares the Character objects by their name. It returns true if the Characters
   * have the same name, and therefore are equal, and false if not. Override annotation is not
   * included - method is used through polymorphism when it is called (in CharacterDataReader.java)
   */
  public boolean equals(CharacterInterface otherCharacter) {
    if (this.getTotalPower() == otherCharacter.getTotalPower()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method returns the Character's super powers.
   */
  @Override
  public List<String> getSuperPowers() {
    return this.superPowers;
  }

}
