/*
 *    JImageIO-extension - OpenSource Java Image translation Library
 *    http://www.geo-solutions.it/
 *    (C) 2007, GeoSolutions
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.imageio.plugins.jp2k;

import java.util.logging.Logger;

import kdu_jni.KduException;
import kdu_jni.Kdu_message;

/**
 * Class used to handle kakadu system messages.
 */

class Kdu_sysout_message extends Kdu_message {
	private static Logger LOGGER = Logger
			.getLogger("it.geosolutions.imageio.jp2k");

	private boolean raiseExceptionOnEndOfMessage;

	// If the raiseException parameter is true, throws an exeception
	// after printing a message.
	public Kdu_sysout_message(boolean raiseException) {
		this.raiseExceptionOnEndOfMessage = raiseException;
	}

	public void Put_text(String text) { // Implements the C++ callback function
		// `kdu_message::put_text'
		LOGGER.info(text);
	}

	public void Flush(boolean endOfMessage) throws KduException { // Implements
		// the C++
		// callback
		// function
		// `kdu_message::flush'.
		if (endOfMessage && raiseExceptionOnEndOfMessage)
			throw new KduException("In `Kdu_sysout_message'.");
	}

}