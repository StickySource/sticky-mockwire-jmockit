package net.stickycode.mockwire.jmockit;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.inject.Provider;

import net.stickycode.bootstrap.StickyBootstrap;
import net.stickycode.mockwire.MockwireMockerBridge;

public class JmockitMockwireMockerBridge
    implements MockwireMockerBridge {

  private StickyBootstrap bootstrap;

  @Override
  public void initialise(StickyBootstrap bootstrap, Class<?> metadata) {
    this.bootstrap = Objects.requireNonNull(bootstrap);
  }

  @Override
  public void process(String name, final Object target, final Field field, Class<?> type) {
    bootstrap.registerProvider(name, new Provider<Object>() {

      @Override
      public Object get() {
        try {
          return field.get(target);
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }, type);
  }

}
