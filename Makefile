run: compile
	java Main $(ARGS)

compile: Main.class FrontEnd.class Backend.class Character.class

Main.class: Main.java
	javac Main.java

FrontEnd.class: FrontEnd.java
	javac FrontEnd.java

Backend.class: Backend.java BackendInterface.class RedBlackTree.class SortedCollectionInterface.class
	javac Backend.java

BackendInterface.class: BackendInterface.java
	javac BackendInterface.java

RedBlackTree.class: RedBlackTree.java
	javac RedBlackTree.java

SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java

Character.class: Character.java CharacterInterface.class CharacterDataReader.class
	javac Character.java

CharacterInterface.class: CharacterInterface.java
	javac CharacterInterface.java

CharacterDataReader.class: CharacterDataReader.java
	javac CharacterDataReader.java

test: testData testBackend testFrontend

testFrontend: FrontEnd.class Backend.class Character.class enterXToExit.class testFrontendInitialOutput.class testInvalidInput.class testSearchByScore.class testSearchHeroByName.class
	java -jar junit5.jar --class-path . --scan-classpath

enterXToExit.class: enterXToExit.java
	javac -cp .:junit5.jar enterXToExit.java -Xlint

testFrontendInitialOutput.class: testFrontendInitialOutput.java
	javac -cp .:junit5.jar testFrontendInitialOutput.java -Xlint

testInvalidInput.class: testInvalidInput.java
	javac -cp .:junit5.jar testInvalidInput.java -Xlint

testSearchByScore.class: testSearchByScore.java
	javac -cp .:junit5.jar testSearchByScore.java -Xlint

testSearchHeroByName.class: testSearchHeroByName.java
	javac -cp .:junit5.jar testSearchHeroByName.java -Xlint

testBackend: Backend.class Character.class BackEndDeveloperTests.class
	java -jar junit5.jar --class-path . --scan-classpath

BackEndDeveloperTests.class: BackEndDeveloperTests.java
	javac -cp .:junit5.jar BackEndDeveloperTests.java -Xlint

testData: Character.class DataWranglerTests.class
	java -jar junit5.jar --class-path . --scan-classpath

DataWranglerTests.class: DataWranglerTests.java
	javac -cp .:junit5.jar DataWranglerTests.java -Xlint

clean:
	$(RM) *.class
