package eu.theappshop.baseadapter.general;

import eu.theappshop.baseadapter.StubVM;
import eu.theappshop.baseadapter.adapterv2.Adapter;
import eu.theappshop.baseadapter.utils.ListUtils;
import eu.theappshop.baseadapter.utils.SerializationUtils;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AdapterSerializationTestCase {

  private Adapter<StubVM> adapter;

  @Before
  public void setUp() {
    adapter = getAdapter(ListUtils.generateList(10, new StubVM.IntMapper()));
  }

  @Test
  public void testConsistency() throws IOException, ClassNotFoundException {
    byte[] serialized1 = SerializationUtils.serialize(adapter);
    byte[] serialized2 = SerializationUtils.serialize(adapter);

    Object deserialized1 = SerializationUtils.deserialize(serialized1);
    Object deserialized2 = SerializationUtils.deserialize(serialized2);
    assertEquals(deserialized1, deserialized2);
    assertEquals(adapter, deserialized1);
    assertEquals(adapter, deserialized2);
  }

  @Test
  public void testInstance() throws IOException, ClassNotFoundException {
    byte[] serialized = SerializationUtils.serialize(adapter);
    Object deserialized = SerializationUtils.deserialize(serialized);

    assertTrue(deserialized instanceof Adapter<?>);
    assertTrue(((Adapter<?>) deserialized).vms().get(0) instanceof StubVM);
    assertEquals(adapter, deserialized);
  }

  protected abstract Adapter<StubVM> getAdapter(List<StubVM> vms);
}
