package eu.theappshop.baseadapter.utils;

import static org.junit.Assert.fail;

public class TestUtils {
  public static <T extends Throwable> void assertThrows(Class<T> expectedException, Block block) {
    try {
      block.run();
      fail("Throwable " + expectedException.getSimpleName() + " is not thrown");
    } catch (Throwable ex) {
      if (!ex.getClass().equals(expectedException)) {
        throw ex;
      }
    }
  }

  public interface Block {
    void run();
  }
}
