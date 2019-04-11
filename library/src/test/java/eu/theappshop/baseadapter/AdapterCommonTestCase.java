package eu.theappshop.baseadapter;

import eu.theappshop.baseadapter.adapterv2.Adapter;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public abstract class AdapterCommonTestCase {

  private Adapter<StubVM> adapter;
  private StubVM[] vms = {
      new StubVM(1)
  };

  @Before
  public void setUp() {
    adapter = provideAdapter();
    List<StubVM> vms = Arrays.asList();
  }

  @Test
  public void testContains() {

  }

  @Test
  public void testContainsAll() {

  }

  @Test
  public void testIndexOf() {

  }

  @Test
  public void testLastIndexOf() {

  }

  @Test
  public void testGet() {

  }

  @Test
  public void testVms() {

  }

  @Test
  public void testBinding() {

  }

  protected abstract Adapter<StubVM> provideAdapter();
}
