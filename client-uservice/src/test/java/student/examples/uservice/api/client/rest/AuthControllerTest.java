package student.examples.uservice.api.client.rest;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@SpringBootTest
public class AuthControllerTest {
	
	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	
	private static List<String> errorsText;
	
	@BeforeAll
	public static void setUp() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
	    validator = validatorFactory.getValidator();
	    errorsText = new ArrayList<>();
	    
//	    getDataFromRemoteRepo();
	}
	

	@ParameterizedTest
	@CsvFileSource(resources = "/data/valid_user_test_data.csv")
	public void validUserDataTest(String userName, String email, String password, String passwordConfirmation) {
	    
		UserSignupRequest userDto = new UserSignupRequest();
		userDto.setUserName(userName);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setPasswordConfirmation(passwordConfirmation);
		
		Set<ConstraintViolation<UserSignupRequest>> violations = validator.validate(userDto);
		
		boolean errorsSetIsEmpty = violations.isEmpty();
		if (!errorsSetIsEmpty) {
			aggregateErrors(userDto, violations);
		}
		
        Assertions.assertTrue(errorsSetIsEmpty);
		
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/data/invalid_user_test_data.csv")
	public void invalidUserDataTest(String userName, String email, String password, String passwordConfirmation) {
	    
		UserSignupRequest userDto = new UserSignupRequest();
		userDto.setUserName(userName);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setPasswordConfirmation(passwordConfirmation);
		
		Set<ConstraintViolation<UserSignupRequest>> violations = validator.validate(userDto);
		
        Assertions.assertFalse(violations.isEmpty());
		
	}
	
	/**
	 * Prepare error text string and add it to the collection
	 * 
	 */
	private void aggregateErrors(UserSignupRequest userDto, Set<ConstraintViolation<UserSignupRequest>> violations) {
		
		StringJoiner joiner = new StringJoiner(",  ");
		joiner.add(userDto.toString());
		
		Iterator<ConstraintViolation<UserSignupRequest>> itr = violations.iterator();
		while (itr.hasNext()) {
			ConstraintViolation<UserSignupRequest> constr = itr.next();
			String errorMsg = constr.getMessage();
			joiner.add(errorMsg);
		}
		String errorString = joiner.toString();
		
		errorsText.add(errorString);
	}
	
	
	/**
	 * Create csv file with result error messages for each failed test
	 */
	@AfterAll
	public static void writeCsv() {
		if (errorsText.isEmpty()) {
			return;
		}
		
		String filePath = "src/test/resources/user_test_data_result.csv";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
            
            for (String error : errorsText) {
                writer.write(error);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	
	private static void getDataFromRemoteRepo() {
		String remoteRepoURL = "https://github.com/Laboulaye/test_data_util.git";
        String localRepoPath = "src/test/resources";
        String invalidDataPath = "data/invalid_user_test_data.csv";
        String validDataPath = "data/valid_user_test_data.csv";
        
        String currentWorkingDirectory = System.getProperty("user.dir");

        try {
            // Clone the repository
            Git git = cloneRepository(remoteRepoURL, localRepoPath);

            // Retrieve the file
            retrieveFile(git, validDataPath);
            retrieveFile(git, invalidDataPath);

            // Clean up
            git.close();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }
	}
	
	
	private static Git cloneRepository(String remoteRepoURL, String localRepoPath)
            throws GitAPIException {
		File file = new File(localRepoPath);
		System.out.println(file.getAbsolutePath());
        Git git = Git.cloneRepository()
                .setURI(remoteRepoURL)
                .setDirectory(new File(localRepoPath))
                .call();

        System.out.println("Repository cloned successfully.");
        return git;
    }
	
	
	private static void retrieveFile(Git git, String filePath) throws IOException, GitAPIException {
        Repository repository = git.getRepository();
        Path localFilePath = Paths.get(repository.getWorkTree().getAbsolutePath(), filePath);

    }
	
	
}
