package org.sonar.plugins.cxx.cccc.utils;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.cxx.CxxLanguage;

public class FileUtils {
	
	public static final String DEFAULT_FILE_LIST_NAME = "metric-files.txt";
	public static final  String DEFAULT_DIR_LIST_NAME = "metric-list.txt";
	public static final String CCCC_PATH = "cccc";

	public static String getCommandString(List<File> files, String outDir) {
		final StringBuilder sb = new StringBuilder();
		sb.append(CCCC_PATH);
		
		for(File f : files) {
			sb.append(" ");
			sb.append(f.toString());
		}

		sb.append(" ");
		if (outDir != null) {
			sb.append("--outdir=");
			sb.append(outDir);
		}
		return sb.toString();
	}
	
	
	public static void executeCommand(String cmd) throws IOException {
		Logger logger = LoggerFactory.getLogger(FileUtils.class);
		String s = null;

        Process p = Runtime.getRuntime().exec(cmd);
        
        BufferedReader stdInput = new BufferedReader(new 
             InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new 
             InputStreamReader(p.getErrorStream()));

        // read the output from the command
        logger.debug("Here is the standard error of the command (if any):\n");
        while ((s = stdInput.readLine()) != null) {
        	logger.debug(s);
        }
        
        // read any errors from the attempted command
        logger.debug("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            logger.debug(s);
        }
	}
	
	/**
	 * Return all files from source dir
	 * @param projectDir
	 * @return
	 * @throws IOException
	 */
	public static List<File> getSourceFiles(String projectDir) throws IOException {
		File file = new File(projectDir, FileUtils.DEFAULT_DIR_LIST_NAME);
		List<String> dirs = FileUtils.readDirList(file);
		List<File> files = new ArrayList<File>();
		for (String dirStr : dirs) {
			files.addAll(FileUtils.getAllCppFilesFromDir(projectDir + dirStr));
		}
		return files;
	}
	
	/**
	 * Returns an array of xml files from given directory, does not enter
	 * recursively.
	 */
	public static List<File> getAllXmlFilesFromDir(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		List<File> res = new ArrayList<File>();
		for (int fileInList = 0; fileInList < files.length; fileInList++) {
			if (files[fileInList].isFile()
					&& files[fileInList].getName().toLowerCase()
							.endsWith("xml")) {
				res.add(files[fileInList]);
			}
		}
		return res;
	}

	/**
	 * Returns list list of given directory and its subdirectories.
	 */
	public static List<File> getDirectoriesFromDir(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		List<File> res = new ArrayList<File>();
		res.add(file);
		for (int fileInList = 0; fileInList < files.length; fileInList++) {
			if (files[fileInList].isDirectory()) {
				res.addAll(getDirectoriesFromDir(files[fileInList].toString()));
			}
		}
		return res;
	}
	

	public static List<File> getProjectFiles(String sourceDir) throws IOException {
	    File file = new File(sourceDir + "/" + DEFAULT_FILE_LIST_NAME);
	    FileInputStream fstream = new FileInputStream(file);
	    // Get the object of DataInputStream
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    List<File> files = new ArrayList<File>();
	    while ((strLine = br.readLine()) != null) {
	      files.add(new File(sourceDir + strLine));
	    }
	    in.close();
	    return files;
	}

	//public static final String[] SUFFIXES = {"cpp", "h", "hpp"};
	/**
	 * Returns an array of c++ files from given directory, does not enter
	 * recursively.
	 */
	public static List<File> getAllCppFilesFromDir(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		List<File> res = new ArrayList<File>();
		for (int fileInList = 0; fileInList < files.length; fileInList++) {
			if (files[fileInList].isDirectory()) {
				res.addAll(getAllCppFilesFromDir(files[fileInList].toString()));
			}
			for (String suffix : CxxLanguage.SUFFIXES)
			{
				if (files[fileInList].isFile() && files[fileInList].getName().toLowerCase()
						.endsWith(suffix)) {
				res.add(files[fileInList]);
				}
			}
		}
		return res;
	}
	
	/**
	 * Return list of directories with sources files 
	 * @param paretnDir
	 * @return
	 */
	public static List<File> getSourceDirs(String paretnDir) {
		File dir = new File(paretnDir);
		File[] files = dir.listFiles();
		List<File> res = new ArrayList<File>();
		for (File file : files) {
			if (hasSourceFile(file)) {
				res.add(file);
			}
			else if (file.isDirectory()) {
				res.addAll(getSourceDirs(file.toString()));
			}
		}
		return res;
	}
	
	/**
	 * Check if directory has any c++ file 
	 * @param dir
	 * @return
	 */
	public static boolean hasSourceFile(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				for (String suffix : CxxLanguage.SUFFIXES) {
					if (file.isFile()
							&& file.getName().toLowerCase().endsWith("." + suffix)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Create file with list of all c++ files from project source directory
	 * @param files
	 * @param dir
	 * @throws IOException
	 */
	public static void generateFileList(List<File> files, String dir, String fileName) throws IOException {
		File file = new File(dir + "/" + fileName);
		if (file.exists()) {
			return;
		}
		FileOutputStream fop=new FileOutputStream(file);

	      if(file.exists()){
	    	  for (File f : files)
	    	  {
	    		  String s = f.getPath().replaceFirst(dir, "");
	    		  s += "\n";
	    		  fop.write(s.getBytes());
	    	  }

	          fop.flush();
	          fop.close();
	          }
	}
	
	/**
	 * Read list of directories from file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readDirList(File file) throws IOException {
		FileInputStream fstream = new FileInputStream(file);
	    // Get the object of DataInputStream
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    List<String> dirs = new ArrayList<String>();
	    while ((strLine = br.readLine()) != null)   {
	      dirs.add(strLine);
	    }
	    in.close();
	    return dirs;
	}

	/**
	 * Deletes all files from directory
	 * @param dir
	 * @return
	 */
	public static boolean clearDir(File dir) {
		if (dir.isDirectory()) {
	    	for (File file : dir.listFiles()) {
	    		if(!deleteDir(file)) {
	    			return false;
	    		}
	    	}
		}
		return true;
	}

	/**
	  * Deletes all files and subdirectories under dir.
	  * Returns true if all deletions were successful.
	  * If a deletion fails, the method stops attempting to delete and returns false.
	  */
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	    	for (File file : dir.listFiles()) {
	    		if(!deleteDir(file)) {
	    			return false;
	    		}
	    	}
	    }
	    // The directory is now empty so delete it
	    return dir.delete();
	}
	
	
	public static String readFileToString(File file) throws IOException {
		return readFileToString(file, 0, 0);
	}
	
	public static String readFileToString(File file, int fromLine) throws IOException {
		return readFileToString(file, fromLine, 0);
	}
	
	/**
	 * Read content of file to string, read only fragment between formLine and toLine
	 * @param file 
	 * @param fromLine
	 * @param toLine
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String readFileToString(File file, int fromLine, int toLine) throws IOException  
	{
		FileInputStream fstream;
		DataInputStream in = null;
		String str = ""; 
		int line = 0;
		try {
			fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// read File Line By Line
			while ((strLine = br.readLine()) != null) {
				line++;
				// read only fragment between formLine and toLine
				// if formLine and toLine equals 0 read all file
				if (line >= fromLine && (line <= toLine || toLine == 0)) {	
					str += strLine + "\n";
				}
			}
		}
		finally {
			in.close();
		}
		return str;
	}
}
