import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;


@RunWith(Suite.class) // Annotation is attached
@Suite.SuiteClasses({ // Another annotation is attached
	ascensore_Test_1.class,
	Rubrica_Test_1.class,
	SIS_Test_1.class,
})
public class LauncherTest {
	

	// Actual execution starts from this line using JunitTestSuitex class defined earlier
	public static void main(String[] args) {
			Result result = JUnitCore.runClasses(LauncherTest.class);
			for (Failure failure : result.getFailures()) {
				System.out.println(failure.toString());
			}
			System.out.println(result.wasSuccessful());
		}
	}
	
	
	
//	@Test
//	public void test_ese() {
//		JUnitCore junit = new JUnitCore();
//		File dir = new File("src-gen/");
//		File[] files = dir.listFiles();
//		for(File f : files) {
//
//			if(f.toString().contains("_Test_")) {
//				
//				//System.out.println(f);
//				String temp = f.toString().replace("src-gen", "").substring(1).replace(".java", "");
//				//System.out.println(temp);
//				junit.addListener(new TextListener(System.out));
//				junit.run(temp.getClass());
//				
//				//org.junit.runner.Result result1 = JUnitCore.runClasses(f.getClass());
//				//
//				//junit.run(temp.class);
//			}
//		}

		
		//junit.addListener(new TextListener(System.out));
		//junit.run(Rubrica_Test_1.class);

