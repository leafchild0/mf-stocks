package com.poc.authserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author victor
 * @date 12.03.2020
 */
public interface HasLogger {

	default Logger getLogger()
	{
		return LoggerFactory.getLogger(getClass());
	}
}
