
//��ͨ��������
public class Traders {
	//��ʼ�ʽ�
	public float money=0;
	//������Ը���ȼ�
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
