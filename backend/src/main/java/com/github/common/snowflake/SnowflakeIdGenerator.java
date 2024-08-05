package com.github.common.snowflake;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;

/**
 * @author Gudao
 * @since 2024/8/5
 */
@Component
public class SnowflakeIdGenerator implements IdentifierGenerator {

	@Serial
	private static final long serialVersionUID = -1501401727192200383L;

	@Value("${snowflake.workerId}")
	private long workId;

	private final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(workId);

	@Override
	public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
		return snowflakeIdWorker.nextId();
	}

}
