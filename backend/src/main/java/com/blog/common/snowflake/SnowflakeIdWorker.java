package com.blog.common.snowflake;

/**
 * 雪花算法ID，16位实现
 *
 * @author Gudao
 * @since 2024/8/5
 */
public class SnowflakeIdWorker {

	/**
	 * 起始时间戳
	 */
	private final long twepoch = 1672531200L;

	/**
	 * 各部分占用位数
	 */
	private final long workerIdBits = 5L;

	private final long sequenceBits = 6L;

	/**
	 * 最大值
	 */
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	/**
	 * 位移
	 */
	private final long workerIdShift = sequenceBits;

	private final long timestampLeftShift = sequenceBits + workerIdBits;

	private long workerId;

	private long sequence = 0L;

	private long lastTimestamp = -1L;

	public SnowflakeIdWorker(long workerId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		this.workerId = workerId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d seconds",
					lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextSecond(lastTimestamp);
			}
		}
		else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
	}

	protected long tilNextSecond(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis() / 1000;
	}

}
