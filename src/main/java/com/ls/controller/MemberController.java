package com.ls.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 用于提供普通收件人注册机登陆接口
 * @author Administrator
 *
 */

import com.ls.domain.BaseAddress;
import com.ls.domain.MemberInfo;
import com.ls.domain.OrderInfo;
import com.ls.domain.PackageInfo;
import com.ls.domain.RecvPerInfo;
import com.ls.domain.SendPerInfo;
import com.ls.http.AppVersionVo;
import com.ls.service.MemberService;
import com.ls.service.OrderService;
import com.ls.service.PackageService;
import com.ls.service.RecvPerService;
import com.ls.service.SendPerService;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.StringUtil;
import com.ls.wx.WXCommonUtil;
import com.ls.wx.WXConfigUtil;
import com.ls.wx.WXEnum;
import com.ls.wx.WXPayCommonUtil;
import com.ls.wx.WXXMLUtil;

/**
 * app*：表示安卓端的接口 ； 其它一般接口,即是后台管理页面接口
 * 
 * @ClassName: MemberController
 * @Description:
 * @author huanglei
 * @date 2017年9月1日 上午11:25:51
 * @version V1.0
 */
@RestController
@RequestMapping("pages")
public class MemberController {

	private static Logger logger = LogManager.getLogger(MemberController.class.getName());

	@Autowired
	private MemberService memberServiceImpl;
	@Autowired
	private SendPerService sendPerServiceImpl;
	@Autowired
	private RecvPerService recvPerServiceImpl;

	@Autowired
	private OrderService orderServiceImpl;

	@Autowired
	private PackageService packageServiceImpl;

