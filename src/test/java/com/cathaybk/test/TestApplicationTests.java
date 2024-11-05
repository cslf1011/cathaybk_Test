package com.cathaybk.test;

import com.cathaybk.test.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {
	@Autowired
	private ProductController productController;

	@Test
	void testFetchFundData() {
		// 調用 fetchFundData 方法
		productController.fetchFundData();

	}

	@Test
	void contextLoads() {
	}


}
