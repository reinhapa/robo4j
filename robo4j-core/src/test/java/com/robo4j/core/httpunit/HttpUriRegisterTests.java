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

package com.robo4j.core.httpunit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import com.robo4j.core.RoboSystem;
import com.robo4j.core.httpunit.test.HttpCommandTestController;

/**
 * target is defined by the unit methods are specified GET, POST options are
 * generated by the Unit accepted Type T POST is written in the JSON
 *
 * @author Marcus Hirt (@hirt)
 * @author Miro Wengner (@miragemiko)
 */
public class HttpUriRegisterTests {
	private static final String TARGET_UNIT = "controller";
	private static final String[] METHODS = { "GET", "POST" };

	@Test
	public void registerSimpleTest() {
		/* tested system configuration */
		RoboSystem system = new RoboSystem();

		HttpUriRegister register = HttpUriRegister.getInstance();

		register.addNote(TARGET_UNIT, METHODS[0]);
		register.addNote(TARGET_UNIT, METHODS[1]);

		HttpCommandTestController ctrl = new HttpCommandTestController(system, TARGET_UNIT);
		register.addUnitToNote(TARGET_UNIT, ctrl);
		assertNotNull(register.getMethodsBytPath(TARGET_UNIT));
		assertEquals(register.getMethodsBytPath(TARGET_UNIT).getUnit(), ctrl);
		assertTrue(register.getMethodsBytPath(TARGET_UNIT).getMethods().containsAll(Arrays.asList(METHODS)));

	}

}
