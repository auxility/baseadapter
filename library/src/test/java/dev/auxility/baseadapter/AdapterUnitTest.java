package dev.auxility.baseadapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdapterSerializationTestCase.class,
    AdapterCommonTestCase.class,
    AdapterTestCase.class
})
public class AdapterUnitTest {

}
