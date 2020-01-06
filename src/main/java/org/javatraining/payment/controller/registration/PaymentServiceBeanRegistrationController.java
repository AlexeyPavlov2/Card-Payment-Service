package org.javatraining.payment.controller.registration;

import lombok.SneakyThrows;
import org.javatraining.payment.util.loader.PaymentServiceClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentServiceBeanRegistrationController {

    @Autowired
    private GenericApplicationContext context;

    @PostMapping("/beanreg")
    @SneakyThrows
    public String registerPaymentServiceBean(@RequestBody BeanDefinitionMetaData metaData) {
        PaymentServiceClassLoader classLoader = new PaymentServiceClassLoader(metaData.getBeanLocation(),
                ClassLoader.getSystemClassLoader());
        Class loadedServiceClass = classLoader.findClass(metaData.getBeanClassName());
        GenericBeanDefinition serviceBeanDefinition = new GenericBeanDefinition();
        serviceBeanDefinition.setBeanClass(loadedServiceClass);
        serviceBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        registry.registerBeanDefinition(metaData.getBeanName(), serviceBeanDefinition);
        context.getBean(metaData.getBeanName());
        return "registered";
    }
}
