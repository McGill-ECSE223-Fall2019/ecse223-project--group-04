package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.controller.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import java.util.*;
import java.io.*;

/**
 * Class used to encapsulate the step definitions related to SavePosition
 * feature
 * 
 * @author Nicolas Buisson
 * 
 */
public class SavePositionStepDef {

	private QuoridorController QC = new QuoridorController();
	private String expected;
	private String previousContent;
	private String savedContent;

	// *********************************************
	// Save position scenario
	// *********************************************

	/**  
	 * @author Nicolas Buisson
	 */


	@Given ("No file {string} exists in the filesystem")
	public void NoFileExistsInTheFileSystem(String filename) {
		String pathName = filename;

		File file = new File (filename);

		// new File creates a pointer to the file
		//createNewFile actually creates it
		
		if(file.exists()) {
			//if file exists, delete it
			file.delete();
		}
		else 
		{
			//file is not there, do nothing
		}
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@When ("The user initiates to save the game with name {string}")
	public void TheUserInitiatesToSaveTheGameWithName(String filename) throws IOException {
		//Create test game and content , Run saveGame() -- throws exception
		savedContent = QC.overwriteGamePosition(filename);
	}


	/**  
	 * @author Nicolas Buisson
	 */
	@Then ("A file with {string} shall be created in the filesystem")
	public void AFileShallBeCreatedInTheFileSystem(String filename) throws IOException {
		
		String pathName = filename;
		File file = new File(pathName);
		//boolean FileExists = false;
		boolean FileExists = file.exists();

		assertEquals(true, FileExists);
		assertEquals(filename, file.getName());
		//check if file exists
		//check if it has the right name
	}
		

	// *********************************************
	// Save position with existing file name scenario
	// *********************************************

	/**   
	 * @author Nicolas Buisson
	 */
	@Given ("File {string} exists in the filesystem")
	public void FileExistsInTheFileSystem(String filename) {
		//Read previous content from file
		expected = "W: a3" + "\n" + "B: f5";
		//TEST FOR WALLS
		Game testGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Tile blackTile = new Tile(5, 6, QuoridorApplication.getQuoridor().getBoard());
		Tile whiteTile = new Tile(3, 1, QuoridorApplication.getQuoridor().getBoard());
		PlayerPosition aNewBlackPosition = new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), blackTile);
		PlayerPosition aNewWhitePosition = new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(), whiteTile);
		testGame.getCurrentPosition().setBlackPosition(aNewBlackPosition);
		testGame.getCurrentPosition().setWhitePosition(aNewWhitePosition);
		
		String pathName = filename;

		File file = new File(pathName);

		if(file.exists()) {
			//if it exists do nothing
		}
		else 
			//file is not there, create it
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}

	/**   
	 * @author Nicolas Buisson
	 */
	@And ("The user confirms to overwrite existing file")
	public void TheUserConfirmsToOverwriteExistingFile() throws IOException {
		// Run overwriteGame() via UI
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@Then ("File with {string} shall be updated in the filesystem")
	public void FileShallBeUpdatedInTheFileSystem(String filename) {
		//# Read real content, assert that real content = testContent
		String pathName = filename;

		File file = new File(pathName);


		FileReader reader = null;
		try{
			reader = new FileReader(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		BufferedReader BF = new BufferedReader(reader); 
	
		String newContent = "";
		StringBuilder sb = new StringBuilder();
		try {
			String line = BF.readLine();
			while(line != null) {
				sb.append(line);
				sb.append("\n");
				line = BF.readLine();
			}	
			sb.delete(sb.length()-1, sb.length());
			newContent = sb.toString();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals(filename, file.getName());
		assertEquals(newContent, expected);
		// check that file has the right name
		// check that content of the file is updated
	}

	// *********************************************
	// Save position cancelled due to existing file name scenario
	// *********************************************

	/**   
	 * @author Nicolas Buisson
	 */
	@And ("The user cancels to overwrite existing file")
	public void TheUserCancelsToOverwriteExistingFile() {
		QC.cancelOverWriteFile();
		
	}

	/**   
	 * @author Nicolas Buisson
	 */
	@Then ("File {string} shall not be changed in the filesystem")
	public void FileShallNotBeChangedInTheFileSystem(String filename) {
		// # as above, but assert taht real content = previous content
		String pathName = filename;
		
		File file = new File(pathName);

		FileReader reader = null;
		try{
			reader = new FileReader(file);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		BufferedReader BF = new BufferedReader(reader); 
		
		String readContent = "";
		StringBuilder sb = new StringBuilder();
		try {
			String line = BF.readLine();
			while(line != null) {
				sb.append(line);
				sb.append("\n");
				line = BF.readLine();
			}	
			sb.delete(sb.length()-1, sb.length());
			readContent = sb.toString();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		assertEquals(filename, file.getName());
		assertEquals(savedContent, readContent);
		//check that the file has the right name
		//check the file has the same content as before
	}

}
