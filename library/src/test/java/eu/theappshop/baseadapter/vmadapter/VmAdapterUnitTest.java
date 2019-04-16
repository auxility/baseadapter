package eu.theappshop.baseadapter.vmadapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    VmAdapterSerializationTestCase.class,
    VmAdapterCommonTestCase.class,
    VmAdapterCRUDTestCase.class
})
public class VmAdapterUnitTest {

}
