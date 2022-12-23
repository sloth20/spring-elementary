package hello.core.bean;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면 중복 오류가 발생")
    void failFindByParentType() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면 이름과 타입으로 지정하면 됨")
    void findByNameAndParentType() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 자식 타입으로 조회")
    void findByChildType() {
        DiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findBeansByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (Map.Entry<String, DiscountPolicy> entry : beansOfType.entrySet()) {
            System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
        }
    }

    @Test
    @DisplayName("Object 타입으로 모든 빈 조회")
    void findBeansByObject() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        System.out.println("beansOfType.size() = " + beansOfType.size());
        for (Map.Entry<String, Object> entry : beansOfType.entrySet()) {
            System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        };

        @Bean
        public DiscountPolicy fixedDiscountPolicy(){
            return new FixDiscountPolicy();
        };
    }
}

