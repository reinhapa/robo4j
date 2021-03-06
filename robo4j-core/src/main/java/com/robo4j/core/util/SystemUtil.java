/*
 * Copyright (c) 2014, 2017, Marcus Hirt, Miroslav Wengner
 * 
 * Robo4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robo4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robo4J. If not, see <http://www.gnu.org/licenses/>.
 */
package com.robo4j.core.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.robo4j.core.RoboContext;
import com.robo4j.core.RoboReference;

/**
 * Some useful little utilities.
 * 
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public final class SystemUtil {
	private static final String BREAK = "\n";
	private static final String SLASH = "/";

	private SystemUtil() {
		// no instances
	}

	public static final Comparator<RoboReference<?>> ID_COMPARATOR = new Comparator<RoboReference<?>>() {
		@Override
		public int compare(RoboReference<?> o1, RoboReference<?> o2) {
			return o1.getId().compareTo(o2.getId());
		}
	};

	public static String printStateReport(RoboContext ctx) {
		StringBuilder builder = new StringBuilder();
		List<RoboReference<?>> references = new ArrayList<>(ctx.getUnits());
		references.sort(ID_COMPARATOR);
		//formatter:off
		builder.append("RoboSystem state ").append(ctx.getState().getLocalizedName())
				.append(BREAK)
				.append("================================================")
				.append(BREAK);
		for (RoboReference<?> reference : references) {
			builder.append(String.format("    %-25s   %13s", reference.getId(), reference.getState().getLocalizedName()))
					.append(BREAK);
		}
		//formatter:on
		return builder.toString();
	}

	public static String printSocketEndPoint(RoboReference<?> point, RoboReference<?> codecUnit){
		final int port = point.getConfiguration().getInteger("port", 0);
		StringBuilder sb = new StringBuilder();
		//@formatter:off
		sb.append("RoboSystem end-points:")
				.append(BREAK)
				.append("================================================")
				.append(BREAK);
		sb.append("http://<IP>")
				.append(port)
				.append(SLASH)
				.append(codecUnit.getId())
				.append(BREAK)
				.append("http methods GET and POST")
				.append(BREAK)
				.append("GET: information about the POST request")
				.append(BREAK)
				.append("POST: uses values of ")
				.append(codecUnit.getMessageType().getSimpleName())
				.append(" object")
				.append(BREAK);
				sb.append("==============================================")
		.append(BREAK);
		//@formatter:on
		return sb.toString();
	}
}
