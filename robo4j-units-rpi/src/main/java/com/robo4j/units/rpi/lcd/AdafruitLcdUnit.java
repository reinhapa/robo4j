/*
 * Copyright (c) 2014, 2017, Miroslav Wengner, Marcus Hirt
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
package com.robo4j.units.rpi.lcd;

import java.io.IOException;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.robo4j.core.LifecycleState;
import com.robo4j.core.RoboContext;
import com.robo4j.core.RoboResult;
import com.robo4j.core.RoboUnit;
import com.robo4j.core.configuration.Configuration;
import com.robo4j.core.logging.SimpleLoggingUtil;
import com.robo4j.hw.rpi.i2c.adafruitlcd.AdafruitLcd;
import com.robo4j.hw.rpi.i2c.adafruitlcd.LcdFactory;
import com.robo4j.hw.rpi.i2c.adafruitlcd.impl.RealLcd.Direction;
import com.robo4j.units.rpi.I2CEndPoint;
import com.robo4j.units.rpi.I2CRegistry;

/**
 * A {@link RoboUnit} for the Adafruit 16x2 character LCD shield.
 * 
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 * @since 17.12.2016
 *
 */
public class AdafruitLcdUnit extends I2CRoboUnit<String> {
	private AdafruitLcd lcd;

	public AdafruitLcdUnit(RoboContext context, String id) {
		super(context, id);
	}

	static AdafruitLcd getLCD(int bus, int address) throws IOException, UnsupportedBusNumberException {
		Object lcd = I2CRegistry.getI2CDeviceByEndPoint(new I2CEndPoint(bus, address));
		if (lcd == null) {
			lcd = LcdFactory.createLCD(bus, address);
			I2CRegistry.registerI2CDevice(lcd, new I2CEndPoint(bus, address));
		}
		return (AdafruitLcd) lcd;
	}

	@Override
	public void initialize(Configuration configuration) throws Exception {
		super.initialize(configuration);
		lcd = getLCD(getBus(), getAddress());
		setState(LifecycleState.INITIALIZED);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoboResult<String, ?> onMessage(Object message) {
		try {
			if (message instanceof LcdMessage) {
				processLcdMessage((LcdMessage) message);
			} else if (message instanceof String) {
				lcd.setText((String) message);
			}
		} catch (Exception e) {
			SimpleLoggingUtil.debug(getClass(), "Could not accept message" + message.toString(), e);
		}

		return super.onMessage(message);
	}

	/**
	 * @param message
	 * @throws IOException
	 */
	private void processLcdMessage(LcdMessage message) throws IOException {
		switch (message.getType()) {
		case CLEAR:
			lcd.clear();
			break;
		case DISPLAY_ENABLE:
			String enablement = message.getText();
			if (enablement.equals("true")) {
				lcd.setDisplayEnabled(true);
			} else if (enablement.equals("false")) {
				lcd.setDisplayEnabled(false);
			} else {
				SimpleLoggingUtil.error(getClass(), "Display enablement " + enablement + " is unknown");
			}
			break;
		case SCROLL:
			String direction = message.getText();
			if (direction.equals("left")) {
				lcd.scrollDisplay(Direction.LEFT);
			} else if (direction.equals("right")) {
				lcd.scrollDisplay(Direction.RIGHT);
			} else {
				SimpleLoggingUtil.error(getClass(), "Scroll direction " + direction + " is unknown");
			}
			break;
		case SET_TEXT:
			if (message.getColor() != null) {
				lcd.setBacklight(message.getColor());
			}
			if (message.getText() != null) {
				lcd.setText(message.getText());
			}
			break;
		case STOP:
			lcd.stop();
			break;
		default:
			SimpleLoggingUtil.error(getClass(), message.getType() + " not supported!");
			break;
		}
	}
}