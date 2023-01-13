package com.huangjiabin.cache;

import com.huangjiabin.cache.entity.User;
import com.huangjiabin.cache.service.EhcacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
class Cache04SpringbootEhcacheApplicationTests {

	@Autowired
	EhcacheService ehcacheService;

	@Test
	public void testFindById() {
		User user1 = ehcacheService.findById("1001");
		System.out.println(user1);
		System.out.println("------------------");
		User user2 = ehcacheService.findById("1001");
		System.out.println(user2);
	}

}
