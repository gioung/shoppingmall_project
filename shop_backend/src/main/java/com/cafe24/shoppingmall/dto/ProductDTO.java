package com.cafe24.shoppingmall.dto;

import java.util.List;

import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;

public class ProductDTO {
	private ProductVo product;
	private List<ProductDetailVo> productDetailList;
	
	
	public ProductDTO(ProductVo product, List<ProductDetailVo> productDetailList) {
		super();
		this.product = product;
		this.productDetailList = productDetailList;
	}
	
	public ProductVo getProduct() {
		return product;
	}
	public void setProduct(ProductVo product) {
		this.product = product;
	}
	public List<ProductDetailVo> getProductDetailList() {
		return productDetailList;
	}
	public void setProductDetailList(List<ProductDetailVo> productDetailList) {
		this.productDetailList = productDetailList;
	}
	@Override
	public String toString() {
		return "ProductDTO [product=" + product + ", productDetailList=" + productDetailList + "]";
	}
	
	
}
