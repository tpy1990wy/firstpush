import java.util.Vector;


class TreeOne{
	Node[] nodes=new Node[24];//包括交换机在内的24个节点，广度优先编码
	double[]  Edge=new double[23];//所有的link，深度优先编码	
	int    LeafStart=9;//叶子节点从9开始
	
	int RackNum=5;
	Vector<Vector<Integer>> Rack=new Vector<Vector<Integer>>();
	Vector<Integer> rack1=new Vector<Integer>();
	Vector<Integer> rack2=new Vector<Integer>();
	Vector<Integer> rack3=new Vector<Integer>();
	Vector<Integer> rack4=new Vector<Integer>();
	Vector<Integer> rack5=new Vector<Integer>();
	
	
	public TreeOne() {
		// TODO Auto-generated constructor stub
		//各个rack的隶属情况
		rack1.add(9);
		rack1.add(10);
		rack1.add(11);
		
		rack2.add(12);
		rack2.add(13);
		rack2.add(14);
		rack2.add(15);
		
		rack3.add(16);
		rack3.add(17);
		rack3.add(18);
		rack3.add(19);
		
		rack4.add(20);
		rack4.add(21);
		
		rack5.add(22);
		rack5.add(23);
		
		Rack.add(rack1);
		Rack.add(rack2);
		Rack.add(rack3);
		Rack.add(rack4);
		Rack.add(rack5);
		//各个边的负载能力；
		Edge[0]=0.9;
		Edge[1]=0.8;
		Edge[2]=0.9;
		Edge[3]=0.7;
		Edge[4]=0.6;
		Edge[5]=0.65;
		Edge[6]=0.7;
		Edge[7]=0.8;
		
		
		/*Edge[0]=1;
		Edge[1]=1;
		Edge[2]=1;
		Edge[3]=0.8;
		Edge[4]=0.8;
		Edge[5]=0.8;
		Edge[6]=0.8;
		Edge[7]=0.8;*/
		Edge[8]=0.3;
		Edge[9]=0.35;
		Edge[10]=0.4;
		Edge[11]=0.45;
		Edge[12]=0.25;
		Edge[13]=0.3;
		Edge[14]=0.4;
		Edge[15]=0.3;
		Edge[16]=0.25;
		Edge[17]=0.3;
		Edge[18]=0.35;
		Edge[19]=0.25;
		Edge[20]=0.3;
		Edge[21]=0.4;
		Edge[22]=0.3;
		/*for (int i = 8; i < 23; i++) {
			Edge[i]=0.4;
		}*/
		
		
		for (int i = 0; i < 24; i++) {
			nodes[i]=new Node();
			/*if(i>8){
			nodes[i].EachBlockRate=0.00001;
			}*/
			
		}
		nodes[9].EachBlockRate=0.00002;
		nodes[10].EachBlockRate=0.00003;
		nodes[11].EachBlockRate=0.00004;
		nodes[12].EachBlockRate=0.00005;
		nodes[13].EachBlockRate=0.00001;
		nodes[14].EachBlockRate=0.00003;
		nodes[15].EachBlockRate=0.00001;
		nodes[16].EachBlockRate=0.00002;
		nodes[17].EachBlockRate=0.00003;
		nodes[18].EachBlockRate=0.00002;
		nodes[19].EachBlockRate=0.00003;
		nodes[20].EachBlockRate=0.00001;
		nodes[21].EachBlockRate=0.00003;
		nodes[22].EachBlockRate=0.00001;
		nodes[23].EachBlockRate=0.00002;
		
		
		
		
		nodes[9].CurentRate=  0.1;
		nodes[10].CurentRate= 0.3;
		nodes[11].CurentRate= 0.1;
		nodes[12].CurentRate= 0.5;
		nodes[13].CurentRate= 0.1;
		nodes[14].CurentRate= 0.1;
		nodes[15].CurentRate=0.5;
		nodes[16].CurentRate= 0.1;
		nodes[17].CurentRate= 0.3;
		nodes[18].CurentRate= 0.1;
		nodes[19].CurentRate= 0.6;
		nodes[20].CurentRate= 0.1;
		nodes[21].CurentRate= 0.05;
		nodes[22].CurentRate= 0.1;
		nodes[23].CurentRate= 0.4;
		
		
		
		/*nodes[9].CurentRate=  0.1;
		nodes[10].CurentRate= 0.1;
		nodes[11].CurentRate= 0.1;
		nodes[12].CurentRate= 0.1;
		nodes[13].CurentRate= 0.1;
		nodes[14].CurentRate= 0.1;
		nodes[15].CurentRate=0.1;
		nodes[16].CurentRate= 0.1;
		nodes[17].CurentRate= 0.1;
		nodes[18].CurentRate= 0.1;
		nodes[19].CurentRate= 0.1;
		nodes[20].CurentRate= 0.1;
		nodes[21].CurentRate= 0.1;
		nodes[22].CurentRate= 0.1;
		nodes[23].CurentRate= 0.1;*/
		
		nodes[0].ID=0;
		Node[] B0= {nodes[1],nodes[2],nodes[3]};
		nodes[0].AddChild(nodes[0], B0);
		
		nodes[1].ID=1;
		nodes[1].AddParent(nodes[1], nodes[0]);
		Node[] B1= {nodes[4],nodes[5]};
		nodes[1].AddChild(nodes[1], B1);
		
		nodes[2].ID=2;
		nodes[2].AddParent(nodes[2], nodes[0]);
		Node[] B2= {nodes[6],nodes[7]};
		nodes[2].AddChild(nodes[2], B2);
		
		nodes[3].ID=3;
		nodes[3].AddParent(nodes[3], nodes[0]);
		Node[] B3= {nodes[8]};
		nodes[3].AddChild(nodes[3], B3);
		
		nodes[4].ID=4;
		nodes[4].AddParent(nodes[4], nodes[1]);
		Node[] B4= {nodes[9],nodes[10],nodes[11]};
		nodes[4].AddChild(nodes[4], B4);
		
		nodes[5].ID=5;
		nodes[5].AddParent(nodes[5], nodes[1]);
		Node[] B5= {nodes[12],nodes[13],nodes[14],nodes[15]};
		nodes[5].AddChild(nodes[5], B5);
		
		nodes[6].ID=6;
		nodes[6].AddParent(nodes[6], nodes[2]);
		Node[] B6={nodes[16],nodes[17],nodes[18],nodes[19]};
		nodes[6].AddChild(nodes[6], B6);
		
		nodes[7].ID=7;
		nodes[7].AddParent(nodes[7], nodes[2]);
		Node[] B7={nodes[20],nodes[21]};
		nodes[7].AddChild(nodes[7], B7);
		
		nodes[8].ID=8;
		nodes[8].AddParent(nodes[8],nodes[3] );
		Node[] B8={nodes[22],nodes[23]};
		nodes[8].AddChild(nodes[8], B8);
		
		nodes[9].AddParent(nodes[9], nodes[4]);
		nodes[10].AddParent(nodes[10], nodes[4]);
		nodes[11].AddParent(nodes[11], nodes[4]);
		nodes[12].AddParent(nodes[12], nodes[5]);
		nodes[13].AddParent(nodes[13], nodes[5]);
		nodes[14].AddParent(nodes[14], nodes[5]);
		nodes[15].AddParent(nodes[15], nodes[5]);
		nodes[16].AddParent(nodes[16], nodes[6]);
		nodes[17].AddParent(nodes[17], nodes[6]);
		nodes[18].AddParent(nodes[18], nodes[6]);
		nodes[19].AddParent(nodes[19], nodes[6]);
		nodes[20].AddParent(nodes[20], nodes[7]);
		nodes[21].AddParent(nodes[21], nodes[7]);
		nodes[22].AddParent(nodes[22], nodes[8]);
		nodes[23].AddParent(nodes[23], nodes[8]);
		
		nodes[9].ID=9;
		nodes[10].ID=10;
		nodes[11].ID=11;
		nodes[12].ID=12;
		nodes[13].ID=13;
		nodes[14].ID=14;
		nodes[15].ID=15;
		nodes[16].ID=16;
		nodes[17].ID=17;
		nodes[18].ID=18;
		nodes[19].ID=19;
		nodes[20].ID=20;
		nodes[21].ID=21;
		nodes[22].ID=22;
		nodes[23].ID=23;
	}
	
