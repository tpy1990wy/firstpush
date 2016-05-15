//针对树1，从第一个机架开始放数据，数据共有10000份，冗余度为A；
public class Task1 {
	int Replication;
	int BlockNum;
	int[] StartNode={9,10,11};
	int[] AvailableNode={12,13,14,15,16,17,18,19,20,21,22,23};
	Task1(){
		
	}
	Task1(int A, int B){
		Replication=A;
		BlockNum=B;
	}


}
