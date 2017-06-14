package io.github.agentsoz.conservation;

/*
 * #%L
 * BDI-ABM Integration Package
 * %%
 * Copyright (C) 2014 - 2015 by its authors. See AUTHORS file.
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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import io.github.agentsoz.conservation.ExtensionOffice.Policy;

/**
 * Store all configurable parameters with their default values and some utility
 * functions.
 * 
 * @author Sewwandi Perera
 *
 */
public class ConservationUtils {

	private static Package referencePackage = null;

	// Default number of packages
	private static int numberOfPackages = 26;

	/**
	 * This enum id used to store how agents change their level of conservation
	 * ethic or profit motivation barometer. UP : agent moved from either low CE
	 * to high CE or low PM to high PM DOWN : agent moved from either high CE to
	 * low CE or high PM to low PM NONE : agent did not change the category of
	 * the barometer
	 */
	public enum CategoryChanges {
		UP,

		DOWN,

		NONE;
	}

	// All configurable data with their default values are declared below

	/**
	 * High probability of a land holder participating in auction. This value is
	 * used in both DecidePartcipationWhenHighCLowP.plan and
	 * DecidePartcipationWhenHighCHighP.plan.
	 * 
	 * Command line argument to set this value: high_participation_prob
	 */
	private static double highParticipationProbability = 0.8;

	/**
	 * Low probability of a land holder participating in auction. This value is
	 * used in DecidePartcipationWhenLowCLowP.plan
	 * 
	 * Command line argument to set this value: -low_participation_prob
	 */
	private static double lowParticipationProbability = 0.3;

	/**
	 * A Conservation Ethic Barometer, which is above this threshold is
	 * considered as HIGH.
	 * 
	 * Command line argument to set this value: -upper_threshold_c
	 */
	private static double upperThresholdC = 70;

	/**
	 * A Conservation Ethic Barometer, which is below this threshold is
	 * considered as LOW.
	 * 
	 * Command line argument to set this value: -lower_threshold_c
	 */
	private static double lowerThresholdC = 30;

	/**
	 * A Profit Motive Barometer, which is above this threshold is considered as
	 * HIGH.
	 * 
	 * Command line argument to set this value: -upper_threshold_p
	 */
	private static double upperThresholdP = 70;

	/**
	 * A Profit Motive Barometer, which is below this threshold is considered as
	 * LOW.
	 * 
	 * Command line argument to set this value: -lower_threshold_p
	 */
	private static double lowerThresholdP = 30;

	/**
	 * Maximum value of conservation ethic barometer
	 * 
	 * Command line argument to set this value: -max_c
	 */
	private static double maxConservationEthic = 100;

	/**
	 * Maximum value of profit motive barometer
	 * 
	 * Command line argument to set this value: -max_p
	 */
	private static double maxProfitMotivation = 100;

	/**
	 * This value is used, when updating individual conservation ethic barometer
	 * according to social norm.
	 * 
	 * Command line argument to set this value: -socialNormUpdatePercentage
	 */
	private static double socialNormUpdatePercentage = 10;

	/**
	 * This seed is used when initializing conservation ethic barometer of each
	 * agent
	 * 
	 * Command line argument to set this value: -conservationEthicSeed
	 */
	private static long conservationEthicSeed = 123456789;

	/**
	 * This seed is used when initializing profit motive barometer of each agent
	 * 
	 * Command line argument to set this value: -profitMotivationSeed
	 */
	private static long profitMotivationSeed = 987654321;

	/**
	 * This parameter represents the gap between low, medium and high profit
	 * percentage levels.
	 * 
	 * Command line argument to set this value: -profitDifferenctial
	 */
	private static double profitDifferenctial = 20;

	/**
	 * The number of bids by default a normal agent make.
	 * 
	 * Command line argument to set this value: -defaultMaxNumberOfBids
	 */
	private static int defaultMaxNumberOfBids = 7;

	/**
	 * The number of bids to be added to “defaultBids” parameter to get bid
	 * numbers for HighCLowP categories
	 */
	private static int bidAddon = 5;

	/**
	 * This parameter represents the size of profit ranges
	 * (lowProfitPercentageRange, medProfitPercentageRange and high
	 * ProfitPercentageRange).
	 * 
	 * Command line argument to set this value: -profitVariability
	 */
	private static double profitVariability = 10;

	/**
	 * The factor used to increase and decrease conservation ethic barometer of
	 * agents
	 * 
	 * Command line argument to set this value: -conservationEthicModifier
	 */
	private static double conservationEthicModifier = 0.2;

	/**
	 * The factor used to increase and decrease profit motive barometer of
	 * agents
	 * 
	 * Command line argument to set this value: -profitMotivationModifier
	 */
	private static double profitMotivationModifier = 0.2;

	/**
	 * The factor used to linearly increase and decrease conservation ethic
	 * barometer of agents
	 */
	private static double staticConservationEthicModifier = -1;

