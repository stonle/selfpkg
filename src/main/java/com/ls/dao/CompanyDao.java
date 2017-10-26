/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.CompanyInfo;
import com.ls.util.Page;

/** 
* @ClassName: CompanyDao 
* @Description: 
* @author huanglei
* @date 2017年9月4日 上午10:38:53 
* @version V1.0    
*/
public interface CompanyDao extends BaseDAO<CompanyInfo, Integer>{
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
}
