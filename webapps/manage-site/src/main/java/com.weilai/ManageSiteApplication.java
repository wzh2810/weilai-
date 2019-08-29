package com.weilai;

@SpringBootApplication
@ComponentScan
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ManageSiteApplication extends SpringBootServletInitializer implements SchedulingConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ManageSiteApplication.class, args);
    }
}
