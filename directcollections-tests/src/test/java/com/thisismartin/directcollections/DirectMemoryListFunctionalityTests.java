package com.thisismartin.directcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.directmemory.DirectMemory;
import org.apache.directmemory.cache.CacheService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Testing all the implemented {@link List} methods in {@link DirectMemoryList}
 * 
 * @author martin.spasovski
 * @version 0.1
 * 
 */
public class DirectMemoryListFunctionalityTests {

	CacheService<String, Object> cacheService;
	Logger logger = LoggerFactory
			.getLogger(DirectMemoryListFunctionalityTests.class);

	@SuppressWarnings("rawtypes")
	List implementation = new DirectMemoryList(cacheService);
	List<String> reference = new ArrayList<String>();

	@Before
	public void init() {
		cacheService = new DirectMemory<String, Object>()
				.setInitialCapacity(100).setNumberOfBuffers(10).setSize(1000)
				.newCacheService();

		reference = new ArrayList<String>();
		implementation = new DirectMemoryList(cacheService);
	}

	@SuppressWarnings("unchecked")
	private void fillTenItems() {
		for (int i = 0; i < 10; i++) {
			reference.add(String.valueOf(i));
			implementation.add(String.valueOf(i));
		}
	}

	@Test
	public void testAdd() {
		fillTenItems();

		for (int i = 0; i < 10; i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSet() {
		fillTenItems();

		reference.set(5, "b");
		implementation.set(5, "b");

		for (int i = 0; i < 10; i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}
	}

	@Test
	public void testSize() {
		fillTenItems();

		logger.info("assertEquals {" + reference.size() + " : "
				+ implementation.size() + "}");
		Assert.assertEquals(reference.size(), implementation.size());
	}

	@Test
	public void testIsEmpty() {

		logger.info("assertEquals {" + reference.isEmpty() + " : " + true + "}");
		logger.info("assertEquals {" + reference.isEmpty() + " : "
				+ implementation.isEmpty() + "}");
		Assert.assertEquals(reference.isEmpty(), true);
		Assert.assertEquals(reference.isEmpty(), implementation.isEmpty());

		fillTenItems();
		logger.info("assertEquals {" + reference.isEmpty() + " : " + false
				+ "}");
		logger.info("assertEquals {" + reference.isEmpty() + " : " + false
				+ "}");
		Assert.assertEquals(reference.isEmpty(), implementation.isEmpty());
		Assert.assertEquals(reference.isEmpty(), implementation.isEmpty());
	}

	@Test
	public void testContains() {
		fillTenItems();

		logger.info("assertEquals {" + reference.contains("5") + " : "
				+ implementation.contains("5") + "}");
		Assert.assertEquals(reference.contains("5"),
				implementation.contains("5"));

		logger.info("assertEquals {" + reference.contains("11") + " : "
				+ implementation.contains("11") + "}");
		Assert.assertEquals(reference.contains("11"),
				implementation.contains("11"));
	}

	@Test
	public void testIterator() {

		fillTenItems();

		Iterator<String> refIterator = reference.iterator();
		@SuppressWarnings("rawtypes")
		Iterator implIterator = implementation.iterator();
		String refStr = "";
		String implStr = "";

		refIterator.next();
		refIterator.remove();
		implIterator.next();
		implIterator.remove();

		while (refIterator.hasNext()) {
			refStr = refStr.concat((String) refIterator.next());
		}
		while (implIterator.hasNext()) {
			implStr = implStr.concat((String) implIterator.next());
		}
		logger.info("assertEquals {" + refStr + " : " + implStr + "}");
		Assert.assertEquals(implStr, implStr);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddAllWithIndex() {

		fillTenItems();

		for (int i = 0; i < 10; i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}

		List<String> newData = new ArrayList<String>();
		newData.add("a");
		newData.add("b");
		newData.add("c");

		reference.addAll(5, newData);
		implementation.addAll(5, newData);

		for (int i = 0; i < reference.size(); i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}

		logger.info("assertEquals {" + reference.size() + " : "
				+ implementation.size() + "}");
		Assert.assertEquals(reference.size(), implementation.size());
	}

	@Test
	public void toArrayTest() {
		fillTenItems();

		logger.info("assertEquals {" + reference.toArray() + " : "
				+ implementation.toArray() + "}");
		Assert.assertArrayEquals(reference.toArray(), implementation.toArray());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemoveAndAdd() {
		fillTenItems();

		reference.remove(3);
		implementation.remove(3);

		reference.add("d");
		implementation.add("d");

		for (int i = 0; i < reference.size(); i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}

	}

	@Test
	public void testCleanAndSize() {

		fillTenItems();

		reference.clear();
		implementation.clear();

		logger.info("assertEquals {" + reference.size() + " : "
				+ implementation.size() + "}");
		Assert.assertEquals(reference.size(), implementation.size());

		logger.info("assertEquals {" + reference.isEmpty() + " : "
				+ implementation.isEmpty() + "}");
		Assert.assertEquals(reference.isEmpty(), implementation.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListIterator() {
		fillTenItems();

		reference.set(3, "z");
		reference.set(6, "a");
		reference.set(7, "j");

		implementation.set(3, "z");
		implementation.set(6, "a");
		implementation.set(7, "j");

		Collections.sort(reference);
		Collections.sort(implementation);

		for (int i = 0; i < reference.size(); i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}
	}

	// TODO: test listIterator(int index)

}
