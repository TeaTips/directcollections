package com.thisismartin.directcollections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.directmemory.DirectMemory;
import org.apache.directmemory.cache.CacheService;
import org.apache.directmemory.serialization.kryo.KryoSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thisismartin.directcollections.DirectMemoryList;
import com.thisismartin.directcollections.helpers.TestPOJO;

/**
 * 
 * @author martin.spasovski
 * @version 0.1
 * 
 */
public class DirectMemoryListWiderTests {
	KryoSerializer ser = new KryoSerializer();

	CacheService<String, Object> cacheService = new DirectMemory<String, Object>()
			.setInitialCapacity(100).setNumberOfBuffers(10).setSize(10000000)
			.setSerializer(ser).newCacheService();
	Logger logger = LoggerFactory
			.getLogger(DirectMemoryListFunctionalityTests.class);

	@SuppressWarnings("rawtypes")
	List implementation = new DirectMemoryList(cacheService);
	List<TestPOJO> pojoTestReference = new ArrayList<TestPOJO>();
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

	@SuppressWarnings("unchecked")
	@Test
	public void testSeveralListFunctionalities() {

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

		reference.remove(6);
		implementation.remove(6);

		reference.addAll(0, newData);
		implementation.addAll(0, newData);

		reference.remove(14);
		implementation.remove(14);

		for (int i = 0; i < reference.size(); i++) {
			logger.info("assertEquals {" + reference.get(i) + " : "
					+ implementation.get(i) + "}");
			Assert.assertEquals(reference.get(i), implementation.get(i));
		}

		logger.info("assertEquals {" + reference.size() + " : "
				+ implementation.size() + "}");
		Assert.assertEquals(reference.size(), implementation.size());

		logger.info("assertEquals {" + reference.toArray() + " : "
				+ implementation.toArray() + "}");
		Assert.assertArrayEquals(reference.toArray(), implementation.toArray());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPOJO() {

		TestPOJO someData = new TestPOJO();
		someData.setNumerOne(0000011L);
		someData.setSecondDate(new Date(1252197830676L));
		someData.setSomeDate(new Date());
		someData.setSomeUUID(UUID.randomUUID());
		someData.setSecondUUID(UUID.randomUUID());

		pojoTestReference.add(someData);
		implementation.add(someData);

		logger.info("assertEquals {" + pojoTestReference.get(0).toString()
				+ " : " + implementation.get(0).toString() + "}");
		Assert.assertEquals(pojoTestReference.get(0).toString(), implementation
				.get(0).toString());
	}

}
