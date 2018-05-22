package com.ariso.benchmark.bloomfilter;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.Collection;

import com.ariso.playground.util.Murmur3;

/**
 
 */
public class SimpleBitSetBloomFilter {
	private BitSet bitset;
	private int bitSetSize;
	private double bitsPerElement;
	private int expectedNumberOfFilterElements; // expected (maximum) number of elements to be added
	private int numberOfAddedElements; // number of elements actually added to the Bloom filter
	private int k; // number of hash functions

	public SimpleBitSetBloomFilter() {
		this(10000, 2, 1);
	}

	/**
	 * Constructs an empty Bloom filter. The total length of the Bloom filter will
	 * be c*n.
	 *
	 * @param c
	 *            is the number of bits used per element.
	 * @param n
	 *            is the expected number of elements the filter will contain.
	 * @param k
	 *            is the number of hash functions used.
	 */
	public SimpleBitSetBloomFilter(double c, int n, int k) {
		this.expectedNumberOfFilterElements = n;
		this.k = k;
		this.bitsPerElement = c;
		this.bitSetSize = (int) Math.ceil(c * n);
		numberOfAddedElements = 0;
		this.bitset = new BitSet(bitSetSize);
	}

	/**
	 * Constructs an empty Bloom filter. The optimal number of hash functions (k) is
	 * estimated from the total size of the Bloom and the number of expected
	 * elements.
	 *
	 * @param bitSetSize
	 *            defines how many bits should be used in total for the filter.
	 * @param expectedNumberOElements
	 *            defines the maximum number of elements the filter is expected to
	 *            contain.
	 */
	public SimpleBitSetBloomFilter(int bitSetSize, int expectedNumberOElements) {
		this(bitSetSize / (double) expectedNumberOElements, expectedNumberOElements,
				(int) Math.round((bitSetSize / (double) expectedNumberOElements) * Math.log(2.0)));
	}

	/**
	 * Constructs an empty Bloom filter with a given false positive probability. The
	 * number of bits per element and the number of hash functions is estimated to
	 * match the false positive probability.
	 *
	 * @param falsePositiveProbability
	 *            is the desired false positive probability.
	 * @param expectedNumberOfElements
	 *            is the expected number of elements in the Bloom filter.
	 */
	public SimpleBitSetBloomFilter(double falsePositiveProbability, int expectedNumberOfElements) {

		this(Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2))) / Math.log(2), // c = k / ln(2)
				expectedNumberOfElements, (int) Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2)))); // k =
																													// ceil(-log_2(false
																													// prob.))
	}

	/**
	 * Construct a new Bloom filter based on existing Bloom filter data.
	 *
	 * @param bitSetSize
	 *            defines how many bits should be used for the filter.
	 * @param expectedNumberOfFilterElements
	 *            defines the maximum number of elements the filter is expected to
	 *            contain.
	 * @param actualNumberOfFilterElements
	 *            specifies how many elements have been inserted into the
	 *            <code>filterData</code> BitSet.
	 * @param filterData
	 *            a BitSet representing an existing Bloom filter.
	 */
	public SimpleBitSetBloomFilter(int bitSetSize, int expectedNumberOfFilterElements, int actualNumberOfFilterElements,
			BitSet filterData) {
		this(bitSetSize, expectedNumberOfFilterElements);
		this.bitset = filterData;
		this.numberOfAddedElements = actualNumberOfFilterElements;
	}

	private static final int BITS_PER_BLOOM = 3;
	private static final int BLOOM_BYTES = 64;

	BitSet mask = new BitSet(BLOOM_BYTES * 8);
	int[] counters = new int[BLOOM_BYTES * 8];

	/* BitSet初始分配2^24个bit */
	private static final int DEFAULT_SIZE = 1 << 25;

	private BitSet bits = new BitSet(DEFAULT_SIZE);

	// 将字符串标记到bits中
	public synchronized void add(String value) {

		int hashCode = Murmur3.hash32(value.getBytes());

		bits.set(hashCode, true);

	}

	// 判断字符串是否已经被bits标记
	public boolean contains(String value) {
		if (value == null) {
			return false;
		}
		int hashCode = Murmur3.hash32(value.getBytes());

		boolean ret = true;

		ret = ret && bits.get(hashCode);

		return ret;
	}

}