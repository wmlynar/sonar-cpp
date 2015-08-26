/*
 * Sonar Cxx Plugin, open source software quality management tool.
 * Copyright (C) 2010 - 2011, Neticoa SAS France - Tous droits réservés.
 * Author(s) : Franck Bonin, Neticoa SAS France.
 *
 * Sonar Cxx Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar Cxx Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar Cxx Plugin; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.cxx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.AbstractSourceImporter;
import org.sonar.api.batch.maven.MavenPlugin;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.utils.SonarException;
import org.sonar.plugins.cxx.cccc.utils.FileUtils;



public final class CxxSourceImporter extends AbstractSourceImporter {
	private static final String DEFAULT_SOURCE_LIST_FILE = "metric-list.txt";

	private Project project;
	
	public static final String GROUP_ID = "org.codehaus.mojo";
	public static final String ARTIFACT_ID = "cxx-maven-plugin";

	protected String getMAVEN_PLUGIN_ARTIFACT_ID() {
		return ARTIFACT_ID;
	}
	
	protected String getMAVEN_PLUGIN_GROUP_ID() {
		return GROUP_ID;
	}
	private static Logger logger = LoggerFactory
	.getLogger(CxxSourceImporter.class);
	
	public CxxSourceImporter(Project p) 
	{
		super(CxxLanguage.INSTANCE);
		project = p;
		// don't import sources if other language
		if (!CxxLanguage.KEY.equals(project.getLanguageKey())) {
			return;
		}
		// add my import sources
		MavenPlugin plugin = MavenPlugin.getPlugin(project.getPom(),
				getMAVEN_PLUGIN_GROUP_ID(), getMAVEN_PLUGIN_ARTIFACT_ID());
		String sourceDirs[] = null;
		// if cxx-maven-plugin is used
		if (plugin != null) {
			sourceDirs = plugin.getParameters("/sourceDirs/param");
			for (String aPath : sourceDirs) {
				project.getPom().addCompileSourceRoot(aPath);
			}
			sourceDirs = plugin.getParameters("/sourceDirs/sourceDir");
			for (String aPath : sourceDirs) {
				logger.debug("sourceDir: " + aPath);
				project.getPom().addCompileSourceRoot(aPath);
			}
		}
		// cccc should still work if there is no cxx-maven-plugin  
		else {
			String projectDir = project.getFileSystem().getBasedir().toString();
			File file = new File(projectDir + "/" + DEFAULT_SOURCE_LIST_FILE);
			if (!file.exists()) {
				file = new File(projectDir + "/" + "metric-list1.txt");
				if (!file.exists())
					throw new SonarException("File " + file.getName() + " not found for this project");
			}
			List<String> dirs;
			try {
				dirs = FileUtils.readDirList(file);
				for (String aPath : dirs) {
					String dir = projectDir + aPath;
					File dirPath = new File(dir);
					project.getFileSystem().addSourceDir(dirPath);
					logger.debug("sourceDir: " + aPath);
				}
			} catch (IOException e) {
				throw new SonarException(e.getMessage());
			}
		}
	}
	
	protected Resource<CxxDir> createResource(File file, List<File> sourceDirs, boolean unitTest) {
	  return CxxFile.fromFileName(project, file.getAbsolutePath(), false);
	}

	@Override
	public String toString() {
	  return getClass().getSimpleName();
	}
}
