package com.shoppingmall.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingmall.mapper.CartItemsMapper;
import com.shoppingmall.model.CartItems;
import com.shoppingmall.model.Products;

@RestController
@RequestMapping("/cartitems")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartItemsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private CartItemsMapper cartItemsMapper;
	//모든 상품 목록 전부보여줌
	@GetMapping("/all")
	public List<CartItems> getAll(){
		return cartItemsMapper.getAll();
	}
	//입력된 id와 매칭되는 상품 전체 데이터 보여줌
	@GetMapping("/{cart_item_id}")
	public CartItems getCartItems(@PathVariable("cart_item_id") int cart_item_id) {
		return cartItemsMapper.getCartItems(cart_item_id);
	}
	
	@GetMapping("/quantity/{cart_item_id}")
	public CartItems getCartQuantity(@PathVariable("cart_item_id") int cart_item_id) {
		return cartItemsMapper.getCartQuantity(cart_item_id);
	}
	
	//사용자 id와 매칭되는 상품 다수 보여줌
	@GetMapping("/getCartItemsByUser/{user_sequence_id}")
	public List<CartItems> getCartItemsByUser(@PathVariable("user_sequence_id") int user_sequence_id) {
		return cartItemsMapper.getCartItemsByUser(user_sequence_id);
	}
	

	//cart_item_id로 상품이미지만 가져옴
	@GetMapping("/showProductImage/{cart_item_id}")
	@ResponseBody
	public ResponseEntity<?> showProductImage(@PathVariable("cart_item_id") int cart_item_id, HttpServletResponse response,
			HttpServletRequest request) throws IOException, SQLException {
		try {
			Products p =cartItemsMapper.getProductImage(cart_item_id);
			response.setContentType("image/jpeg; image/jpg; image/png; image/gif");
			InputStream in = new ByteArrayInputStream(p.getProduct_picture());
			IOUtils.copy(in, response.getOutputStream());

			return new ResponseEntity<>("Product Saved With File", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
}
	//장바구니 데이터 모두 입력
	@PostMapping("")
	public CartItems post(@RequestBody CartItems cartitems) {
		cartItemsMapper.insert(cartitems);
		return cartitems;
	}
	
	
	//장바구니 데이터 수량 1증가시킴
	@PutMapping("/plus/{cart_item_id}")
	public void plusQuantity(@PathVariable("cart_item_id") int cart_item_id,@RequestBody CartItems cartitems) {
		System.out.println(cartitems);
		cartItemsMapper.plusQuantity(cartitems);
		
	}
	
	//장바구니 모든 데이터 수정
	@PutMapping("/{cart_item_id}")
	public void updateCartItems(@PathVariable("cart_item_id") int cart_item_id,@RequestBody CartItems cartitems) {
		cartItemsMapper.updateCartItems(cartitems);
	}
	
	
	//장바구니 데이터 부분 수정
	@PatchMapping("/{cart_item_id}")
	public @ResponseBody void patchCartItem(@PathVariable int cart_item_id, @RequestBody Map<Object, Object> fields) {
		CartItems cartitems = cartItemsMapper.getCartItems(cart_item_id);	
		fields.forEach((k,v) -> {
			Field field = ReflectionUtils.findRequiredField(CartItems.class, (String)k);
			System.out.println(k);
			System.out.println(v);
			ReflectionUtils.setField(field, cartitems, v);
		});
		cartItemsMapper.updateCartItems(cartitems);
	}
	
	
	@PatchMapping("/plusQuantity/{cart_item_id}")
	public @ResponseBody void updateplusQuantity(@PathVariable int cart_item_id, @RequestBody Map<Object, Object> fields) {
		CartItems cartitems = cartItemsMapper.getCartItems(cart_item_id);	
		fields.forEach((k,v) -> {
			Field field = ReflectionUtils.findRequiredField(CartItems.class, (String)k);
			System.out.println(k);
			System.out.println(v);
			ReflectionUtils.setField(field, cartitems, v);
		});
		cartItemsMapper.updateplusQuantity(cartitems);
	}
	
	@PatchMapping("/minusQuantity/{cart_item_id}")
	public @ResponseBody void updateminusQuantity(@PathVariable int cart_item_id, @RequestBody Map<Object, Object> fields) {
		CartItems cartitems = cartItemsMapper.getCartItems(cart_item_id);	
		fields.forEach((k,v) -> {
			Field field = ReflectionUtils.findRequiredField(CartItems.class, (String)k);
			System.out.println(k);
			System.out.println(v);
			ReflectionUtils.setField(field, cartitems, v);
		});
		cartItemsMapper.updateMinusQuantity(cartitems);
	}
	
	
	//입력된 id와 매칭되는 상품 삭제
	@DeleteMapping("/{cart_item_id}")
	public void deleteUser(@PathVariable("cart_item_id")int cart_item_id) {
		cartItemsMapper.deleteCartItems(cart_item_id);
	}
	


}
