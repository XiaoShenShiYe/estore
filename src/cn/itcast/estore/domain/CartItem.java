package cn.itcast.estore.domain;
/**
 * 购物项的实体
 * @author Admin
 *
 */
public class CartItem {
	private Book book;//图书的对象
	private Integer count;//图书 的数量
	//private Double subtotal;
	//图书的总计价格
	public Double getSubtotal() {
		return count*book.getPrice();
	}
	/*public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}*/
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
	
}