	/**
	 * 用于后台页面展示
	 * 
	 * @param personel
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryMemberByPage.do", method = RequestMethod.GET)
	public Map<String, Object> queryObjectByPage(MemberInfo personel, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MemberInfo> list = new ArrayList<MemberInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), memberServiceImpl.getTotalCount(personel),
					page.getCurrentPage());
			list = memberServiceImpl.listPersonelByPage(page, personel);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("list", list);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 后台服务展示收件人信息列表
	 * 
	 * @param personel
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryCollertByPage.do", method = RequestMethod.GET)
	public Map<String, Object> queryRecvPerInfoByPage(RecvPerInfo personel, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RecvPerInfo> list = new ArrayList<RecvPerInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), recvPerServiceImpl.getRecvPreTotalCount(personel),
					page.getCurrentPage());
			list = recvPerServiceImpl.listRecvPerByPage(page, personel);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("list", list);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 寄件人信息
	 * 
	 * @param personel
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/querySendPerByPage.do", method = RequestMethod.GET)
	public Map<String, Object> querySendPerByPage(SendPerInfo personel, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SendPerInfo> list = new ArrayList<SendPerInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), sendPerServiceImpl.getSendPreTotalCount(personel),
					page.getCurrentPage());
			list = sendPerServiceImpl.listSendPerByPage(personel, page);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("list", list);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 会员注册 会员注册只需手机号和密码
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "/appandmemberinfo.do", method = RequestMethod.POST)
	public Map<String, Object> andObject(@RequestBody MemberInfo member) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (member == null) {
				map.put("msg", "-1");
				map.put("info", "未检测到需要保存的对象");
				return map;
			}
			memberServiceImpl.saveMemberInfo(member);
			map.put("msg", "1");
			map.put("info", "注册成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 会员登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appmeberLogin.do", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody MemberInfo member) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String str = memberServiceImpl.checkMemberLogin(member);
			map.put("msg", "1");
			map.put("info", str);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appmodifyMemberPwd.do", method = RequestMethod.PUT)
	public Map<String, Object> modifyMemberPwd(@RequestParam("cellPhone") String cellPhone,
			@RequestParam("newPwd") String newPwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			memberServiceImpl.modifyMemberPwd(cellPhone, newPwd);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 会员用户详细信息完善
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/appeditMemberInfoByMemberId.do", method = RequestMethod.POST)
	public Map<String, Object> editMemberInfoByMemberId(@RequestBody MemberInfo member) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			memberServiceImpl.updateMemberInfoByMemberId(member);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 获取个人信息
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/appqueryMemberInfoById.do", method = RequestMethod.GET)
	public Map<String, Object> queryMemberInfoById(@RequestParam(value = "memberId") String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		MemberInfo memberInfo = new MemberInfo();
		try {
			memberInfo = memberServiceImpl.queryMemberInfoById(memberId);
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("obj", memberInfo);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 修改个人信息表
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/appmodifyMemberInfiById.do", method = RequestMethod.PUT)
	public Map<String, Object> modifyMemberInfiById(@RequestBody MemberInfo membe) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			memberServiceImpl.updateMemberInfo(membe);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 添加寄件人信息接口
	 * 
	 * @param sendPerInfo
	 * @return
	 */
	@RequestMapping(value = "/appaddSendInfo.do", method = RequestMethod.POST)
	public Map<String, Object> addSendInfo(@RequestBody SendPerInfo sendPerInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sendPerServiceImpl.addSendInfo(sendPerInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 获取寄件人件人信息接口
	 * 
	 * @param remberId
	 * @return
	 */
	@RequestMapping(value = "/appquerySendPerInfo.do", method = RequestMethod.GET)
	public Map<String, Object> querySendPerAndRecvInfo(@RequestParam(value = "memberId") String remberId, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			page = PageUtil.createPage(page.getEveryPage(), sendPerServiceImpl.getTotalCount(remberId),
					page.getCurrentPage());
			List<SendPerInfo> sendPerInfos = sendPerServiceImpl.querySendPerInfo(remberId, page);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("list", sendPerInfos);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 修寄件人信息接口
	 * 
	 * @param sendPerInfo
	 * @return
	 */
	@RequestMapping(value = "/appmodifySendPerInfo.do", method = RequestMethod.PUT)
	public Map<String, Object> modifySendPerInfo(@RequestBody SendPerInfo sendPerInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sendPerServiceImpl.updateSendPerInfo(sendPerInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 删除寄件人信息接口
	 * 
	 * @param sendId
	 * @param recvId
	 * @return
	 */
	@RequestMapping(value = "/appdelSendPerInfo.do", method = RequestMethod.DELETE)
	public Map<String, Object> delSendPerAndRecvInfo(@RequestParam("sendId") String sendId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sendPerServiceImpl.deleteSendPerAndRecvInfo(sendId);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 添加收件人信息
	 * 
	 * @param recvPerInfo
	 * @return
	 */
	@RequestMapping(value = "/appaddRecvPerInfo.do", method = RequestMethod.POST)
	public Map<String, Object> addRecvPerInfo(@RequestBody RecvPerInfo recvPerInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			recvPerServiceImpl.addRecvPerInfo(recvPerInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 获取常用收件人信息
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/applistRecvPerInfo.do", method = RequestMethod.GET)
	public Map<String, Object> listRecvPerInfo(@RequestParam("memberId") String memberId, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RecvPerInfo> recvPerInfos = new ArrayList<RecvPerInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), recvPerServiceImpl.getTotalCount(memberId),
					page.getCurrentPage());
			recvPerInfos = recvPerServiceImpl.listRecvPerInfo(memberId, page);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("list", recvPerInfos);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 修改收件人信息
	 * 
	 * @param recvPerInfo
	 * @return
	 */
	@RequestMapping(value = "/appmodifyRecvPerInfo.do", method = RequestMethod.PUT)
	public Map<String, Object> modifyRecvPerInfo(@RequestBody RecvPerInfo recvPerInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			recvPerServiceImpl.updateRecvPerInfo(recvPerInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 删除收件人信息表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appdelRecvPerInfo.do", method = RequestMethod.DELETE)
	public Map<String, Object> delRecvPerInfo(@RequestParam("recvId") String recvId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			recvPerServiceImpl.deleteRecvPerInfo(recvId);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 下订单接口
	 * 
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping(value = "/appaddOrderInfo.do", method = RequestMethod.POST)
	public Map<String, Object> addOrderInfo(@RequestBody OrderInfo orderInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			orderServiceImpl.addOrderInfo(orderInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 查询订单接口
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/appqueryOrderInfos.do", method = RequestMethod.GET)
	public Map<String, Object> queryOrderInfos(@RequestParam("memberId") String memberId, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), orderServiceImpl.getTotalCount(memberId),
					page.getCurrentPage());
			orderInfos = orderServiceImpl.queryOrderInfos(memberId, page);
			map.put("totalCount", page.getTotalCount());
			map.put("currentPage", page.getCurrentPage());
			map.put("totalPage", page.getTotalPage());
			map.put("list", orderInfos);
			map.put("msg", "1");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 只能取消没有支付的订单
	 * 
	 * @param oderId
	 * @return
	 */
	@RequestMapping(value = "/appCancelOrderInfo.do", method = RequestMethod.DELETE)
	public Map<String, Object> delOrderInfo(@RequestParam("pkgNum") String pkgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			orderServiceImpl.deleteOrderInfo(pkgId);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	/**
	 * 手机端 只请求一次
	 * 
	 * @return
	 */
	@RequestMapping(value = "/appqueryBaseAddress.do", method = RequestMethod.GET)
	public Map<String, Object> queryBaseAddress() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BaseAddress baseAddress = orderServiceImpl.queryBaseAddress();
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("obj", baseAddress);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	@RequestMapping(value = "/appCheckProgramVersion.do", method = RequestMethod.GET)
	public Map<String, Object> checkProgramVersion(@RequestParam("curVersion") String curVersion) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AppVersionVo baseAddress = orderServiceImpl.checkProgramVersion(curVersion);
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("obj", baseAddress);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}

	// 手机APP端微信支付接口；
	/**
	 * 微信统一下单接口
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appWxPrePay.do", method = RequestMethod.GET)
	public Map<String, Object> wxPrePay(@RequestParam("pkgNum") String pkgNum, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取订单
		PackageInfo packageInfo = packageServiceImpl.getPackageInfoByPkgNum(pkgNum);
		double price = packageInfo.getPaymentAmount();
		int price100 = new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
		if (price100 <= 0) {
			resultMap.put("msg", "-1");
			resultMap.put("code", "付款金额错误");
			return resultMap;
		}
		// 设置回调地址-获取当前的地址拼接回调地址
		String url = request.getRequestURL().toString();
		String domain = url.substring(0, url.length() - 13);

		String notify_url = domain + "appWxNotify.do";

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", WXConfigUtil.APPID);
		parameters.put("mch_id", WXConfigUtil.MCH_ID);
		parameters.put("nonce_str", WXPayCommonUtil.CreateNoncestr());
		parameters.put("body", "购买测试");
		parameters.put("out_trade_no", pkgNum); // 订单id
		parameters.put("fee_type", "CNY");
		parameters.put("total_fee", String.valueOf(1));
		parameters.put("spbill_create_ip", WXCommonUtil.toIpAddr(request));
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", "APP");
		// 设置签名
		String sign = WXPayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		// 封装请求参数结束
		String requestXML = WXPayCommonUtil.getRequestXml(parameters);
		// 调用统一下单接口
		String result = WXPayCommonUtil.httpsRequest(WXConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
		System.out.println("\n" + result);
		try {
			/**
			 * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，
			 * partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign
			 * =WXPay
			 **/
			Map<String, String> map = WXXMLUtil.doXMLParse(result);
			SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
			parameterMap2.put(WXEnum.APPID.getName(), WXConfigUtil.APPID);
			parameterMap2.put("partnerid", WXConfigUtil.MCH_ID);
			parameterMap2.put("prepayid", map.get("prepay_id"));
			parameterMap2.put("package", "Sign=WXPay");
			parameterMap2.put("noncestr", WXPayCommonUtil.CreateNoncestr());
			// 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
			parameterMap2.put("timestamp",
					Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
			String sign2 = WXPayCommonUtil.createSign("UTF-8", parameterMap2);
			parameterMap2.put("sign", sign2);
			if("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))){
				resultMap.put("code", "200");
			}else{
				resultMap.put("code", "-1");
			}
			resultMap.put("msg", parameterMap2);
		} catch (Exception e) {
			resultMap.put("code", "-1");
		}
		return resultMap;
	}

	/**
	 * 微信异步通知
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping("/appWxNotify.do")
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
		// 读取参数
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = WXXMLUtil.doXMLParse(sb.toString());
		for (Object keyValue : m.keySet()) {
			System.out.println(keyValue + "=" + m.get(keyValue));
		}
		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		// 判断签名是否正确
		String resXml = orderServiceImpl.checkOrderInfoPayMent(packageParams);
		// ------------------------------
		// 处理业务完毕
		// ------------------------------
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();

	}
	
	@RequestMapping(value = "/appGetPayMentByPkgNum.do", method = RequestMethod.GET)
	public Map<String,Object> getPayMentByPkgNum(@RequestParam("pkgNum")String pkgNum){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			OrderInfo orderInfo = orderServiceImpl.getObjectById(OrderInfo.class, pkgNum);
			map.put("msg", "1");
			map.put("info", "sucess");
			map.put("obj", orderInfo);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
	
}
