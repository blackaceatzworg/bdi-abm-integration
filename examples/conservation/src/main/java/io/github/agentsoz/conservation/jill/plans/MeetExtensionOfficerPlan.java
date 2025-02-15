/**
 * 
 */
package io.github.agentsoz.conservation.jill.plans;

/*
 * #%L
 * BDI-ABM Integration Package
 * %%
 * Copyright (C) 2014 - 2017 by its authors. See AUTHORS file.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.agentsoz.conservation.ConservationUtils;
import io.github.agentsoz.conservation.Main;
import io.github.agentsoz.conservation.jill.agents.Landholder;
import io.github.agentsoz.conservation.jill.goals.MeetExtensionOfficerGoal;
import io.github.agentsoz.jill.lang.Agent;
import io.github.agentsoz.jill.lang.Goal;
import io.github.agentsoz.jill.lang.Plan;
import io.github.agentsoz.jill.lang.PlanStep;

/**
 * @author dsingh
 *
 */
public class MeetExtensionOfficerPlan extends Plan {

    final private Logger logger = LoggerFactory.getLogger(Main.LOGGER_NAME);

	Landholder landholder;
	MeetExtensionOfficerGoal meetExtensionOfficerGoal;
	/**
	 * @param agent
	 * @param goal
	 * @param name
	 */
	public MeetExtensionOfficerPlan(Agent agent, Goal goal, String name) {
		super(agent, goal, name);
		landholder = (Landholder) agent;
		meetExtensionOfficerGoal = (MeetExtensionOfficerGoal) goal;
		body = steps;
	}

	@Override
	public boolean context() {
		return true;
	}

	@Override
	public void setPlanVariables(Map<String, Object> arg0) {
	}

	PlanStep[] steps = { 
		new PlanStep() {
			public void step() {
				
				double currentC = landholder.getConservationEthicBarometer();
				double deltaX = ConservationUtils.getSigmoidMaxStepX() * meetExtensionOfficerGoal.getInfluence();
				double oldX = ConservationUtils.sigmoid_normalised_100_inverse(currentC/100);
				double newX = (oldX + deltaX >= 100) ? 100.0 : oldX + deltaX;
				double newC = 100*ConservationUtils.sigmoid_normalised_100(newX);


				// Finally, update land holder's C and recalculate whether his C is
				// high or low.
				newC = landholder.setConservationEthicBarometer(newC);
				String newStatus = (landholder.isConservationEthicHigh()) ? "high" : "low";
				logger.debug(String.format("%supdated CE %.1f=>%.1f, which is %s"
						,landholder.logprefix(), currentC, newC, newStatus));
			}
		} 
	};
	
}
