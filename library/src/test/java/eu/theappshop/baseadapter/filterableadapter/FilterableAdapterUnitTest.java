package eu.theappshop.baseadapter.filterableadapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    FilterableAdapterCommonTestCase.class,
    FilterableAdapterSerializationTestCase.class,
    FilterableAdapterTestCase.class
})
public class FilterableAdapterUnitTest {
}
