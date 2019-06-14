package com.skiff2011.baseadapter.general;

import com.skiff2011.baseadapter.AbstractItemAdapter;
import com.skiff2011.baseadapter.StubItem;
import com.skiff2011.baseadapter.utils.ListUtils;
import com.skiff2011.baseadapter.utils.SerializationUtils;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AdapterSerializationTestCase {

  private AbstractItemAdapter<StubItem> adapter;

  @Before
  public void setUp() {
    adapter = getAdapter(ListUtils.generateList(10, new StubItem.IntMapper()));
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

    assertTrue(deserialized instanceof AbstractItemAdapter<?>);
    assertTrue(((AbstractItemAdapter<?>) deserialized).vms().get(0) instanceof StubItem);
    assertEquals(adapter, deserialized);
  }

  protected abstract AbstractItemAdapter<StubItem> getAdapter(List<StubItem> vms);
}
