package org.sonar.plugins.cxx.cccc;

import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.plugins.cxx.utils.CxxAbstractProfileDefinition;

public class CxxCcccProfile extends CxxAbstractProfileDefinition{

	  public CxxCcccProfile(XMLProfileParser xmlProfileParser) {
		  super(xmlProfileParser);
	  }
	  
	  @Override
	  protected String profileFileName() {
		  return "cccc-profile.xml";
	  }
}