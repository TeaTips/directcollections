package com.thisismartin.directcollections.helpers;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TestPOJO implements Serializable {

	private static final long serialVersionUID = -7635192691885019080L;

	Date someDate;

	Date secondDate;

	long numerOne;

	UUID someUUID;

	UUID secondUUID;

	private static int start = 3;

	public Date getSomeDate() {
		return someDate;
	}

	public void setSomeDate(Date someDate) {
		this.someDate = someDate;
	}

	public Date getSecondDate() {
		return secondDate;
	}

	public void setSecondDate(Date secondDate) {
		this.secondDate = secondDate;
	}

	public long getNumerOne() {
		return numerOne;
	}

	public void setNumerOne(long numerOne) {
		this.numerOne = numerOne;
	}

	public static int getStart() {
		return start;
	}

	public static void setStart(int start) {
		TestPOJO.start = start;
	}

	public UUID getSomeUUID() {
		return someUUID;
	}

	public void setSomeUUID(UUID someUUID) {
		this.someUUID = someUUID;
	}

	public UUID getSecondUUID() {
		return secondUUID;
	}

	public void setSecondUUID(UUID secondUUID) {
		this.secondUUID = secondUUID;
	}

	@Override
	public String toString() {
		return "TestPOJO [getSomeDate()=" + getSomeDate().getTime()
				+ ", getSecondDate()=" + getSecondDate().getTime() + ", getNumerOne()="
				+ getNumerOne() + ", getSomeUUID()=" + getSomeUUID()
				+ ", getSecondUUID()=" + getSecondUUID() + "]";
	}

}