	/**
	 * The minimum margin of medium profit range
	 * 
	 * Command line argument to set this value: -medProfitRangeMinMargin
	 */
	private static double medProfitRangeMinMargin = 10;

	/**
	 * Percentage of agents having high conservation ethic barometer
	 * 
	 * Command line argument to set this value: -highCEAgentsPercentage
	 */
	private static double highCEAgentsPercentage = 40;

	/**
	 * The percentage of maximum possible target (if all agents bid on the
	 * highest package) that should be assigned as the target.
	 * 
	 * Command line argument to set this value: -targetPercentage
	 */
	private static double targetPercentage = 20;

	/**
	 * The visiting policy for extension officers. See {@link Policy}.
	 */
	private static Policy visitPolicy = ExtensionOffice.Policy.NEVER;
	
	/** 
	 * The amount by which an landholders' conservation ethic value is boosted,
	 * in absolute amount, by a visit from an extension officer.
	 */
	private static double visitConservationEthicBoostValue = 10.0;
	
	/**
	 * The minimum margin of high profit range
	 * 
	 * Command line argument to set this value: -highProfitRangeMinMargin
	 */
	private static double highProfitRangeMinMargin = 20;

	/**
	 * The Random used throughout application.
	 */
	private static Random globalRandom;

	/**
	 * This seed is used for the global random, which is used throughout the
	 * application
	 * 
	 * Command line argument to set this value: -globalRandomSeed
	 */
	private static long globalRandomSeed = 543219876;

	public static void setNumberOfPackages(int numbOfPackages) {
		numberOfPackages = numbOfPackages;
	}

	public static double getHighParticipationProbability() {
		return highParticipationProbability;
	}

	public static double getLowParticipationProbability() {
		return lowParticipationProbability;
	}

	public static double getUpperThresholdC() {
		return upperThresholdC;
	}

	public static double getLowerThresholdC() {
		return lowerThresholdC;
	}

	public static double getUpperThresholdP() {
		return upperThresholdP;
	}

	public static double getLowerThresholdP() {
		return lowerThresholdP;
	}
	
	public static Policy getVisitPolicy() {
		return visitPolicy;
	}
	public static void setVisitPolicy(Policy p) {
		visitPolicy = p;
	}
	public static String getVisitPolicyOptions() {
		String opts = "";
		for (Policy p : visitPolicy.values()) {
			opts += p + " ";
		}
		return opts;
	}

	public static void setHighParticipationProbability(double value) {
		highParticipationProbability = value;
	}

	public static void setLowParticipationProbability(double value) {
		lowParticipationProbability = value;
	}

	public static void setLowerThresholdC(double value) {
		lowerThresholdC = value;
	}

	public static void setUpperThresholdC(double value) {
		upperThresholdC = value;
	}

	public static void setUpperThresholdP(double value) {
		upperThresholdP = value;
	}

	public static void setLowerThresholdP(double value) {
		lowerThresholdP = value;
	}

	public static double getMaxConservationEthic() {
		return maxConservationEthic;
	}

	public static void setMaxConservationEthic(double value) {
		ConservationUtils.maxConservationEthic = value;
	}

	public static double getMaxProfitMotivation() {
		return maxProfitMotivation;
	}

	public static void setMaxProfitMotivation(double value) {
		ConservationUtils.maxProfitMotivation = value;
	}

	public static double getSocialNormUpdatePercentage() {
		return socialNormUpdatePercentage;
	}

	public static void setSocialNormUpdatePercentage(double value) {
		ConservationUtils.socialNormUpdatePercentage = value;
	}

	public static long getConservationEthicSeed() {
		return conservationEthicSeed;
	}

	public static void setConservationEthicSeed(long conservationEthicSeed) {
		ConservationUtils.conservationEthicSeed = conservationEthicSeed;
	}

	public static long getProfitMotivationSeed() {
		return profitMotivationSeed;
	}

	public static void setProfitMotivationSeed(long profitMotivationSeed) {
		ConservationUtils.profitMotivationSeed = profitMotivationSeed;
	}

	public static double getProfitDifferenctial() {
		return profitDifferenctial;
	}

	public static void setProfitDifferenctial(double profitDifferenctial) {
		ConservationUtils.profitDifferenctial = profitDifferenctial;
	}

	public static double getProfitVariability() {
		return profitVariability;
	}

	public static void setProfitVariability(double profitVariability) {
		ConservationUtils.profitVariability = profitVariability;
	}

	public static int getDefaultMaxNumberOfBids() {
		return defaultMaxNumberOfBids;
	}

	public static void setDefaultMaxNumberOfBids(double defaultMaxNumberOfBids) {
		ConservationUtils.defaultMaxNumberOfBids = (int) Math
				.round(defaultMaxNumberOfBids);
	}

	public static int getBidAddon() {
		return bidAddon;
	}

	public static void setBidAddon(double bidAddon) {
		ConservationUtils.bidAddon = (int) Math.round(bidAddon);
	}

	public static double getConservationEthicModifier() {
		return conservationEthicModifier;
	}

