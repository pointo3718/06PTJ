package com.model2.mvc.web.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//상풍관리 Controller
@Controller
public class PurchaseController {
	
	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method 구현 X
	
	public PurchaseController() {
		System.out.println(this.getClass());
		//getClass() : 객체가 속하는 클래스의 정보를 알아내는 메소드이다.
	}
	
	// @Value : properties 파일에 세팅한 내용을 Spring 변수에 주입
//	@Value("#{commonProperties['pageUnit']}")
//	int pageUnit;
//	
//	@Value("#{commonProperties['pageSize']}")
//	int pageSize;
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, HttpSession session, @RequestParam("prodNo") int prodNo) throws Exception {
		User user = (User) session.getAttribute("user");
		Product product = new Product();
		product.setProdNo(prodNo);
		
		//puchase 설정
		purchase.setBuyerId(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		//실행
		purchaseService.addPurchase(purchase);
		
		return new ModelAndView("forward:/purchase/addPurchase.jsp");
	}
	
	
	  @RequestMapping("/addPurchaseView.do")
	  public ModelAndView addPurchaseView(@RequestParam("prod_no") int prodNo)throws Exception{ 
	  Product product = productService.getProduct(prodNo);
	  
	  ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
	  modelAndView.addObject("product",product);
	  
	  	return modelAndView; 
	  }
	  
//	  @RequestMapping(value="/listPurchase.do")
//		public ModelAndView getPurchaseList(@RequestParam(value = "page", defaultValue = "1") int page, 
//									  @RequestParam(value = "currentPage", defaultValue= "1") int currentPage,
//									  HttpServletRequest request, HttpSession session) throws Exception {
//			
//			System.out.println("\n==>listPurchase Start.........");
//			
//			if(request.getMethod().equals("GET")) {
//				currentPage = page;
//			}
//			
//			
//					
//			User user = (User)session.getAttribute("user");		
//
//			//3.DB 접속을 위한 search
//			Search search = new Search();
//			search.setCurrentPage(currentPage);
//			search.setPageSize(pageSize);
//			search.setUserId(user.getUserId());
//
//			///4.DB에 접속하여 결과값을 Map으로 가져옴
//			Map<String, Object> map = purchaseService.getPurchaseList(search);
//
//			
//			///5.pageView를 위한 객체
//			Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(),
//					pageUnit, pageSize);
//			
//			System.out.println("ListPurchaseAction-resultPage : " + resultPage);
//			System.out.println("ListPurchaseAction-list.size() : " + ((List)map.get("list")).size());
//			
//			
//			///6.JSP에 출력을 하기위한 설정들
//			//title 설정
//			String title = "구매 목록 조회";
//			
//			//colum 설정
//			List<String> columList = new ArrayList<String>();
//			columList.add("No");
//			columList.add("회원ID");
//			columList.add("회원명");
//			columList.add("전화번호");
//			columList.add("거래상태");
//			columList.add("정보수정");
//			
//			//출력을 위한 Object
//			ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase.jsp");
//			modelAndView.addObject("title", title);
//			modelAndView.addObject("columList", columList);
//			modelAndView.addObject("resultPage", resultPage);
//
//			System.out.println("\n==>listPurchase End.........");
//			
//			return modelAndView;
//		}
//	 
//	  @RequestMapping("/getPurchase.do")
//		public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
//			System.out.println("\n==>getPurchase Start.........");
//			
//			Purchase purchase = purchaseService.getPurchase(tranNo);
//					
//			List<String> purchaseList = purchase.toList();
//			System.out.println("getPurchaseAction의 getTranCode값 : "+purchase.getTranCode());
//			
//			ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
//			modelAndView.addObject("list", purchaseList);
//			modelAndView.addObject("purchase", purchase);
//			
//			System.out.println("\n==>getPurchase End.........");
//			
//			return modelAndView;
//		}
//	
//	  @RequestMapping("/updatePurchaseView.do")
//		public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo, Map<String, Object> map) throws Exception {
//			Purchase purchase = purchaseService.getPurchase(tranNo);
//
//			ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
//			modelAndView.addObject("purchase", purchase);
//
//			return modelAndView;
//		}
//	
//	  @RequestMapping("/updatePurchase.do")
//	  public ModelAndView updatePurchase(@ModelAttribute Purchase purchase, Map<String,Object> map) throws Exception {
//		System.out.println("\n==>updatePurchase Start.........");
//			
//		purchaseService.updatePurchase(purchase);
//		
//		ModelAndView modelAndView = new ModelAndView("forward:/getPurchase.do?");
//		modelAndView.addObject("tranNo", purchase.getTranNo());
//		
//		System.out.println("\n==>updatePurchase End.........");
//		return modelAndView;
//	  }
	
	/*
	 * @RequestMapping("/updateTranCode.do") public ModelAndView
	 * updateTranCode(@RequestParam("tranCode") String
	 * tranCode, @RequestParam("tranNo") int tranNo) throws Exception{ Purchase
	 * purchase = new Purchase(); purchase.setTranNo(tranNo);
	 * purchase.setTranCode(tranCode);
	 * 
	 * System.out.println(purchase);
	 * 
	 * purchaseService.updateTranCode(purchase);
	 * 
	 * return new ModelAndView("forward:/listPurchase.do?"); }
	 */
	
}
