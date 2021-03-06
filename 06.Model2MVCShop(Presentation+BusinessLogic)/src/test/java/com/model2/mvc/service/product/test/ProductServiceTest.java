package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;

//	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("망고스틴");
		product.setProdDetail("꿀맛");
		product.setManuDate("20220401");
		product.setPrice(5000);
		product.setFileName("망고스틴");
		
//		System.out.println(product);

		productService.addProduct(product);
		
//		product = productService.getProduct(10021);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("망고스틴", product.getProdName());
		Assert.assertEquals("꿀맛", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("망고스틴", product.getFileName());
	}
	
//	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> 필요하다면...
//		product.setProdNo(10020);
//		product.setProdName("망고스틴");
//		product.setProdDetail("꿀맛");
//		product.setManuDate("20220401");
//		product.setPrice(5000);
//		product.setFileName("망고스틴");
		
		product = productService.getProduct(10021);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals(10021, product.getProdNo());
		Assert.assertEquals("망고스틴", product.getProdName());
		Assert.assertEquals("꿀맛", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("망고스틴", product.getFileName());

	}
	
//	@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10020);
		Assert.assertNotNull(product);
		System.out.println("1"+product);
		
		Assert.assertEquals("망고스틴", product.getProdName());
		Assert.assertEquals("꿀맛", product.getProdDetail());
		Assert.assertEquals("20220401", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("망고스틴", product.getFileName());

		product.setProdName("샤인머스캣");
		product.setProdDetail("꿀맛탱");
		product.setManuDate("20220402");
		product.setPrice(8000);
		product.setFileName("샤인머스캣");
		
		System.out.println("2"+product);
		productService.updateProduct(product);
		
		product = productService.getProduct(10021);
		Assert.assertNotNull(product);
		
		//==> console 확인
		System.out.println(product);

		//==> API 확인
		Assert.assertEquals("샤인머스캣", product.getProdName());
		Assert.assertEquals("꿀맛탱", product.getProdDetail());
		Assert.assertEquals("20220402", product.getManuDate());
		Assert.assertEquals(8000, product.getPrice());
		Assert.assertEquals("샤인머스캣", product.getFileName());
	 }
	 
//	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	//System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
//	 @Test
	 public void testGetProductListByProductNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10020");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("망고스틴");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}