	public static void setConservationEthicModifier(
			double agentConservationEthicModifier) {
		ConservationUtils.conservationEthicModifier = agentConservationEthicModifier;
	}

	public static double getStaticConservationEthicModifier() {
		if (staticConservationEthicModifier == -1) {
			setStaticConservationEthicModifier(getConservationEthicModifier()
					* getLowProfitPercentageRange()[1] / 200);
		}

		return staticConservationEthicModifier;
	}

	public static void setStaticConservationEthicModifier(
			double staticAgentAttributeModifier) {
		ConservationUtils.staticConservationEthicModifier = staticAgentAttributeModifier;
	}

	public static double getLowProfitPercentage() {
		return 0;
	}

	public static double[] getLowProfitPercentageRange() {
		double lowProfitPercentage = getLowProfitPercentage();
		double variability = getProfitVariability();

		return new double[] { lowProfitPercentage - variability,
				lowProfitPercentage + variability };
	}

	public static double getMediumProfitPercentage() {
		return getLowProfitPercentage() + getProfitDifferenctial();
	}

	public static double[] getMediumProfitPercentageRange() {
		double medProfitPercentage = getMediumProfitPercentage();
		double variability = getProfitVariability();

		return new double[] {
				Math.max(medProfitPercentage - variability,
						medProfitRangeMinMargin),
				medProfitPercentage + variability };
	}

	public static double getHighProfitPercentage() {
		return getMediumProfitPercentage() + getProfitDifferenctial();
	}

	public static double[] getHighProfitPercentageRange() {
		double highProfitPercentage = getHighProfitPercentage();
		double variability = getProfitVariability();

		return new double[] {
				Math.max(highProfitPercentage - variability,
						highProfitRangeMinMargin),
				highProfitPercentage + variability };
	}

	public static Package getReferencePackage() {

		if (referencePackage != null) {
			return referencePackage;
		}

		Comparator<Package> customComparator = new Comparator<Package>() {
			@Override
			public int compare(Package o1, Package o2) {
				if (o1.opportunityCost < o2.opportunityCost)
					return -1;
				if (o1.opportunityCost > o2.opportunityCost)
					return 1;
				return 0;
			}
		};

		Package[] packages = Package.getPackages(numberOfPackages).clone();
		Arrays.sort(packages, customComparator);

		referencePackage = packages[(int) (packages.length * 0.75)];

		return referencePackage;
	}

	public static double getReferenceBidProfit() {
		return getReferencePackage().opportunityCost
				* getHighProfitPercentage() / 100;
	}

	public static double getMedProfitRangeMinMargin() {
		return medProfitRangeMinMargin;
	}

	public static void setMedProfitRangeMinMargin(double medProfitRangeMinMargin) {
		ConservationUtils.medProfitRangeMinMargin = medProfitRangeMinMargin;
	}

	public static double getHighProfitRangeMinMargin() {
		return highProfitRangeMinMargin;
	}

	public static void setHighProfitRangeMinMargin(
			double highProfitRangeMinMargin) {
		ConservationUtils.highProfitRangeMinMargin = highProfitRangeMinMargin;
	}

	public static double getProfitMotivationModifier() {
		return profitMotivationModifier;
	}

	public static void setProfitMotivationModifier(
			double profitMotivationModifier) {
		ConservationUtils.profitMotivationModifier = profitMotivationModifier;
	}

	public static double getHighCEAgentsPercentage() {
		return highCEAgentsPercentage;
	}

	public static void setHighCEAgentsPercentage(double populationCategory) {
		ConservationUtils.highCEAgentsPercentage = populationCategory;
	}

	public static double getTargetPercentage() {
		return targetPercentage;
	}

	public static void setTargetPercentage(double targetPercentage) {
		ConservationUtils.targetPercentage = targetPercentage;
	}

	public static Random getGlobalRandom() {
		if (globalRandom == null) {
			setGlobalRandom(new Random(getGlobalRandomSeed()));
		}
		return globalRandom;
	}

	public static void setGlobalRandom(Random globalRandom) {
		ConservationUtils.globalRandom = globalRandom;
	}

	public static long getGlobalRandomSeed() {
		return globalRandomSeed;
	}

	public static void setGlobalRandomSeed(long globalRandomSeed) {
		ConservationUtils.globalRandomSeed = globalRandomSeed;
		setGlobalRandom(new Random(globalRandomSeed));
	}
	
	public static int getNumberOfPackages() {
		return numberOfPackages;
	}
	
	/**
	 * See {@link #visitConservationEthicBoostValue}
	 * @return
	 */
	public static double getVisitConservationEthicBoostValue() {
		return visitConservationEthicBoostValue;
	}
	public static void setVisitConservationEthicBoostValue(double val) {
		visitConservationEthicBoostValue = val;
	}

	public static double sigmoid_normalised_100(double x) {
		final double a = 0.1;
		final double c = 50;
		return 1/(1 + Math.exp(-a*(x-c)));
	}

}
