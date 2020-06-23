import java.util.*;


//��������
public class main {
	// ��Ʊ���۸�ð������


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

	// ����ָ��
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

		// ��ʼ��
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("�����뽻����������");
		int TraNum = sc.nextInt();
		System.out.println("������ԭʼ�ɽ�����������");
		int IPONum = sc.nextInt();
		// �½������ߡ�ԭʼ�ɷ����̡���Ʊ��������
		Traders[] t = new Traders[TraNum];
		Stock[] s = new Stock[IPONum];
		// ��ʼ����Ʊ�����ơ���š��۸���������ԭʼ�ɷ����̣����й�Ʊ���ʽ𡢱�ţ�ǰi����IPO
		for (int i = 0; i < IPONum; i++) {
			t[i] = new Traders(IPONum);
		}
		for (int j = IPONum; j < TraNum; j++) {
			t[j] = new Traders(IPONum);
		}
		for (int i = 0; i < IPONum; i++) {
			System.out.println("�������Ʊ���� �۸� ����������");
			String name = sc.next();
			float price = sc.nextFloat();
			int num = sc.nextInt();
			s[i]=new Stock(i,name,price,num,TraNum);
			for (int j = 0; j < TraNum; j++) {
				t[j].stock[i]=new Stock(i,name,0,0,TraNum);
			}
			t[i].stock[0]=new Stock(i,name,price,num,TraNum);
		}

		// ��ʼ����ͨ�����ߣ������ʽ𡢱�ţ�
		float minPrice=s[0].price;
		float minNum=s[0].num;
		for (Stock stock : s) {
			if (stock.price*stock.num < minPrice*minNum) {
				minPrice = stock.price;
				minNum = stock.num;
			}
		}
		for (int i = TraNum-IPONum; i < TraNum; i++) {
			// �ں���Χ�������ʼ����ͨ�����ߵ��ֽ���
			Random r = new Random();
			t[i].money = r.nextInt((int) Math.min(1,(minPrice * minNum / 3 - minPrice)))+minPrice;
		}

		// ����ָ����ʼ��
		float index;
		int loop=1;
		Stock[] ss;

		// ����
		loop:while (true) {
			// ���һ����Ʊ�������м۸����򷽱���������Ը���Ҳ��ı�ԭ���з������
			ss = Stock.StockPriceBubbleSort(s, IPONum);
			// Ns��Na��s��˳����ͬ

			/* ��ͨ������������Ը���� */
			for (int i = 0; i < TraNum; i++) {
				int count = 0;
				// ��Ʊѭ��
				for (int j = 0; j < IPONum; j++) {
					// �������������
					if (t[i].money < ss[j].price)
						continue;
					// ���������������Ը�嵥
					else {
						s[ss[j].stockNumber].buy[i]= 1;
						count++;
					}
				}
			}

			/* ԭʼ�ɳ�����������Ը���� */
			// ���ڵ�iλ������
			for (int i = 0; i < TraNum; i++) {
				int a = 0;
				// �趨ԭʼ�ɵ�������Ը����1
				if (i<IPONum) {
					if (t[i].stock[0] != null && t[i].stock[0].num != 0) {
						s[i].sell[i] = t[i].stock[0].num;
					}
				}
				// ������Ʊ

				Random rand = new Random();
				// ����i�����̵ĵ�aֻ��Ʊs[a]
				for (Stock stock:t[i].stock) {
					stock.sell[i]=Traders.strategy();
				}
			}

			// �����Ʊ�۸񣬳�ʼ�����ܣ�����������
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
			//�����Ը
			for(Stock stock:s){
				Arrays.fill(stock.sell, 0);
				Arrays.fill(stock.buy, 0);
			}


			//���¼������ָ��
			index = StockIndex(s, IPONum);
			System.out.println(index);
			loop++;
			//�ж��Ƿ������һ��
			System.out.println("�����Ƿ������һ�֣���ȷ���밴1���˳����������");
			int p = sc.nextInt();
			if(p == 1)
				continue loop;
			else
				break loop;
		}
	}
}
