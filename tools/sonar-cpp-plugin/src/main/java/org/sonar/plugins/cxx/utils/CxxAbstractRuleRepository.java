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
package org.sonar.plugins.cxx.utils;

import java.io.InputStream;
import java.util.List;

import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;
import org.sonar.plugins.cxx.CxxLanguage;
import org.sonar.plugins.cxx.CxxPlugin;

public abstract class CxxAbstractRuleRepository extends RuleRepository {
	  public static final String REPOSITORY_KEY = CxxPlugin.KEY;
	  
	  abstract protected String repositoryFileName();
	  abstract protected String repositoryName();
	  
	  public CxxAbstractRuleRepository()
	  {
	    super(REPOSITORY_KEY, CxxLanguage.KEY);
	    setName(repositoryName());
	  }
	  
	  @Override
	  public List<Rule> createRules() {
	    final XMLRuleParser xmlParser = new XMLRuleParser();
	    final InputStream xmlStream = getClass().getResourceAsStream(repositoryFileName());
	    return xmlParser.parse(xmlStream);
	    
	  }

	}