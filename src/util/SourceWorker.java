package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The heavy lifting class 
 * 
 * @author nofearinc
 *
 */
public class SourceWorker {
	private String projectFolder;
	private String language;
	private String extension;
	private String[] functions;
	private String outputFile;
	private String execLog = "exec-log.txt";
	
	/**
	 * Set the worker
	 *  
	 * @param projectFolder the path to the project that we poke
	 * @param language the language (PHP, C++, Python...)
	 * @param extension the extension (php, cpp, py, ...)
	 * @param functions array of translation functions to catch
	 * @param outputFile the output file messages.po path
	 */
	public SourceWorker(String projectFolder, String language, String extension,
			String functionStr, String outputFile) {
		this.projectFolder = projectFolder;
		this.language = language;
		this.extension = extension;
		this.outputFile = outputFile;
		this.functions = functionStr.split("[, ]+");
	}
	
	public String buildCommandString() {
		StringBuilder keywordsBuilder = new StringBuilder();
		
		String workingDir = System.getProperty("user.dir");
		String pattern = "xgettext -f " + workingDir + "/pocandidates.txt %s %s %s %s";
		
//		commandBuilder.append("find " + projectFolder + " -iname *.");
//		commandBuilder.append(extension);
//		commandBuilder.append(" | xargs xgettext --language=");
//		commandBuilder.append(language);
		
		for(String function : functions) {
			keywordsBuilder.append(" --keyword=" + function);
		}
		
		// Replace with these
		String keywordsCommands = keywordsBuilder.toString();
		String extraArgs = " -j ";
		String outputString = "";
		if( ! outputFile.isEmpty() ) {
			outputString = "-o " + outputFile;
		}
		String langString = "";
		if( ! language.isEmpty() ) {
			langString = "--language=" + language;
		}
		
		// Build it
		String command = String.format(pattern, 
				langString, keywordsCommands, extraArgs, outputString);
		
//		commandBuilder.append(" -j -f - -o ");
//		commandBuilder.append(outputFile);
//		
//		String command = commandBuilder.toString();
//		System.out.println(command);
//		System.out.println("\n\n");
		
		return command;
	}
	
	public String executeTranslation() {
		List<String> filesList = findFiles();
		saveFiles("pocandidates.txt", filesList);
		String command = buildCommandString();
		System.out.println(command);

		try {
//			FileOutputStream fos = new FileOutputStream(execLog);
			
			Process proc = Runtime.getRuntime().exec(command);
			
            // any error message?
//            StreamGobbler errorGobbler = new 
//                StreamGobbler(proc.getErrorStream(), "ERROR");         
//			System.out.println(proc.exitValue());
//			
//			StreamGobbler outputGobbler = new 
//	                StreamGobbler(proc.getInputStream(), "OUTPUT", fos);
//		
//            errorGobbler.start();
//            outputGobbler.start();

            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);
//            fos.flush();
//            fos.close();        
            
			return "Command executed";
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Execution failed";
	}
	
	private void saveFiles(String outputFile, List<String> filesList) {
		try {
			PrintWriter writer = new PrintWriter(new File(outputFile));
			for(String fileName : filesList) {
				writer.println(fileName);
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public List<String> findFiles() {
		List<String> files = new ArrayList<>();
		
		String command = "find " + projectFolder + " -iname " + extension;
		
		try {
			Process proc = Runtime.getRuntime().exec(command);
			Scanner reader = new Scanner( proc.getInputStream() );
			
			while( reader.hasNextLine() ) {
				files.add(reader.nextLine());
			}
			
			reader.close();
			
			return files;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return files;
	}
	
}
