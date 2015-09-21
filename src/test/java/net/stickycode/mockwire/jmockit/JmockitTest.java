package net.stickycode.mockwire.jmockit;

import org.junit.Test;

import mockit.Mocked;
import mockit.Verifications;
import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.mockwire.MockwireMockerBridge;

/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

public class JmockitTest {

  @Mocked
  StickyBootstrap bootstrap;

  @Test(expected=NullPointerException.class)
  public void initaliseNull() {
    MockwireMockerBridge.bridge().initialise(null, getClass());
  }

  @Test
  public void initalise() {
    MockwireMockerBridge.bridge().initialise(bootstrap, getClass());
  }

  @Test
  public void fail() {
    MockwireMockerBridge bridge = MockwireMockerBridge.bridge();
    bridge.initialise(bootstrap, getClass());
    bridge.process("b", null, null,null);

    new Verifications() {
      {
        bootstrap.registerProvider(withEqual("b"), withNotNull(), null);
      }
    };
  }

  @Test
  public void wire() {
    MockwireMockerBridge bridge = MockwireMockerBridge.bridge();
    bridge.initialise(bootstrap, getClass());
    Object o = new Object();
    bridge.process("b", o, null,Object.class);
    new Verifications() {
      {
        bootstrap.registerProvider(withEqual("b"), withNotNull(), withNotNull());
      }
    };
  }
}
