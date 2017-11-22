/**
 * 
 */
package io.github.agentsoz.bushfire.matsimjill;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.core.utils.io.UncheckedIOException;
import org.matsim.core.utils.misc.CRCChecksum;
import org.matsim.testcases.MatsimTestUtils;
import io.github.agentsoz.bushfire.matsimjill.Main;
/**
 * @author dsingh
 *
 */
public class MainCampbellsCreek50Test {
	// have tests in separate classes so that they run, at least under maven, in separate JVMs.  kai, nov'17

	@Rule public MatsimTestUtils utils = new MatsimTestUtils() ;

	@Test
	public void testCampbellsCreek50() {

		String [] args = {
				"--config",  "scenarios/campbells-creek/scenario_main.xml", 
				"--logfile", "scenarios/campbells-creek/scenario.log",
				"--loglevel", "INFO",
				//	                "--plan-selection-policy", "FIRST", // ensures it is deterministic, as default is RANDOM
				"--seed", "12345",
				"--safeline-output-file-pattern", "scenarios/campbells-creek/safeline.%d%.out",
				"--matsim-output-directory", utils.getOutputDirectory(),
				"--jillconfig", "--config={"+
						"agents:[{classname:io.github.agentsoz.bushfire.matsimjill.agents.Resident, args:null, count:50}],"+
						"logLevel: WARN,"+
						"logFile: \"scenarios/campbells-creek/jill.log\","+
						"programOutputFile: \"scenarios/campbells-creek/jill.out\","+
						"randomSeed: 12345,"+ // jill random seed
						"numThreads: 1"+ // run jill in single-threaded mode so logs are deterministic
		"}"};

		Main.main(args);
		
		// first print out the actuals so we have them even if it fails afterwards

		long actualEvents = CRCChecksum.getCRCFromFile( utils.getOutputDirectory() + "/output_events.xml.gz" ) ;
		System.err.println("actual(events)="+actualEvents) ;

		long actualPlans = CRCChecksum.getCRCFromFile( utils.getOutputDirectory() + "/output_plans.xml.gz" ) ;
		System.err.println("actual(plans)="+actualPlans) ;

		long [] expectedEvents = new long [] {
                CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/output_events.xml.gz" ), 
		        //CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/1/output_events.xml.gz" ), 
				//CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/1/output_events.xml.gz" ), 
				// 3214464728 mvn console, eclipse single
				//CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/2/output_events.xml.gz" ), 
				// 1316710466 eclipse in context
				//				CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/3/output_events.xml.gz" ) 
                3114851905L // 3695594801L // travis
		} ;

		long [] expectedPlans = new long [] {
		        CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/output_plans.xml.gz" ), 
				//CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/1/output_plans.xml.gz" ), 
				// 1884178769 mvn console, eclipse single
				//CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/2/output_plans.xml.gz" ),
				// eclipse in context
				//				CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/3/output_plans.xml.gz" )
		        2234597649L // 547669447L // travis
		} ;

        checkSeveral(expectedEvents, actualEvents);
        checkSeveral(expectedPlans, actualPlans);

		//		{
		//			long expectedCRC = CRCChecksum.getCRCFromFile( utils.getInputDirectory() + "/jill.out" ) ;
		//			long actualCRC = CRCChecksum.getCRCFromFile( "scenarios/campbells-creek/jill.out" ) ;
		//			Assert.assertEquals (expectedCRC, actualCRC); 
		//		}
	}

	public static void checkSeveralFiles(long actual, final String cmpFileName, String baseDir ) {
		List<Long> expecteds = new ArrayList<>() ;
	
		try {
			Files.walkFileTree(new File(baseDir).toPath(), new SimpleFileVisitor<Path>() {
				@Override public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					final String filename = dir + cmpFileName;
					if ( Files.exists( new File( filename).toPath() ) ) {
						System.err.println( "checking against " + filename );
						long crc = CRCChecksum.getCRCFromFile( filename ) ; 
						expecteds.add(crc) ;
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new UncheckedIOException(e.getMessage(), e);
		}
	
		checkSeveral(expecteds, actual);
	}

	public static void checkSeveral(List<Long> expecteds, long actualEvents) {
		long [] expectedsArray = new long[expecteds.size()] ;
		for ( int ii=0 ; ii<expecteds.size() ; ii++ ) {
			expectedsArray[ii] = expecteds.get(ii) ;
		}
		checkSeveral( expectedsArray, actualEvents ) ;
	}
	public static void checkSeveral(long[] expectedEvents, long actualEvents) {
		boolean found = false ;
		for ( int ii=0 ; ii<expectedEvents.length ; ii++ ) {
			final boolean b = actualEvents==expectedEvents[ii];
			System.err.println("checking if " + actualEvents + "==" + expectedEvents[ii] + " ? " + b);
			if ( b ) {
				found = true ;
				break ;
			}
		}
		if ( !found ) {
			Assert.fail(); 
		}
	}

}
