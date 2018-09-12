/**
 * 
 */
package io.github.agentsoz.ees;

import io.github.agentsoz.util.TestUtils;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.core.utils.misc.CRCChecksum;
import org.matsim.testcases.MatsimTestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dsingh
 *
 */
public class Castlemaine1000Test {
	// have tests in separate classes so that they run, at least under maven, in separate JVMs.  kai, nov'17
	
	private static final Logger log = LoggerFactory.getLogger(Castlemaine1000Test.class) ;

	@Rule public MatsimTestUtils utils = new MatsimTestUtils() ;

	@Test
	public void testCastlemaine1000ReducedV2() {

		utils.getOutputDirectory(); // creates a clean one so need to call this first
		String[] args = {
				"--config", "scenarios/mount-alexander-shire/castlemaine-1000/ees.xml",
		};
		Run.main(args);

		final String actualEventsFilename = utils.getOutputDirectory() + "/output_events.xml.gz";
		final String primaryExpectedEventsFilename = utils.getInputDirectory() + "/output_events.xml.gz";
		TestUtils.comparingDepartures(primaryExpectedEventsFilename,actualEventsFilename,10.);
		TestUtils.comparingArrivals(primaryExpectedEventsFilename,actualEventsFilename,10.);
		TestUtils.comparingActivityStarts(primaryExpectedEventsFilename,actualEventsFilename, 10.);
		TestUtils.compareFullEvents(primaryExpectedEventsFilename,actualEventsFilename, false);

	}

}
