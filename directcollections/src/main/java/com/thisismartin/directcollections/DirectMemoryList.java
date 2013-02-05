package com.thisismartin.directcollections;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.directmemory.cache.CacheService;

/**
 * <br/>
 * <b>FIXME</b>: Not to have rawtypes and unchecked. <br/>
 * <b>TODO</b>: A builder for list instantation - DirectMemoryList.getBuilder
 * (..).build() <br/>
 * <b>NOTE</b>: Does not check for concurrent modification when using Iterators.
 * 
 * @author martin.spasovski
 * 
 */
public class DirectMemoryList implements List<Object> {

	CacheService<String, Object> cacheService;
	private String id;
	private BigInteger size;

	public DirectMemoryList() {
		id = UUID.randomUUID().toString();
		size = BigInteger.ZERO;
	}

	public DirectMemoryList(CacheService<String, Object> cacheService) {
		this();
		this.cacheService = cacheService;
	}

	public int size() {
		return size.intValue();
	}

	public boolean isEmpty() {
		if (size.compareTo(BigInteger.ZERO) == 0) {
			return true;
		}

		return false;
	}

	public boolean contains(Object o) {
		if (indexOf(o) != -1) {
			return true;
		}

		return false;
	}

	public Iterator<Object> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<Object> {
		int cursor = 0; // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such

		public boolean hasNext() {
			return cursor != size.intValue();
		}

		public Object next() {
			int i = cursor;
			if (i >= size.intValue())
				throw new NoSuchElementException();

			cursor = i + 1;
			lastRet = i;
			return DirectMemoryList.this.get(i);
		}

		public void remove() {
			DirectMemoryList.this.remove(lastRet);
			cursor = lastRet;
			lastRet = -1;
		}
	}

	public Object[] toArray() {
		Object[] result = new Object[size.intValue()];
		for (int i = 0; i < size.intValue(); i++) {
			result[i] = this.get(i);
		}
		return result;
	}

	// TODO: test for this method
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		Object[] result = new Object[size.intValue()];
		for (int i = 0; i < size.intValue(); i++) {
			result[i] = this.get(i);
		}
		return (T[]) result;
	}

	public boolean add(Object e) {
		if (cacheService.put(id.concat(":").concat(size.toString()), e) != null) {
			size = size.add(BigInteger.ONE);
			return true;
		}

		return false;
	}

	public boolean remove(Object o) {
		for (int i = 0; i < size.intValue(); i++) {
			if (this.get(i).equals(o)) {
				cacheService.free(id.concat(":").concat(String.valueOf(i)));
				size = size.subtract(BigInteger.ONE);
				return true;
			}
		}

		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object e : c) {
			if (!this.contains(e)) {
				return false;
			}
		}

		return true;
	}

	public boolean addAll(Collection<? extends Object> c) {
		if (c.isEmpty()) {
			return false;
		}

		for (Object record : c) {
			this.add(record);
		}

		return true;
	}

	public boolean addAll(int index, Collection<? extends Object> c) {
		if (c.isEmpty()) {
			return false;
		}

		int i = index;
		for (Object o : c) {
			this.add(i, o);
			i++;
		}

		return true;
	}

	public boolean removeAll(Collection<?> c) {
		for (Object e : c) {
			if (!this.remove(e)) {
				return false;
			}
		}

		return true;
	}

	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		for (int i = 0; i < size.intValue(); i++) {
			cacheService.free(id.concat(":").concat(Integer.toString(i)));
		}
		size = BigInteger.ZERO;
	}

	public Object get(int index) {
		return cacheService.retrieve(id.concat(":").concat(
				String.valueOf(index)));
	}

	public Object set(int index, Object element) {
		rangeCheckForSet(index);
		cacheService.free(id.concat(":").concat(Integer.toString(index)));
		cacheService.put(id.concat(":").concat(Integer.toString(index)),
				element);
		return get(index);
	}

	// add + shift right
	public void add(int index, Object element) {

		rangeCheckForAdd(index);

		if (index < size.intValue()) {
			// do some shifting and then add it
			int origSize = size.intValue();
			size = size.add(new BigInteger("1"));
			for (int i = origSize - 1; i >= index; i--) {
				this.set(i + 1, this.get(i));
			}

			this.set(index, element);

		} else if (index == size.intValue()) {
			// just add it on the end of the list
			this.add(element);
		}
	}

	public Object remove(int index) {
		rangeCheckForRemove(index);

		Object removed = this.get(index);

		if (index < size.intValue() - 1) {
			// remove the element and do shifting
			for (int i = index; i < size.intValue() - 1; i++) {
				this.set(i, this.get(i + 1));
			}
		}
		size = size.subtract(new BigInteger("1"));
		cacheService.free(id.concat(":").concat(
				Integer.toString(size.intValue())));

		return removed;
	}

	public int indexOf(Object o) {
		for (int i = 0; i < size.intValue(); i++) {
			if (this.get(i).equals(o)) {
				return i;
			}
		}

		return -1;
	}

	public int lastIndexOf(Object o) {
		for (int i = size.intValue() - 1; i >= 0; i--) {
			if (this.get(i).equals(o)) {
				return i;
			}
		}

		return -1;
	}

	public ListIterator<Object> listIterator() {
		return new ListItr(0);
	}

	public ListIterator<Object> listIterator(int index) {
		return new ListItr(index);
	}

	private class ListItr extends Itr implements ListIterator<Object> {

		ListItr(int index) {
			super();
			cursor = index;
		}

		public boolean hasPrevious() {
			return cursor != 0;
		}

		public int nextIndex() {
			return cursor;
		}

		public int previousIndex() {
			return cursor - 1;
		}

		public Object previous() {
			int i = cursor - 1;
			if (i < 0)
				throw new NoSuchElementException();
			cursor = i;
			lastRet = i;
			return DirectMemoryList.this.get(i);
		}

		public void set(Object e) {
			if (lastRet < 0)
				throw new IllegalStateException();
			DirectMemoryList.this.set(lastRet, e);
		}

		public void add(Object e) {
			int i = cursor;
			DirectMemoryList.this.add(e);
			cursor = i + 1;
			lastRet = -1;
		}
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	private void rangeCheckForSet(int index) {
		if (index > size.intValue() && index < 0)
			throw new IndexOutOfBoundsException(index + " < " + size.intValue());
	}

	private void rangeCheckForAdd(int index) {
		if (index >= size.intValue() && index < 0)
			throw new IndexOutOfBoundsException();
	}

	private void rangeCheckForRemove(int index) {
		if (index > size.intValue() && index < 0) {
			throw new IndexOutOfBoundsException();
		}
	}
}
