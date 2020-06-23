
//��Ʊ��
public class Stock {
	public int stockNumber;
	//��Ʊ����
	public String stockName;
	//��Ʊ�۸�
	public float price;
	//��Ʊ����
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
		//���
		try {
			// ֱ�ӵ��ø����clone()����
			Stock s = (Stock) super.clone();
			return s;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	public static Stock[] StockPriceBubbleSort(Stock[] s, int length) {
		// temp��������Ԫ�ؽ���
		Stock temp;
		// i��¼ɨ�����
		for (int i = length - 1; i > 0; i--) {
			// ������һ�ֵ�ð������
			for (int j = 0; j < i; j++) {
				// �ӵ�һ��Ԫ�ؿ�ʼ����һ���Ƚϣ�����һ�����򽻻�
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
