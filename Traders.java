
//普通交易者类
public class Traders {
	//初始资金
	public float money=0;
	//买入意愿优先级
	public Stock[] stock;

	public int type;

	public Stock initialStock;

	public Traders() {}

	public Traders(Stock s, int IPOTraNum) {
		this.initialStock = s;
		this.stock = new Stock[IPOTraNum];
	}

	public Traders(int IPOTraNum) {
		this.stock = new Stock[IPOTraNum];
	}

	public static int strategy(){
		return 0;
	}
}
