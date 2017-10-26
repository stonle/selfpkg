package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 包裹基本信息表
 * @author Administrator
 *
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class PackageInfo {
	
	private Integer id;
	private long height;
	private long width;
	private long length;
	private long weight;
	private double paymentAmount;
	private double recvMany;
	private double backMany;
	private Integer payType;
	private String createDate;
	private Integer status;
	
	private OrderInfo orderInfo;
	
	private Device device;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public double getRecvMany() {
		return recvMany;
	}

	public void setRecvMany(double recvMany) {
		this.recvMany = recvMany;
	}

	public double getBackMany() {
		return backMany;
	}

	public void setBackMany(double backMany) {
		this.backMany = backMany;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
}
