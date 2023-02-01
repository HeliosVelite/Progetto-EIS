package myTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestListConstructor.class,
	TestListInspection.class,
	TestListIteration.class,
	TestListManipulation.class,
	TestSublist.class,
})

public class ListAdapterTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}