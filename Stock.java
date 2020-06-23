
//股票类
public class Stock {
	public int stockNumber;
	//股票名称
	public String stockName;
	//股票价格
	public float price;
	//股票总数
	public int num;

	public int[] buy;

	public int[] sell;
	
	public Stock() {}
	
	public Stock(int stockNumber,String stockName, float price, int num,int TraNum) {
		this.stockNumber = stockNumber;
		this.num = num;
		this.price = price;
		this.stockName = stockName;
		this.buy=new int[TraNum];
		this.sell=new int[TraNum];
	}

	public Stock(int stockNumber,String stockName, float price,int num) {
		this.stockNumber = stockNumber;
		this.price = price;
		this.stockName = stockName;
		this.num = num;
	}
	@Override
	public Object clone() {
		//深拷贝
		try {
			// 直接调用父类的clone()方法
			Stock s = (Stock) super.clone();
			return s;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	public static Stock[] StockPriceBubbleSort(Stock[] s, int length) {
		// temp用于数组元素交换
		Stock temp;
		// i记录扫描次数
		for (int i = length - 1; i > 0; i--) {
			// 进行这一轮的冒泡排序
			for (int j = 0; j < i; j++) {
				// 从第一个元素开始和下一个比较，比下一个大则交换
				if (s[j].price > s[j + 1].price) {
					temp = s[j];
					s[j] = s[j + 1];
					s[j + 1] = temp;
				}
			}
		}
		return s;
	}
}
