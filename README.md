### Spring boot issue 13134

If you defined the ServletContextListener by the annotation @WebListener on the Undertow container server
when you stops the Undertow server the ServletContextListener's contextDestoryed method is not called

You can work around the problem by providing a custom Undertow factory that returns a custom Undertow container 
that overrides stop to also call undeploy

the spring boot before the 1.5.13.RELEASE has this bug and will fix the next version in Milestone 1.5.x

issue description: https://github.com/spring-projects/spring-boot/issues/13134
