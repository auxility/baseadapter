package eu.theappshop.baseadapter;

import eu.theappshop.baseadapter.adapterv2.VmAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VmAdapterSerializationTestCase {

  private VmAdapter<StubVM> adapter;

  @Before
  public void setUp() {
    List<StubVM> vms = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      vms.add(new StubVM(i));
    }
    adapter = new VmAdapter<>(vms);
  }

  @Test
  public void testConsistency() throws IOException, ClassNotFoundException {
    byte[] serialized1 = SerializationUtils.serialize(adapter);
    byte[] serialized2 = SerializationUtils.serialize(adapter);

    Object deserialized1 = SerializationUtils.deserialize(serialized1);
    Object deserialized2 = SerializationUtils.deserialize(serialized2);
    Assert.assertEquals(deserialized1, deserialized2);
    Assert.assertEquals(adapter, deserialized1);
    Assert.assertEquals(adapter, deserialized2);
  }
}
