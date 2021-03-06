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
package com.robo4j.core;

import com.robo4j.core.client.util.RoboClassLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Test(s) for the builder.
 *
 * @author Marcus Hirt (@hirt)
 * @author Miroslav Wengner (@miragemiko)
 */
public class RoboBuilderTests {
    private static final int MESSAGES = 1000;

    @Test
    public void testParsingFile() throws RoboBuilderException, InterruptedException, ExecutionException {
        RoboBuilder builder = new RoboBuilder();
        builder.add(RoboClassLoader.getInstance().getResource("test.xml"));
        RoboContext system = builder.build();
        Assert.assertEquals(system.getState(), LifecycleState.UNINITIALIZED);
        system.start();
        Assert.assertTrue(system.getState() == LifecycleState.STARTING || system.getState() == LifecycleState.STARTED);

        /* descriptor is similar for both units */
        final DefaultAttributeDescriptor<Integer> descriptor = DefaultAttributeDescriptor
                .create(Integer.class, "getNumberOfSentMessages");

        RoboReference<String> producer = system.getReference("producer");
        Assert.assertNotNull(producer);
        for (int i = 0; i < MESSAGES; i++) {
            producer.sendMessage("sendRandomMessage");
        }
        Assert.assertEquals(MESSAGES, (int) producer.getAttribute(descriptor).get());

        RoboReference<String> consumer = system.getReference("consumer");
        Assert.assertNotNull(consumer);

        //TODO: is this correct ?
        synchronized ( consumer.getAttribute(descriptor).get()){
            int receivedMessages = consumer.getAttribute(descriptor).get();
            Assert.assertEquals(MESSAGES, receivedMessages);
        }


        system.stop();
        system.shutdown();
    }

    @Test
    public void testAddingNonUnique() {
        RoboBuilder builder = new RoboBuilder();
        boolean gotException = false;
        try {
            builder.add(RoboClassLoader.getInstance().getResource("double.xml"));
        } catch (RoboBuilderException e) {
            gotException = true;
        }
        Assert.assertTrue(gotException);
    }

    @Test
    public void testComplexConfiguration() throws RoboBuilderException {
        RoboBuilder builder = new RoboBuilder();
        builder.add(RoboClassLoader.getInstance().getResource("testsubconfig.xml"));
        RoboContext system = builder.build();
        system.start();
        RoboReference<Object> reference = system.getReference("consumer");
        Assert.assertNotNull(reference);
    }
  
}
