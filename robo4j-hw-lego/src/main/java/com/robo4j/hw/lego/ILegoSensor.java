/*
 * Copyright (C) 2014, 2017. Miroslav Wengner, Marcus Hirt
 * This LegoSensor.java  is part of robo4j.
 * module: robo4j-hw-lego
 *
 * robo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * robo4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with robo4j .  If not, see <http://www.gnu.org/licenses/>.
 */

package com.robo4j.hw.lego;


import com.robo4j.hw.lego.enums.DigitalPortEnum;
import com.robo4j.hw.lego.enums.SensorTypeEnum;

/**
 *
 * Basic functionality of any LegoMindstorm Sensor
 *
 * @author Marcus Hirt (@hirt)
 * @author Miro Wengner (@miragemiko)
 */
public interface ILegoSensor {

	DigitalPortEnum getPort();

	SensorTypeEnum getType();

	String getData();

	void activate(boolean status);

	void close();

}