	int[] PathLink(Node A, Node B) //统计两个节点之间交流要经过的link；
	{
	    
		Vector LeftLink =new Vector();
		LeftLink.add(A.ID-1);
		Node LeftNode=A.Parent;
		
		Vector RightLink =new Vector();
		RightLink.add(B.ID-1);
		Node RightNode=B.Parent;
		
		
		
		while (LeftNode.ID!=RightNode.ID) {
			LeftLink.add(LeftNode.ID-1);
			LeftNode=LeftNode.Parent;
			RightLink.add(RightNode.ID-1);
			RightNode=RightNode.Parent;
		}

		
		int[] Path=new int[LeftLink.size()+RightLink.size()];
		
		for (int i = 0; i < LeftLink.size(); i++) {
			Path[i]=(int) LeftLink.get(i);
			//System.out.print(LeftLink.get(i));
		}
		
		for (int i = 0; i < RightLink.size(); i++) {
			Path[LeftLink.size()+i]=(int) RightLink.get(i);
			//System.out.print(RightLink.get(i));
		}
		
		return Path;
		
		
	}
	

	
}






class Node{
	Node Parent;
	Node[] Childs;
	double EachBlockRate;//每个块占的比例
	double CurentRate;//现在已经占的比例
	int ID;
	
    public  void AddParent(Node A, Node B) {
    	A.Parent=B;	
    }
    


	public  void AddChild(Node A, Node[] B) {
    	A.Childs=B;	
    }
}
