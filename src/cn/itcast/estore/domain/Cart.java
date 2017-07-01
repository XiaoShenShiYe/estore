package cn.itcast.estore.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 购物车的实体
 * @author Admin
 *
 */
public class Cart {
	//把购物项封装成Map集合放入购物车
	private Map<String, CartItem> map=new LinkedHashMap<String,CartItem>();
	private Double total=0d;//总计
	/*public Map<String, CartItem> getMap() {
		return map;
	}*/
	public Collection<CartItem>  getCartItems(){
		return map.values();
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	/*public void setTotal(Double total) {
		this.total = total;
	}
	
	*/
	/**
	 * 把 购物项添加到购物车
	 * @param cartItem
	 */
	public void addCart(CartItem cartItem){
		//先判断购买的图书是否已在购物车中,如果有则让新增的数量加上subtotal,否则在购物车中新建一个购物项
		String bid=cartItem.getBook().getBid();
		
		if(map.containsKey(bid)){
			CartItem _cartItem=map.get(bid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
			
			
		}else{
			map.put(bid, cartItem);
		}
		total += cartItem.getSubtotal();
	}
	@Override
	public String toString() {
		return "Cart [map=" + map + ", total=" + total + "]";
	}
	/**
	 * 从购物车中移除购物项
	 */
	public void removeCart(String bid){
		//同时清空购物项和总计
		CartItem cartItem= map.remove(bid);//把bid作为map 的key
		total -= cartItem.getSubtotal();
	}
	/**
	 * 清空购物车
	 */
	public void clearCart(){
		
		map.clear();
		
		total=0d;
	}
	
}
