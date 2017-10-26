package com.ls.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ls.domain.City;
import com.ls.domain.District;
import com.ls.domain.Province;
import com.ls.service.CityService;
import com.ls.service.DistrictService;
import com.ls.service.ProvinceService;

/**
 * @Description:
 * @author: cjw
 * @date: 2017年4月7日
 **/
@Controller
public class AreaController {
	@Resource(name = "provinceServiceImpl")
	private ProvinceService provinceService;

	@Resource(name = "cityServiceImpl")
	private CityService cityService;

	@Resource(name = "districtServiceImpl")
	private DistrictService districtService;

	// log4j日志记录
	private static Logger logger = LogManager.getLogger(AreaController.class.getName());

	/**
	 * Json格式返回全部的省份信息 {'list':[...],'msg';1} 成功操作msg:1
	 */
	@RequestMapping(value = "/pages/listAllProvinceToJson.do", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listAllProvinceToJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Province> list = provinceService.getAllProvince();
			map.put("msg", "1");
			map.put("list", list);
		} catch (Exception e) {
			map.put("msg", "-100");
			logger.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 根据指定省份返回全部的城市信息 Json格式{'list':[...],'msg';1} 成功操作msg:1
	 */
	@RequestMapping(value = "/pages/listCityByProvinceToJson.do", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listCityByProvinceToJson(@RequestParam(required = true) int provinceID) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (provinceID == 0) {
				map.put("msg", "-1");
				map.put("info", "请求参数错误");
				return map;
			}
			List<City> list = cityService.listCityByProvince(provinceID);
			map.put("msg", "1");
			map.put("list", list);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 根据指定城市返回全部的区县信息 Json格式{'list':[...],'msg';1} 成功操作msg:1
	 */
	@RequestMapping(value = "/pages/listDistrictByCityToJson.do", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listDistrictByCityToJson(@RequestParam(required = true) int cityID) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (cityID == 0) {
				map.put("msg", "-1");
				map.put("info", "请求参数错误");
				return map;
			}
			List<District> list = districtService.listDistrictByCity(cityID);
			map.put("msg", "1");
			map.put("list", list);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
		}
		return map;
	}

}
