// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * The Backend creates and fills two red black trees with character objects. The
 * backend interacts with the data through the data wrangler's CharacterDataReader
 * class and Character class. The Backend allows for the interaction with this
 * data through the methods defined in it.
 */
public class Backend implements BackendInterface {

    private CharacterDataReader reader;
    private FileReader fr;
    
    private List<Character> characterList; // array storing all hero objects from data wrangler

    private RedBlackTree<CharacterInterface> heroTree; // red black tree storing all hero characters
    private RedBlackTree<CharacterInterface> villainTree; // red black tree storing all villain characters

    private List<CharacterInterface> selectedCharacters; // a list of characters that have been filtered by the user
    private CharacterInterface foundCharacter; // character
    private List<CharacterInterface> nameSearch; // list of characters found with substring search

    private List<CharacterInterface> roster; // fantasy character team

    private Iterator<CharacterInterface> it_hero;   // iterator through the hero red black tree
    private Iterator<CharacterInterface> it_villain; // iterator through the villain red black tree

    private final int ROSTER_MAX = 5;

    /**
     * This Constructor initializes all the necessary lists and sorts and fills the red
     * black trees with a list of character objects created from the file given as an
     * argument to the constructor.
     * 
     * @param fileName  - the filename/filepath containing the data to be parsed to create the character objects
     * @throws IOException
     * @throws DataFormatException
     */
    public Backend(String fileName) throws IOException, DataFormatException{
        fr = new FileReader(fileName);
        reader = new CharacterDataReader();
        characterList = reader.readData(fr);

        heroTree = new RedBlackTree<CharacterInterface>();
        villainTree = new RedBlackTree<CharacterInterface>();
        selectedCharacters = new ArrayList<CharacterInterface>();
        roster = new ArrayList<CharacterInterface>();

        for(CharacterInterface character : characterList) {
            if(character.getAlignment().equals("good")) {
                heroTree.insert(character);
            } else if (character.getAlignment().equals("bad")) {
                villainTree.insert(character);
            } else {
                heroTree.insert(character);
                villainTree.insert(character);
            }
        }

    }
    
    /**
     * This constructor is used for testing purposes
     */
    public Backend(StringReader sr) throws IOException, DataFormatException {
        reader = new CharacterDataReader();
        characterList = reader.readData(sr);

        heroTree = new RedBlackTree<CharacterInterface>();
        villainTree = new RedBlackTree<CharacterInterface>();
        selectedCharacters = new ArrayList<CharacterInterface>();
        roster = new ArrayList<CharacterInterface>();

        for(CharacterInterface character : characterList) {
            if(character.getAlignment().equals("good")) {
                heroTree.insert(character);
            } else if (character.getAlignment().equals("bad")) {
                villainTree.insert(character);
            } else {
                heroTree.insert(character);
                villainTree.insert(character);
            }
        }

    }

    /**
     * This method searches for a Hero object in a red black tree by a substring of
     * the hero's name and returns a list of hero objects with names containing
     * the substring
     * 
     * @param name - substring to be searched for
     * @return list of characters whose names contain the substring
     */
    @Override
    public List<CharacterInterface> getCharacterByName(String name) {
        nameSearch = new ArrayList<CharacterInterface>();
        it_hero = heroTree.iterator();
        it_villain = villainTree.iterator();
        
        foundCharacter = null; // clear found results from previous search

        if(it_hero.hasNext()) {
            foundCharacter = it_hero.next();
            getNameHelper(name, it_hero);
        }         

        foundCharacter = null; // clear found results from previous search
        
        if(it_villain.hasNext()) {
            foundCharacter = it_villain.next();
            getNameHelper(name, it_villain);
        }

        return nameSearch;
    }

    /**
     * Private recursive helper method that searches using in-order traversal
     * to find character by the substring of their name and then adding it to
     * a list
     * 
     * @param name - substring to be searched for
     * @param it - iterator in current tree
     */
    private void getNameHelper(String name, Iterator<CharacterInterface> it) { 
        if(name == null || it == null) {
            return;
        }
        
        // check if current node matches the character
        if(foundCharacter.getName().toLowerCase().contains(name.toLowerCase())) {
            nameSearch.add(foundCharacter);
            return;
        }
        
        if ( !it.hasNext() ) {
            foundCharacter = null;
            return; 
        }

        // get next character
        foundCharacter = it.next();
        getNameHelper(name,it);
    }

