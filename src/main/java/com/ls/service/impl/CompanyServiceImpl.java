/**   
* 
*/
package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.CompanyDao;
import com.ls.domain.CompanyInfo;
import com.ls.service.CompanyService;
import com.ls.util.Page;
import com.ls.util.Utils;

/** 
* @ClassName: CompanyServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月4日 上午10:28:38 
* @version V1.0    
*/
@Service
public class CompanyServiceImpl extends BaseServiceImpl<CompanyInfo, Integer> implements CompanyService{
    @Autowired
	private CompanyDao companyDaoImpl;
	@Override
	public int getTotalCount() {
		return companyDaoImpl.getTotalCount();
	}

	@Override
	public List<CompanyInfo> listCompanyByPage(Page page) {
		return companyDaoImpl.listCompanyByPage(page);
	}
	@Override
	public void addCompany(CompanyInfo companyInfo) {
		companyInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		companyInfo.setCreateDate(Utils.getCurrentTime());
		companyDaoImpl.saveObject(companyInfo);
		
	}
	@Override
	public void delCompanyInfo(String items) {
		String[] strs = items.split(",");
		for(String str:strs){
			CompanyInfo companyInfo = companyDaoImpl.getObjectById(CompanyInfo.class, Integer.parseInt(str));
			companyInfo.setStatus(0);
		}
	}

	@Override
	public CompanyInfo getCompanyById(int copId) {

		return companyDaoImpl.getObjectById(CompanyInfo.class, copId);
	}

	@Override
	public void editeCompany(CompanyInfo companyInfo) {
		CompanyInfo companyfo = companyDaoImpl.getObjectById(CompanyInfo.class, companyInfo.getCopId());
		companyfo.setCopName(companyInfo.getCopName());
		companyfo.setRemark(companyInfo.getRemark());
	}

	@Override
	public List<CompanyInfo> listCompany() {
		return companyDaoImpl.getAllObjects(CompanyInfo.class);
	}
 
}
