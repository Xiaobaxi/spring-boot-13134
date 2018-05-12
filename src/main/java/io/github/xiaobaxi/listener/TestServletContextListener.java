package io.github.xiaobaxi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fangzhibin
 */
@WebListener
public class TestServletContextListener implements ServletContextListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestServletContextListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.info("- contextInitialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    LOGGER.info("- contextDestroyed");
  }
}
