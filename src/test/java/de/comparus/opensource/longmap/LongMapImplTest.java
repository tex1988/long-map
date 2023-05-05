package de.comparus.opensource.longmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LongMapImplTest {
    private LongMap<String> map;

    @Before
    public void setUp() {
        map = new LongMapImpl<>();
    }

    @Test
    public void testPut() {
        String value1 = "value1";
        String value2 = "value2";

        String expectedValue1 = map.put(1, value1);

        assertEquals(expectedValue1, value1);
        assertEquals(value1, map.get(1));
        assertEquals(1, map.size());

        String expectedValue2 = map.put(2, value2);

        assertEquals(expectedValue2, value2);
        assertEquals(value2, map.get(2));
        assertEquals(2, map.size());
    }

    @Test
    public void testGet() {
        String value = "value";
        map.put(1, value);

        assertEquals(value, map.get(1));
        assertNull(map.get(3));
    }

    @Test
    public void testRemove() {
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        map.put(1, value1);
        map.put(2, value2);
        map.put(3, value3);

        String expectedValue1 = map.remove(1);

        assertEquals(expectedValue1, value1);
        assertNull(map.get(1));
        assertEquals(2, map.size());

        String expectedValue2 = map.remove(2);

        assertEquals(expectedValue2, value2);
        assertNull(map.get(2));
        assertEquals(1, map.size());

        String expectedValue3 = map.remove(4);

        assertNull(expectedValue3);
        assertEquals(1, map.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(map.isEmpty());

        String value = "value";
        map.put(1, value);

        assertFalse(map.isEmpty());
    }

    @Test
    public void testContainsKey() {
        String value = "value";
        map.put(1, value);

        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(2));
    }

    @Test
    public void testContainsValue() {
        String value1 = "value1";
        String value2 = "value2";
        map.put(1, value1);

        assertTrue(map.containsValue(value1));
        assertFalse(map.containsValue(value2));
    }

    @Test
    public void testKeys() {
        long key1 = 1;
        long key2 = 2;
        map.put(key1, "value");
        map.put(key2, "value");

        long[] keys = map.keys();

        assertEquals(2, keys.length);
        assertEquals(key1, keys[0]);
        assertEquals(key2, keys[1]);
    }

    @Test
    public void testValues() {
        Object[] values = map.values();
        assertEquals(0, values.length);

        String value1 = "value1";
        String value2 = "value2";
        map.put(1, value1);
        map.put(2, value2);

        values = map.values();

        assertEquals(2, values.length);
        assertEquals(value1, values[0]);
        assertEquals(value2, values[1]);
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());

        map.put(1, "value");
        assertEquals(1, map.size());

        map.put(2, "value");
        assertEquals(2, map.size());

        map.remove(1);
        assertEquals(1, map.size());
    }

    @Test
    public void testClear() {
        String value1 = "value";
        String value2 = "value";
        map.put(1, value1);
        map.put(2, value2);

        map.clear();

        assertEquals(0, map.size());
        assertNull(map.get(1));
        assertNull(map.get(2));
    }
}