IntegrationManager README for Project Two (CS400 @ UW Madison)
==============================================================

Name of IntegrationManager: Akshat Bansal
@wisc.edu Email of IntegrationManager: akshat.bansal@wisc.edu
Group: KB
Team: Red

Complete List of Files:
----------------------
Frontend Files: Frontend Developer - Elliot Weinshenker
	Main.java
	Frontend.java
	enterXToExit.java
	testFrontendInitialOutput.java
	testInvalidInput.java
	testSearchByScore.java
	testSearchHeroByName.java
	FrontEndDeveloper_README.txt

Backend Files: Backend Developer - Anna Stephan
	Backend.java
	BackendInterface.java
	RedBlackTree.java
	BackEndDeveloperTests.java
	BackEndDeveloper_README.txt

Data Wrangler Files: Data Wrangler - Huong Nguyen
	Character.java
	CharacterInterface.java
	CharacterDataReader.java
	DataWranglerTests.java
	DataWrangler_README.txt

Integration Manager Files: Integration Manager - Akshat Bansal
	Makefile
	IntegrationManager_README.txt

Other files needed to run program:
	characters_stats.csv - Houses the data for the
			       program.
	characters_test.csv - Used in the Data Wrangler tests.
	junit5.jar - Used for all tests.

Instructions to Build, Run and Test your Project:
-------------------------------------------------
Building the program:
	To build the Superhero Database program, you will need
	to enter the following command: 
		make run ARGS=characters_stats.csv
	This will show you the welcome screen of the program

Running the program:
	After the welcome screen has been shown, proceed to
	read the intructions and enter in the command
	corresponding to the action you choose to carry
	out.

Testing the program:
	To test the Superhero DataBase program, you will need
	to type in the following command:
		make test
	This command will run the test cases for all three
	parts of the program, namely, frontend, backend, and
	data wrangler parts.

Team Member Contributions:
--------------------------
All team members were focused and completed their portion of the project in time.
All of my team members namely, Elliot, Anna, and Huong, were available when needed to
help with the integration. I, Akshat Bansal, also implemented some changes to the code
that helped ensure that the program ran smoothly.

Signature:
-------------
Akshat Bansal
