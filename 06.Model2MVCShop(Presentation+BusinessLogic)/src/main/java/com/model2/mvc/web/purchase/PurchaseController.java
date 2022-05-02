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

//��ǳ���� Controller
@Controller
public class PurchaseController {
	
	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method ���� X
	
	public PurchaseController() {
		System.out.println(this.getClass());
		//getClass() : ��ü�� ���ϴ� Ŭ������ ������ �˾Ƴ��� �޼ҵ��̴�.
	}
	
	// @Value : properties ���Ͽ� ������ ������ Spring ������ ����
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
		
		//puchase ����
		purchase.setBuyerId(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		//����
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
//			//3.DB ������ ���� search
//			Search search = new Search();
//			search.setCurrentPage(currentPage);
//			search.setPageSize(pageSize);
//			search.setUserId(user.getUserId());
//
//			///4.DB�� �����Ͽ� ������� Map���� ������
//			Map<String, Object> map = purchaseService.getPurchaseList(search);
//
//			
//			///5.pageView�� ���� ��ü
//			Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(),
//					pageUnit, pageSize);
//			
//			System.out.println("ListPurchaseAction-resultPage : " + resultPage);
//			System.out.println("ListPurchaseAction-list.size() : " + ((List)map.get("list")).size());
//			
//			
//			///6.JSP�� ����� �ϱ����� ������
//			//title ����
//			String title = "���� ��� ��ȸ";
//			
//			//colum ����
//			List<String> columList = new ArrayList<String>();
//			columList.add("No");
//			columList.add("ȸ��ID");
//			columList.add("ȸ����");
//			columList.add("��ȭ��ȣ");
//			columList.add("�ŷ�����");
//			columList.add("��������");
//			
//			//����� ���� Object
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
//			System.out.println("getPurchaseAction�� getTranCode�� : "+purchase.getTranCode());
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
