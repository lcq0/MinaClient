package com.kingdom.park.mina.util;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Assert 断言，用于常用条件的判断
 *
 * @author Mark version 1.0 2009-6-8
 */
public final class Assert {

	private Assert() {
	}

	public static boolean isNull(Object... objects) {
		if (objects == null)
			return true;
		for (Object o : objects) {
			if (o == null)
				return true;
			if (o.getClass() == String.class) {
				String data = (String) o;
				data=data.replaceAll("　", " ");
				if (data == null || data.equals("") || data.trim().equals("")
						|| data.equals(" ")||data.equals("NULL")||data.equals("null"))
					return true;
			}
		}
		return false;
	}

	public static final boolean isInstance(final Object object,
			final Class<?>... classes) {
		if (isNull(object, classes)) {
			return false;
		}

		for (Class<?> c : classes) {
			if (!isNull(c) && c.isInstance(object)) {
				return true;
			}
		}

		return false;
	}

	public static final boolean equals(final Object left, final Object right) {
		return left == null ? right == null : ((right != null) && left
				.equals(right));
	}

	public static final boolean equals(final Object[] leftArray,
			final Object[] rightArray) {
		if (leftArray == rightArray) {
			return true;
		}

		if (leftArray == null) {
			return (rightArray == null);
		} else if (rightArray == null) {
			return false;
		}

		if (leftArray.length != rightArray.length) {
			return false;
		}

		for (int i = 0; i < leftArray.length; i++) {
			final Object left = leftArray[i];
			final Object right = rightArray[i];
			final boolean equal = (left == null) ? (right == null) : (left
					.equals(right));
			if (!equal) {
				return false;
			}
		}

		return true;
	}


	/**
	 * 验证数字是否为0
	 *
	 * @param o
	 * @return
	 */
	public static boolean isZero(Object o) {
		if (Assert.isNull(o))
			return true;
		if (o.getClass() == Double.class) {
			return (Double) o == 0D;
		} else if (o.getClass() == Long.class) {
			return (Long) o == 0L;
		} else if (o.getClass() == BigDecimal.class) {
			return ((BigDecimal) o).intValue() == 0;
		} else if (o.getClass() == Integer.class) {
			return (Integer) o == 0;
		}
		return false;
	}

}
