package io.github.agentsoz.conservation.jill.goals;

/*
 * #%L BDI-ABM Integration Package %% Copyright (C) 2014 - 2017 by its authors. See AUTHORS file. %%
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>. #L%
 */

import io.github.agentsoz.jill.lang.Goal;
import io.github.agentsoz.jill.lang.GoalInfo;

@GoalInfo(hasPlans = {"io.github.agentsoz.conservation.jill.plans.MeetExtensionOfficerPlan"})
public class MeetExtensionOfficerGoal extends Goal {

  private double influence;

  public MeetExtensionOfficerGoal(String str, double influence) {
    super(str);
    this.influence = influence;
  }
  
  public double getInfluence() {
    return influence;
  }

}
