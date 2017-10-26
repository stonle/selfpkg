/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.CompanyInfo;
import com.ls.util.Page;

/** 
* @ClassName: CompanyService 
* @Description: 
* @author huanglei
* @date 2017年9月4日 上午10:27:37 
* @version V1.0    
*/
public interface CompanyService extends BaseService<CompanyInfo, Integer> {
	/**
	 *获取总条数
	 * @return
	 */
   int getTotalCount();
   
   /**
    * 分页查询公司信息
    * @param page
    * @return
    */
   List<CompanyInfo> listCompanyByPage(Page page);
   
   /**
    * 注册公司信息
    * @param companyInfo
    */
   void addCompany(CompanyInfo companyInfo);
   
   /**
    * 删除公司
    * @param items
    */
   void delCompanyInfo(String items);
   
   /**
    * 根据Id查询公司
    * @param copId
    * @return
    */
   CompanyInfo getCompanyById(int copId);
   
   /**
    * 编辑公司
    * @param companyInfo
    */
   void editeCompany(CompanyInfo companyInfo);
   
   /**
    * 获取快递公司列表，用于手机端快递员注册
    * @return
    */
   List<CompanyInfo> listCompany();
}
