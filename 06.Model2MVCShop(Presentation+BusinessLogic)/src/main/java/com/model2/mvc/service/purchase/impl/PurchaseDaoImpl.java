package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDAO;


//==> 상품관리 DAO CRUD 구현
@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDAO{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}

	///Method
	@Override
	public void addPuchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}
	
	@Override
	   public void updatePurchase(Purchase purchase) throws Exception {
	      sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	   }

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode",purchase);
	}
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId)  throws Exception {		
		
		Map<String,Object> map = new HashMap<String,Object>();
		search.setSearchKeyword(buyerId);		
		List<Purchase> list = 	sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
		
//		int totalProductCount = sqlSession.selectOne("PurchaseMapper.getCountPurchase", buyerId);	
	
//		map.put("count", new Integer(totalProductCount));
		map.put("list", list);

		
		return map;
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}
	
}