    /**
     * This method searches the red black tree for heros that have a total power
     * level within the range specified by the parameters to this function. The alignment
     * argument chooses the villain tree or hero tree
     * 
     * @param minPower - the lower bound on total power range being filtered
     * @param maxPower - the upper bound on the total power range being filtered
     * @param alignment - which tree to search in (villain/hero)
     */
    @Override
    public void filterCharacters(String minPower, String maxPower, String alignment) {
        int min = Integer.parseInt(minPower);
        int max = Integer.parseInt(maxPower);
        selectedCharacters.clear();

        // check to make sure the requested range is valid
        if(min > max) {
            return;
        }

        if(alignment.equalsIgnoreCase("good")){
            // get new iterator
            it_hero = heroTree.iterator();
            
            if(it_hero.hasNext()) {
                // initialize found character
                foundCharacter = it_hero.next();
                // filter from the hero tree
                filterHelper(min,max,it_hero);
            }
        } else if (alignment.equalsIgnoreCase("bad")) {
            // get new iterator
            it_villain = villainTree.iterator();

            if(it_villain.hasNext()) {
                // initialize found character
                foundCharacter = it_villain.next();
                // filter from the villain tree
                filterHelper(min,max,it_villain);
            }
        }

        
    }

    /**
     * private recursive helper method that searches for characters within the
     * trees using reverse in-order traversal
     * characters are added to a list when found
     * 
     * @param min - lower bound on total power range
     * @param max - upper bound on total power range
     * @param it - iterator in current tree
     */
    private void filterHelper(int min,int max,Iterator<CharacterInterface> it) {
        if(it == null) {
            return;
        }

        // check if current node is in the range
        if(foundCharacter.getTotalPower() >= min && foundCharacter.getTotalPower() <= max) {
            selectedCharacters.add(0, foundCharacter);
        }

        if( !it.hasNext() ) {
            return;
        }
  
        // get next character
        foundCharacter = it.next();
        filterHelper(min,max,it);
        return;
    }

    /**
     * This method gets and returns ten hero objects from the filtered list of heros
     * starting at startingIndex given as a parameter to the function. The heros are
     * returned in decreasing power levels (a hero associated with a smaller index
     * has a higher power level than those with a larger index). If there are less than
     * three heros present in filtered list, then this function returns however many
     * heros are present in the filtered list.
     * 
     * @param startingIndex - the starting index in selectedCharacters to start returning characters
     * @return a list of max 3 (min 0) characters from selectedCharacters
     */
    @Override
    public List<CharacterInterface> getTenCharacters(int startingIndex) {
        List<CharacterInterface> list = new ArrayList<CharacterInterface>();
        for(int i = 0; i < 10; i++) {
            if( (startingIndex + i) < selectedCharacters.size() ) {
                list.add(selectedCharacters.get(startingIndex + i));
            }
        }

        return list;
    }

    /**
     * This method adds the specified character to a fantasy roster
     * 
     * @param characterIndex -  index associated with specific character in 
     *                          selectedCharacters to be added to roster
     * @return true if added, false if not added
     */
    @Override
    public boolean addToRoster(int characterIndex) {
        // check if index is in bounds for the available characters
        if (characterIndex >= selectedCharacters.size()) { 
            return false;
        }

        if (!roster.contains(selectedCharacters.get(characterIndex)) && roster.size() < 5) {
          roster.add(selectedCharacters.get(characterIndex)); 
          return true;
        }
        return false;        
    }

    /**
     * This method removes the requested character from the roster
     * 
     * @param characterIndex -  the index associated with the specific
     *                          character to be removed form the roster
     * @return true when removed, 
     *          false if the index isn't in bounds of the roster
     */
    @Override
    public void removeFromRoster(int characterIndex) {
        if(roster.size() == ROSTER_MAX) {
            return;
        }

        // check if index is in bounds for the available characters
        if(characterIndex >= roster.size()) {
            return;
        }        

        roster.remove(characterIndex);
        return;
    }

    /**
     * This method returns the roster
     */
    @Override
    public List<CharacterInterface> getRoster() {
        return roster;
    }

    /**
     * Returns the size of the filtered list of characters
     */
    @Override
    public int getNumCharacters() {
        return selectedCharacters.size();
    }


    
}
