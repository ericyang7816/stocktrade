import java.util.*;


//迭代程序
public class main {
	// 股票按价格冒泡排序


	public static int[] randomCommon(int min, int max, int n){
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		Random r = new Random(100);
		int[] result = new int[n];
		int count = 1;
		while(count < n) {
			int num = (int) ( r.nextFloat()* (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	// 大盘指数
	public static float StockIndex(Stock[] s, int length) {
		float index = 0;
		int indexnum = 0;
		for (int i = 0; i < length; i++) {
			index += s[i].price * s[i].num;
			indexnum += s[i].num;
		}
		index = index / indexnum;
		return index;
	}

	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 初始化
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入交易者数量：");
		int TraNum = sc.nextInt();
		System.out.println("请输入原始股交易者数量：");
		int IPONum = sc.nextInt();
		// 新建交易者、原始股发行商、股票对象数组
		Traders[] t = new Traders[TraNum];
		Stock[] s = new Stock[IPONum];
		// 初始化股票（名称、编号、价格、数量）和原始股发行商（持有股票、资金、编号）前i名是IPO
		for (int i = 0; i < IPONum; i++) {
			t[i] = new Traders(IPONum);
		}
		for (int j = IPONum; j < TraNum; j++) {
			t[j] = new Traders(IPONum);
		}
		for (int i = 0; i < IPONum; i++) {
			System.out.println("请输入股票名称 价格 发行数量：");
			String name = sc.next();
			float price = sc.nextFloat();
			int num = sc.nextInt();
			s[i]=new Stock(i,name,price,num,TraNum);
			for (int j = 0; j < TraNum; j++) {
				t[j].stock[i]=new Stock(i,name,0,0,TraNum);
			}
			t[i].stock[0]=new Stock(i,name,price,num,TraNum);
		}

		// 初始化普通交易者（持有资金、编号）
		float minPrice=s[0].price;
		float minNum=s[0].num;
		for (Stock stock : s) {
			if (stock.price*stock.num < minPrice*minNum) {
				minPrice = stock.price;
				minNum = stock.num;
			}
		}
		for (int i = TraNum-IPONum; i < TraNum; i++) {
			// 在合理范围内随机初始化普通交易者的现金数
			Random r = new Random();
			t[i].money = r.nextInt((int) Math.min(1,(minPrice * minNum / 3 - minPrice)))+minPrice;
		}

		// 大盘指数初始化
		float index;
		int loop=1;
		Stock[] ss;

		// 迭代
		loop:while (true) {
			// 抽出一个股票副本进行价格排序方便编排买家意愿，且不改变原序列方便查找
			ss = Stock.StockPriceBubbleSort(s, IPONum);
			// Ns、Na和s的顺序相同

			/* 普通交易者买入意愿决定 */
			for (int i = 0; i < TraNum; i++) {
				int count = 0;
				// 股票循环
				for (int j = 0; j < IPONum; j++) {
					// 如果买不起则跳过
					if (t[i].money < ss[j].price)
						continue;
					// 如果买得起则加入意愿清单
					else {
						s[ss[j].stockNumber].buy[i]= 1;
						count++;
					}
				}
			}

			/* 原始股持有者卖出意愿决定 */
			// 对于第i位持有者
			for (int i = 0; i < TraNum; i++) {
				int a = 0;
				// 设定原始股的卖出意愿总是1
				if (i<IPONum) {
					if (t[i].stock[0] != null && t[i].stock[0].num != 0) {
						s[i].sell[i] = t[i].stock[0].num;
					}
				}
				// 其他股票

				Random rand = new Random();
				// 对于i发行商的第a只股票s[a]
				for (Stock stock:t[i].stock) {
					stock.sell[i]=Traders.strategy();
				}
			}

			// 计算股票价格，初始化货架（方便买卖）
			for (int i = 0; i < IPONum; i++) {
				float na = 0;
				float ns = 0;
				for (int j = 0; j < TraNum; j++) {
					na = na + s[i].buy[j];
					ns = ns + s[i].sell[j];
				}
				if(na!=0||ns!=0)
					s[i].price = s[i].price * (na / ns);
			}
			int[] order = randomCommon(0,IPONum,IPONum);
			for (int i : order) {
				int buyFlag = 0;
				int sellFlag = 0;
				for (int n : s[i].buy) {
					if (n != 0) {
						buyFlag = 1;
						break;
					}
				}
				for (int n : s[i].sell) {
					if (n != 0) {
						sellFlag = 1;
						break;
					}
				}
				while (sellFlag == 1 && buyFlag == 1) {
					int maxBuyTra = 0;
					int maxBuyNum = 0;

					for (int j = 0; j < TraNum; j++) {
						if(t[j].money>s[i].price) {
							if (s[i].buy[j] > maxBuyNum) {
								maxBuyNum = s[i].buy[j];
								maxBuyTra = j;
							}
						}
						else s[i].buy[j]=0;
					}
					int maxSellTra = 0;
					int maxSellNum = 0;

					for (int j = 0; j < TraNum; j++) {
						if (s[i].sell[j] > maxSellNum) {
							maxSellNum = s[i].sell[j];
							maxSellTra = j;
						}
					}

					int tradeNum = Math.min(Math.min(maxBuyNum,maxSellNum),(int)(t[maxBuyTra].money/s[i].price));
					t[maxBuyTra].stock[i].price=(t[maxBuyTra].stock[i].price*t[maxBuyTra].stock[i].num+s[i].price*tradeNum)/(t[maxBuyTra].stock[i].num+tradeNum);
					s[i].buy[maxBuyTra]-=tradeNum;
					s[i].sell[maxSellTra]-=tradeNum;
					t[maxBuyTra].money -= tradeNum * s[i].price;
					t[maxSellTra].money += tradeNum * s[i].price;
					t[maxBuyTra].stock[i].num+=tradeNum;
					t[maxSellTra].stock[i].num-=tradeNum;

					buyFlag = 0;
					sellFlag = 0;
					for (int n : s[i].buy) {
						if (n != 0) {
							buyFlag = 1;
							break;
						}
					}
					for (int n : s[i].sell) {
						if (n != 0) {
							sellFlag = 1;
							break;
						}
					}
				}
			}
			//清空意愿
			for(Stock stock:s){
				Arrays.fill(stock.sell, 0);
				Arrays.fill(stock.buy, 0);
			}


			//重新计算大盘指数
			index = StockIndex(s, IPONum);
			System.out.println(index);
			loop++;
			//判断是否进入下一轮
			System.out.println("请问是否进入下一轮？（确认请按1，退出按任意键）");
			int p = sc.nextInt();
			if(p == 1)
				continue loop;
			else
				break loop;
		}
	}
}
