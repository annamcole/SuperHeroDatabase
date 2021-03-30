// --== CS400 File Header Information ==--
// Name: Anna Stephan
// Email: amstephan@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.zip.DataFormatException;
import java.io.IOException;
import java.io.StringReader;

/**
 * This class tests the Backend class that implements the BackendInterface
 */
public class BackEndDeveloperTests {
	
	private Backend backend;


	/**
	 * This test creates a backend object and checks to make sure
	 * that the backend is not null.
	 */
	@Test
	public void testCreateBackend() {
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,bad,63,81,28,28,100,70,370\n"));
			assertNotNull(backend);
		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This test creates a backend object and checks to make sure
	 * that the backend correctly does not update original hero
	 * object with duplicate hero object information.
	 */
	@Test
	public void testInsertHero_Dulicates() {
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,bad,63,81,28,28,100,70,370\n"
											 + "Exodus I,bad,0,0,0,0,0,0,370\n"));

		backend.filterCharacters("300","400","villain");
		List<CharacterInterface> doubleCharacters = backend.getTenCharacters(0);
		 
		for(CharacterInterface character : doubleCharacters) {
			assertTrue(character.getTotalPower() == 370);
		}
		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	/**
	 * This test creates a backend object and checks if the
	 * correct character object is returned when searched for
	 * using the character's name.
	 * This method searches for both bad and good characters.
	 */
	@Test
	public void testFindHero_Name() {

		List<CharacterInterface> heros;
		List<CharacterInterface> villains;
		CharacterInterface hero;
		CharacterInterface villain;

		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,bad,63,81,28,28,100,70,370\n"));

			heros = backend.getCharacterByName("aini");
			hero = heros.get(0);

			assertEquals("Brainiac 5",hero.getName());
			assertEquals("good",hero.getAlignment());
			assertTrue(100 == hero.getIntelligence());
			assertTrue(10 == hero.getStrength());
			assertTrue(23 == hero.getSpeed());
			assertTrue(28 == hero.getDurability());
			assertTrue(60 == hero.getPower());
			assertTrue(32 == hero.getCombat());
			assertTrue(253 == hero.getTotalPower());

			villains = backend.getCharacterByName("Exodus");
			assertEquals(1, villains.size());
			//hero = heros.get(1);

			//assertEquals("Exodus",hero.getName());
			//assertEquals("bad",hero.getAlignment());
			//assertTrue(63 == hero.getIntelligence());
			//assertTrue(81 == hero.getStrength());
			//assertTrue(28 == hero.getSpeed());
			//assertTrue(28== hero.getDurability());
			//assertTrue(100 == hero.getPower());
			//assertTrue(70 == hero.getCombat());
			//assertTrue(370 == hero.getTotalPower());

		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	/**
	 * This test creates a backend object and checks if the
	 * correct hero objects are returned when searched for
	 * using a range of total power levels.
	 */
	@Test 
	public void testFindHero_PowerRange() {

		List<CharacterInterface> heros;

		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,bad,63,81,28,28,100,70,370\n"));

		backend.filterCharacters("360","400","bad");
		heros = backend.getTenCharacters(0);
		
		assertTrue(heros.size() == 1);

		CharacterInterface hero2 = heros.get(0);
		
		assertEquals("Exodus",hero2.getName());
		assertTrue(63 == hero2.getIntelligence());
		assertTrue(81 == hero2.getStrength());
		assertTrue(28 == hero2.getSpeed());
		assertTrue(28 == hero2.getDurability());
		assertTrue(100 == hero2.getPower());
		assertTrue(70 == hero2.getCombat());
		assertTrue(370 == hero2.getTotalPower());

		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This test creates a backend object and checks if the
	 * correct hero objects are returned in descending order when
	 * requested using the getThreeHeros() function of the
	 * Backend class.
	 */
	@Test
	public void testThreeHeros() {
		List<CharacterInterface> heros;
		
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,good,63,81,28,28,100,70,370\n"));

			backend.filterCharacters("0","1000","good");
			heros = backend.getTenCharacters(0);
	
			CharacterInterface hero0 = heros.get(2);
			assertEquals("3-D Man",hero0.getName());
			assertTrue(50 == hero0.getIntelligence());
			assertTrue(31 == hero0.getStrength());
			assertTrue(43 == hero0.getSpeed());
			assertTrue(32 == hero0.getDurability());
			assertTrue(25 == hero0.getPower());
			assertTrue(52 == hero0.getCombat());
			assertTrue(233 == hero0.getTotalPower());
	
			CharacterInterface hero1 = heros.get(1);
			assertEquals("Brainiac 5",hero1.getName());
			assertTrue(100 == hero1.getIntelligence());
			assertTrue(10 == hero1.getStrength());
			assertTrue(23 == hero1.getSpeed());
			assertTrue(28 == hero1.getDurability());
			assertTrue(60 == hero1.getPower());
			assertTrue(32 == hero1.getCombat());
			assertTrue(253 == hero1.getTotalPower());
	
			CharacterInterface hero2 = heros.get(0);
			assertEquals("Exodus",hero2.getName());
			assertTrue(63 == hero2.getIntelligence());
			assertTrue(81 == hero2.getStrength());
			assertTrue(28 == hero2.getSpeed());
			assertTrue(28 == hero2.getDurability());
			assertTrue(100 == hero2.getPower());
			assertTrue(70 == hero2.getCombat());
			assertTrue(370 == hero2.getTotalPower());
		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
		
	}

	/**
	 * This method tests if the backend.getCharacterByName() method fails correctly
	 * when the name searched for doesn't exist in the trees;
	 */
	@Test
	public void testGetName_Nonexistent() {
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,good,63,81,28,28,100,70,370\n"));
		
			List<CharacterInterface> characters = backend.getCharacterByName("Sal");

			assertEquals(true, characters.isEmpty());

		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	/**
	 * This method tests if the backend.filterCharacters() method correclty
	 * filters with incorrect input
	 */
	@Test
	public void testFilter_badInput() {
		List<CharacterInterface> list;

		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,good,63,81,28,28,100,70,370\n"));
		
			backend.filterCharacters("300","200","good");

			list = backend.getTenCharacters(0);

			assertTrue(list.size() == 0);

			backend.filterCharacters("300","200","bad");

			list = backend.getTenCharacters(0);

			assertTrue(list.size() == 0);

		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}

	/**
	 * This method tests the filter method to see if it correctly filters
	 * when given a range of 1
	 */
	@Test
	public void testFilter_singleRange() {
		List<CharacterInterface> heros;
		
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,233\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,253\n"
			                                 + "Exodus,good,63,81,28,28,100,70,370\n"));

			backend.filterCharacters("253","253","good");
			heros = backend.getTenCharacters(0);

			assertTrue(heros.size() == 1);

			CharacterInterface hero1 = heros.get(0);
			assertEquals("Brainiac 5",hero1.getName());
			assertTrue(100 == hero1.getIntelligence());
			assertTrue(10 == hero1.getStrength());
			assertTrue(23 == hero1.getSpeed());
			assertTrue(28 == hero1.getDurability());
			assertTrue(60 == hero1.getPower());
			assertTrue(32 == hero1.getCombat());
			assertTrue(253 == hero1.getTotalPower());
			
		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

	/**
	 * This method tests if the getThreeMovies method correctly returns the 
	 * correct movie corresponding to the correct index when more than three
	 * movies are present in the filtered list
	 */
	@Test
	public void testGetThreeMovies_MoreMovies() {
		List<CharacterInterface> heros;
		
		try {
			backend = new Backend(new StringReader(
			                                 "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
			                                 + "3-D Man,good,50,31,43,32,25,52,100\n"
			                                 + "Brainiac 5,good,100,10,23,28,60,32,200\n"
			                                 + "Exodus,good,63,81,28,28,100,70,300\n"
											 + "Sal,good,50,31,44,32,25,52,400\n"
			                                 + "Bigfoot,good,100,10,23,28,60,32,500\n"
			                                 + "Santa,good,100,100,100,100,100,100,600\n"));

			backend.filterCharacters("0","600","good");
			heros = backend.getTenCharacters(3);

			assertEquals("Exodus",heros.get(0).getName());
			assertEquals("Brainiac 5",heros.get(1).getName());
			assertEquals("3-D Man",heros.get(2).getName());
			
		} catch (IOException e) {
			assertFalse(true);
			e.printStackTrace();
		} catch (DataFormatException e) {
			assertFalse(true);
			e.printStackTrace();
		}
	}

    /**
     * This method tests the balancing method.
     */
    @Test
    public void testTreeBalancing() {
        
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
                
        tree.insert(31);
        assertTrue(tree.root.data == 31);
        assertNull(tree.root.leftChild);
        assertNull(tree.root.rightChild);
        
        tree.insert(17);
        tree.insert(59);
        tree.insert(42);
        tree.insert(36);
        
        assertEquals("[31, 17, 42, 36, 59]",tree.root.toString());  
    }

	/**
	 * This method tests that the red black tree correctly keeps duplicate values.
	 */
	@Test
	public void testDuplicateValues() {

		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();

		tree.insert(37);
		tree.insert(17);
		tree.insert(42);

		try { 
			tree.insert(37);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}

		assertEquals("[37, 17, 42]",tree.root.toString());
	}

}
