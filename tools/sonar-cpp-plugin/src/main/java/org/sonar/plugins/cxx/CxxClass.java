package org.sonar.plugins.cxx;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.utils.WildcardPattern;
import org.sonar.plugins.cxx.cppcheck.CxxCppCheckSensor;
 
public class CxxClass extends Resource<CxxFile> {
	
	private CxxFile file;
	private String name;
	private String description;
	private String qualifier;
	private String scope;
	private Language language;
	
//	public static final String SCOPE = Scopes.BLOCK_UNIT;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public String getLongName() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public CxxFile getParent() {
		return file;
	}

	@Override
	public String getQualifier() {
		return qualifier;
	}

	@Override
	public String getScope() {
		return scope;
	}

	@Override
	public boolean matchFilePattern(String pattern) {
		logger.debug("matchFilePattern call with {} on {}", pattern, getKey());
		String patternWithoutFileSuffix = StringUtils.substringBeforeLast(
				pattern, ".");
		WildcardPattern matcher = WildcardPattern.create(
				patternWithoutFileSuffix, "/");
		return matcher.match(getKey());
	}
	
	private static Logger logger = LoggerFactory
	.getLogger(CxxCppCheckSensor.class);
	
	private String CanonicalizeAbsoluteFilePath(String filePath)
	{
		filePath = filePath.replaceAll("/./", "/").replaceAll("/+", "/");
		//logger.info("Build FilePath 2 = {}", filePath);
		File tmp = new File(filePath);
		try {
			filePath = tmp.getCanonicalPath();
		} catch (IOException e) {
			filePath = tmp.getAbsolutePath();
		}
		return filePath;
	}
	
	private void initIdentifierFromAbsolutFilePath(Project project, String absoluteFilePath, String functionName)
	{
        file = CxxFile.fromFileName(project, absoluteFilePath, false);
		String key =  file.getParent().getProjectPath() + ":" + file.getName() + ":" + functionName;
		logger.debug("key =  {}", key.replace('/', ':'));
		setKey(key.replace('/', ':'));
	}
	
	private CxxClass(Project project, String fileName, String functionName, boolean unitTest) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		scope = Resource.SCOPE_ENTITY;
		qualifier =Resource.QUALIFIER_CLASS;// unitTest ? Resource.QUALIFIER_UNIT_TEST_CLASS : Resource.QUALIFIER_METHOD;//Resource.QUALIFIER_FILE;
		language = CxxLanguage.INSTANCE;
		name = functionName;

		String realFileName = StringUtils.trim(fileName).replace('\\', '/');
		if (new File(realFileName).isAbsolute()) { //realFileName.startsWith("/")) {
			logger.debug("we got an absolute path");
			realFileName = CanonicalizeAbsoluteFilePath(realFileName);
			initIdentifierFromAbsolutFilePath(project, realFileName, functionName);
		} else if (realFileName.contains("/")) {
			logger.debug("we got a relative path");
/*			for (String includePath : includeSearchPath) {
				String pathToTest = null;
				if (new File(includePath).isAbsolute())
					pathToTest = includePath + "/" + realFileName;	
				else
					pathToTest = project.getFileSystem().getBasedir().toString() + "/" + includePath + "/" + realFileName;						
				//logger.info("Build FilePath 1 = {}", pathToTest);
				pathToTest = CanonicalizeAbsoluteFilePath(pathToTest);
				if (new File(pathToTest).exists()) {
					realFileName = pathToTest;
					break;
				}
			}*/
			initIdentifierFromAbsolutFilePath(project, realFileName, functionName);
		} else {
			logger.debug("we got something else");
			file = CxxFile.fromFileName(project, realFileName, unitTest);
			setKey(functionName);
		}
		
		Object t[] = {realFileName, getKey(), name, file.getKey()};
		logger.debug("CxxFunction created from fileName {},  key = {}, Name = {}, filekey = {}", t);
	}
	
	public static CxxClass fromFileName(Project project, String filename, String name, boolean unitTest) {
		logger.debug("CxxFunction fromFileName:  filename = " + filename + " function = " + name);
		return new CxxClass(project, filename, name, unitTest);
	}

}
