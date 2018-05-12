package io.github.xiaobaxi.listener;

import io.undertow.Undertow.Builder;
import io.undertow.servlet.api.DeploymentManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fangzhibin
 */
@SpringBootApplication
@RestController
@ServletComponentScan
public class ListenerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ListenerApplication.class, args);
	}

	@GetMapping(value = "/test")
	public String listener() {
		return "SUCCESS";
	}

	/**
	 * the spring boot 13134 bug work around
	 * manager.undeploy() will called the TestServletContextListener's contextDestoryed when the undertow server stops
	 * @return
	 */
	@Bean
	public UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory() {
		return new UndertowEmbeddedServletContainerFactory() {

			@Override
			protected UndertowEmbeddedServletContainer getUndertowEmbeddedServletContainer(
					Builder builder, DeploymentManager manager, int port) {
				return new UndertowEmbeddedServletContainer(builder, manager,
						getContextPath(), isUseForwardHeaders(), port >= 0,
						getCompression(), getServerHeader()) {

					@Override
					public void stop() throws EmbeddedServletContainerException {
						super.stop();
						LOGGER.info("undertow undeploying");
						manager.undeploy();
						LOGGER.info("undertow undeployed");
					}

				};
			}

		};
	}
}
