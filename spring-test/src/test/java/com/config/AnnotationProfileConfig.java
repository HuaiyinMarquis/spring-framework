package com.config;

import com.bean.Person;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

@PropertySource(value = {"classpath:init.properties"})
@Configuration
public class AnnotationProfileConfig implements EmbeddedValueResolverAware {
    String name;

    @Bean
//    @Profile("default")   默认选择default
    @Profile("personA")
    public Person getPersonA() {
        Person person = new Person();
        person.setHeight(167D);
        person.setWeight(148D);
        person.setName(name);
        return person;
    }

    @Bean
    @Profile("personB")
    public Person getPersonB() {
        Person person = new Person();
        person.setHeight(176D);
        person.setWeight(189D);
        person.setName(name);
        return person;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.name = resolver.resolveStringValue("${person.name}");
    }
}
