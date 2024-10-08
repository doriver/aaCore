package hello.core.autowired;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

public class AllBeanTest {
	@Test
	void findAllBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		DiscountService discountService = ac.getBean(DiscountService.class);
		Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
		
		Member member = new Member(1L, "userA", Grade.VIP);
		int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
		Assertions.assertThat(discountPrice).isEqualTo(1000);
		int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
		Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);	
	}
	
	static class DiscountService {
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policies;
		
		@Autowired
		public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
			this.policyMap = policyMap;
			this.policies = policies;
			System.out.println("policyMap = " + policyMap); // { fixDiscountPolicy = hello.core.discount.FixDiscountPolicy@ca27722, rateDiscountPolicy = hello.core.discount.RateDiscountPolicy@70ab80e3 }
			System.out.println("policies = " + policies); // [ hello.core.discount.FixDiscountPolicy@ca27722 , hello.core.discount.RateDiscountPolicy@70ab80e3 ]
		}
		
		public int discount(Member member, int price, String discountCode) {
			DiscountPolicy discountPolicy = policyMap.get(discountCode);
			return discountPolicy.discount(member, price);
		}
	}
}
