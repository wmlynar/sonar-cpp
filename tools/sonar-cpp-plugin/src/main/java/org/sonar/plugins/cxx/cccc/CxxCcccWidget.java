package org.sonar.plugins.cxx.cccc;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

@UserRole(UserRole.USER)
public class CxxCcccWidget extends AbstractRubyTemplate implements RubyRailsWidget {

	  public String getId() {
	    return "CxxCccc_widget";
	  }

	  public String getTitle() {
	    return "Cccc Metrics";
	  }

	  protected String getTemplatePath() {
	    return "/cccc_widget.html.erb";
	  }
	}

