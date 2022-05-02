package com.model2.mvc.web.product;

import java.awt.Menu;
import java.util.Map;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//��ǳ���� Controller
@Controller
public class ProductController {
	
	//Field
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method ���� X
	
	public ProductController() {
		System.out.println(this.getClass());
		//getClass() : ��ü�� ���ϴ� Ŭ������ ������ �˾Ƴ��� �޼ҵ��̴�.
	}
	
	// @Value : properties ���Ͽ� ������ ������ Spring ������ ����
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@RequestMapping ��û�� ���� �� � ��Ʈ�ѷ��� ȣ���� �Ǿ�� �ϴ��� �˷��ִ� ��ǥ
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product")Product product,@RequestParam("manuDate")String manuDate) throws Exception {
		//@ModelAttribute�� HTTP Body ����� HTTP �Ķ������ ������ Getter, Setter, �����ڸ�
		// ���� �����ϱ� ���� ����Ѵ�. �Ϲݺ����� ��� ���� �Ұ��� > model��ü ���ؼ� ����.
		//���� @ModelAttribute("�Ķ���͸�") 
		//@ModelAttribute ���� �� �ڵ����� ���� �Ǵ� �۾�
		//1. �Ķ���ͷ� �Ѱ� �� Ÿ���� ������Ʈ�� �ڵ����� �����Ѵ�.
		//2.������ ������Ʈ(test) HTTP�� �Ѿ� �� ������ �ڵ����� ���ε��Ѵ�.
		//3.���������� @ModelAttribute ������̼��� ���� ��ü�� (Product��ü) �ڵ����� Model��ü�� �߰��ǰ� View������ ���޵ȴ�.
		
		System.out.println("/addProduct.do");
		
		product.setManuDate(manuDate.replace("-", ""));
		productService.addProduct(product);
		System.out.println();
		return "redirect:/product/addProductView.jsp";
	}
	
	/* @RequestParam 
	 * 1���� HTTP �Ķ���͸� ��� ���� ����
	 * �ʼ� ���ΰ� true�̱� ������ �ݵ�� �ʿ��� ��찡 �ƴ϶�� required=false ������ �ʿ���
	 * @ModelAttribute 
	 * multipart/form-data ���·� ���� HTTP Body �����Ϳ� HTTP �Ķ���͵��� Setter�� ���� 1��1�� ��ü�� ���ε���Ŵ
	 * ��ȯ�� �ƴ� ���� ���Խ�Ű�Ƿ�, �������� �����ڳ� Setter�Լ��� ������ �������� ������� ����
	 */
	@RequestMapping("/getProduct.do")
	//@RequestParam("������ �������� �̸�") [������Ÿ��] [�����µ����͸� ���� ����] �������� ��� model ��ü�� �̿��ؼ� view �� ����.
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		
		Product product= productService.getProduct(prodNo);
		
		//model.addAttribute�� (key,value) �޼ҵ��̿��ؼ� view �����ҵ����� ����
		model.addAttribute("product",product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model ) throws Exception {
		
		System.out.println("/updateProductView.do");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product",product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product")Product product, Model model) throws Exception{
	
		System.out.println("/updateProduct.do");
//		Product prodNo=productService.getProduct(product.getProdNo());
		
		productService.updateProduct(product);
		
		return "redirect:/getProduct.do?prodNo="+product.getProdNo();
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct(@ModelAttribute("search") Search search ,@RequestParam("menu")String menu, Model model , HttpServletRequest request) throws Exception {
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);	
		
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("menu",menu);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}

}
