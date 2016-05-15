import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;


public class TPPLS {

	float NeiborRandom = (float) 0.001;//邻居的变异概率
	float LSNeiborRandom=(float) 0.01;//扰动的变异概率
	int NeighborSize = 5;
	int PopulationSize=100;//种群规模
	int PLSMax=5;//PLS的最大值
	double[] IdealPoint =new double[2];//参考点
	
	
	int BlockNum=100;//数据的大小
	int Replication=10;//副本的个数
	Task1 Task=new Task1(Replication,BlockNum);
	TreeOne Tree=new TreeOne();
	HDFSBalanceIndividual[] Population;
	Vector<HDFSBalanceIndividual> PE;
	Vector<HDFSBalanceIndividual> PP;
	
	int[] zuizhi=new int[3000];
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		//TreeOne Tree= new TreeOne();
        //int[] Path=Tree1.PathLink(Tree1.nodes[9], Tree1.nodes[11]);
		
		//int h=(int)0.3;
		

		for (int time = 0; time < 30; time++) {
			long startTime=System.currentTimeMillis(); 
			System.out.print(time);
			
		
        TPPLS H1=new TPPLS();
        
		
		
		
        
        H1.PE=(Vector<HDFSBalanceIndividual>) H1.Nondomination(H1.Population).clone();        
        H1.PP=(Vector<HDFSBalanceIndividual>) H1.Nondomination(H1.Population).clone();
        H1.PP=H1.InitialNeighbor2(H1.PP);
        H1.Population=H1.AggregateFunction(H1.Population,H1.IdealPoint);
        
    
        
        int PPNEZ=0;

		
		for (int i1 = 0; i1 < 300; i1++) {
			H1.PP=new Vector<HDFSBalanceIndividual>();
	        
	        //Pertubation
	        PerturbationTPPLS myPerturbation=new PerturbationTPPLS(H1.Population,  H1.PE, H1.PP, H1.IdealPoint);
			
	        H1.Population=myPerturbation.PL;
	        H1.PE=myPerturbation.PE;
	        H1.PP=myPerturbation.PP;
	        H1.IdealPoint=myPerturbation.IdealPoint;
		}
		
		
		for (int i2 = 0; i2 < 300; i2++) {
			PLSModifyTwo MyPLS=new PLSModifyTwo(H1.Population, H1.PE, H1.PP, H1.PLSMax, H1.IdealPoint);
			//PLS MyPLS=new PLS(H1.Population, H1.PE, H1.PP, H1.PLSMax, H1.IdealPoint);
	        H1.Population=MyPLS.PL;
	        H1.PE=MyPLS.PE;
	        H1.IdealPoint=MyPLS.IdealPoint;
			
		}

        
        
        
        String name3=time+"PEy.txt";
        File f = new File(name3);
        FileWriter output = new FileWriter(f);
        for (int i = 0; i < H1.PE.size(); i++) {
        	Float ff02 = new Float(H1.PE.get(i).obj[0]);
        	 output.write(ff02.toString());
        	 output.write("\t");
        	 
        	 Float ff1 = new Float(H1.PE.get(i).obj[1]);
        	 output.write(ff1.toString());
        	 output.write("\r\n");
        	             
		}
        output.close();
       
        String name4=time+"PEx.txt";
        File fPS = new File(name4);
        FileWriter outputPS = new FileWriter(fPS);
        for (int i = 0; i < H1.PE.size(); i++) {
        	for (int j = 0; j <H1.PE.get(i).Var.length; j++) {
        		Integer ff03 = new Integer(H1.PE.get(i).Var[j]);
        		outputPS.write(ff03.toString());
           	 outputPS.write("\t");
			}
        	 outputPS.write("\r\n");
        	             
		}
        outputPS.close();   
        
        long endTime=System.currentTimeMillis(); //获取结束时间  
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");  
        
		}
		
		
	}
	
	

	/**
	 * 构造函数用来给各个类传递参数用
	 * @param A
	 */
	TPPLS(int A){
		
	}
	

	/**
	 * 构造函数，用来初始化
	 */
	TPPLS(){
		//Population=new HDFSBalanceIndividual[PopulationSize];
		Population=InitialPopulation(Population);
		
	}
	
	/**
	 * @param 初始化自变量，自变量的权重，自变量的邻域
	 * @return
	 */
	HDFSBalanceIndividual[] InitialPopulation(HDFSBalanceIndividual[] Population){
		
		Population=new HDFSBalanceIndividual[PopulationSize];
		
		/**
		 * 初始化自变量矩阵
		 */
		/*for (int i = 0; i < PopulationSize; i++) {
			Population[i]=new HDFSBalanceIndividual();
			Population[i].Var=new int[Replication*BlockNum];
			for (int j = 0; j < Replication*BlockNum; j++) {
				if (j%Replication==0) {
					int m1=Task.StartNode.length;
					Random rand1=new Random();
					int r1=rand1.nextInt(m1);
					int r2=rand1.nextInt(m1);
					while (r1==r2) {
						r2=rand1.nextInt(m1);						
					}
					Population[i].Var[j]=Task.StartNode[r1];
					Population[i].Var[j+1]=Task.StartNode[r2];
					j=j+1;
				} else {
					int m2=Task.AvailableNode.length;
					Random rand2=new Random();
					int r3=rand2.nextInt(m2);
					Population[i].Var[j]=Task.AvailableNode[r3];

				}
			}
		}*/
		
		for (int i = 0; i < PopulationSize; i++) {
			Population[i]=new HDFSBalanceIndividual();
			Population[i].Var=new int[Replication*BlockNum];
			for (int j = 0; j < BlockNum; j++) {
				int[] A=GenerateNewPipleline();
				for (int k = 0; k < Replication; k++) {
					Population[i].Var[j*Replication+k]=A[k];
				}
			}
		}
		
		/**
		 * 初始化权重
		 */
		for (int i = 0; i < PopulationSize; i++) {
			Population[i].weight=new float[2];
			Population[i].weight[0]=((float)i/PopulationSize);
			Population[i].weight[1]=(1-((float)i/PopulationSize));			
		}//初始化权重向量
		
		/**
		 * 初始化邻居,随机改变若干位产生邻居
		 */

		/*for (int i = 0; i < PopulationSize; i++) {
			float[] distance=new float[PopulationSize-1];
			int[] Index=new int[PopulationSize-1];
			int ij=0;
			
			for (int j = 0; j < PopulationSize; j++) {
					if (i!=j) {
					float n1=Population[i].weight[0]-Population[j].weight[0];
					float n2=Population[i].weight[1]-Population[j].weight[1];
						distance[ij]=(float) Math.hypot(n1, n2);
						Index[ij]=j;
						ij++;						
				}					
			}
			
			for (int j = 0; j < Index.length-1; j++) {
				for (int j2 = j+1; j2 < Index.length; j2++) {
					if (distance[j]>distance[j2]) {
						float temp1=distance[j];
						distance[j]=distance[j2];
						distance[j2]=temp1;	
						
						int temp=Index[j];
						Index[j]=Index[j2];
						Index[j2]=temp;						
					}
				}
				
			}
			Population[i].Neighbor=new int[NeighborSize];
			for (int j = 0; j < NeighborSize; j++) {
				Population[i].Neighbor[j]=Index[j];				
			}
		}*/
		//初始化目标函数值和更新参考点
		IdealPoint[0]=1000000000;
		IdealPoint[1]=1000000000;
		Population =EvaluatePopulation(Population);
		System.out.println("obcom");
		//初始化参考点
		IdealPoint[0]=1000000000;
		IdealPoint[1]=1000000000;
		for (int i = 0; i < Population.length; i++) {
			if (Population[i].obj[0]<IdealPoint[0]) {
				IdealPoint[0]=Population[i].obj[0];
			}
			if (Population[i].obj[1]<IdealPoint[1]) {
				IdealPoint[1]=Population[i].obj[1];
			}
		}
		System.out.println("参考点完成");
		//初始化聚合函数值
		Population=AggregateFunction(Population, IdealPoint);	
		System.out.println("聚合函数完成");
		
		return Population;
	}
   	/**
	 * 计算自变量的目标函数值和聚合函数值
	 * @param Population
	 * @return
	 */
	HDFSBalanceIndividual[] EvaluatePopulation(HDFSBalanceIndividual[] Population){
		
		for (int i = 0; i < Population.length; i++) {
			Population[i].obj=new double[2];
			double[] MemoryRate=new double[Task.StartNode.length+Task.AvailableNode.length];
			for (int j = 0; j < MemoryRate.length; j++) {
				MemoryRate[j]=Tree.nodes[j+Tree.LeafStart].CurentRate;
			}
			double[] NetworkLoad=new double[Tree.Edge.length];
			for (int j = 0; j < Population[i].Var.length; j++) {
				if (j%Replication==Replication-1) {
					
				} else {
					int [] path=Tree.PathLink(Tree.nodes[Population[i].Var[j]], Tree.nodes[Population[i].Var[j+1]]);
					for (int k = 0; k < path.length; k++) {
						NetworkLoad[path[k]]=NetworkLoad[path[k]]+1;
					}
				}//统计网络负载
				
				//统计内存负载；
				double aaa= Tree.nodes[Population[i].Var[j]].EachBlockRate;
				
				MemoryRate[Population[i].Var[j]-Tree.LeafStart]= Tree.nodes[Population[i].Var[j]].EachBlockRate+MemoryRate[Population[i].Var[j]-Tree.LeafStart];
				}
			for (int j = 0; j < NetworkLoad.length; j++) {
				NetworkLoad[j]=NetworkLoad[j]/Tree.Edge[j];
			}
			Population[i].obj[0]=GetSTDDevition(MemoryRate);//f1存储内存负载的方差
			//Population[i].obj[1]=GetAverage(NetworkLoad)*GetSTDDevition(NetworkLoad);//f2网络负载的方差和均值之和
			Population[i].obj[1]=GetSTDDevition(NetworkLoad);
			//Population[i].obj[1]=0.01*GetAverage(NetworkLoad)+0.99*GetSTDDevition(NetworkLoad);
			//Population[i].obj[1]=GetMax(NetworkLoad);
			//Population[i].obj[1]=GetAverage(NetworkLoad);
		}
		
		return Population;
		
	}
	
	double GetMax(double[] NetworkLoad){
		double Max=0;
		for (int i = 0; i < NetworkLoad.length; i++) {
			if (NetworkLoad[i]>Max) {
				Max=NetworkLoad[i];
			}
		}
		return Max;
	}
    /**
     * 输出均值
     * @param memoryRate
     * @return
     */
	double GetAverage(double[] memoryRate){
		double sum=0;
		for (int i = 0; i < memoryRate.length; i++) {
			sum=sum+memoryRate[i];
		}
		double Average=sum/memoryRate.length;
		return Average;
    }
		/**
	 * 输出方差
	 * @param memoryRate
	 * @return
	 */
	double GetSTDDevition(double[] memoryRate){
		double Average=GetAverage(memoryRate);
		double Dev=0;
		for (int i = 0; i < memoryRate.length; i++) {
			Dev=Dev+(memoryRate[i]-Average)*(memoryRate[i]-Average);
		}
		return  Math.sqrt(Dev/memoryRate.length);
	}
	/**
	 * 计算聚合函数值
	 * @param Population
	 * @return
	 */
	HDFSBalanceIndividual[] AggregateFunction(HDFSBalanceIndividual[] Population,double[] idealPoint2){
		for (int i = 0; i < Population.length; i++) {
			double[] values=new double[Population[i].obj.length];
			for (int j = 0; j < Population[i].obj.length; j++) {
				values[j]=Population[i].weight[j]*Math.abs(Population[i].obj[j]-idealPoint2[j]);
			}
			Arrays.sort(values);
			Population[i].ValueOfAG=values[1];
		}
		return Population;
		
	}

	/**
	 * 获得非支配解
	 * @param Population
	 * @return
	 */
	Vector<HDFSBalanceIndividual> Nondomination(HDFSBalanceIndividual[] Population){
		Vector<HDFSBalanceIndividual> A1=new Vector<HDFSBalanceIndividual>();
		for (int i = 0; i < Population.length; i++) {
			int indic=0;
			for (int j = 0; j < Population.length; j++) {
				if (i!=j) {
					if (Population[i].obj[0]<Population[j].obj[0]|Population[i].obj[1]<Population[j].obj[1]) {
						
					} else if(Population[i].obj[0]>Population[j].obj[0]|Population[i].obj[1]>Population[j].obj[1]) {
						indic=1;
						break;
					}
					
				}
			}
			if (indic==0) {
				HDFSBalanceIndividual YYYYY=(HDFSBalanceIndividual) Population[i].clone();
				A1.add(YYYYY);
			}
		}
		/*HDFSBalanceIndividual[] PE=new HDFSBalanceIndividual[A1.size()];
		for (int i = 0; i < A1.size(); i++) {
			PE[i]=A1.get(i);
		}*/
		return A1;
	}

	/**
	 * 给Population添加邻居
	 * @param Population
	 * @return
	 */
	/*Vector<HDFSBalanceIndividual> InitialNeighbor(Vector<HDFSBalanceIndividual> Population)
	{
		for (int i = 0; i < Population.size(); i++) {
			//System.out.println(i);
			Population.get(i).Neighbor=new HDFSBalanceIndividual[NeighborSize];
			for (int j = 0; j < NeighborSize; j++) {
				Population.get(i).Neighbor[j]=new HDFSBalanceIndividual();
				Population.get(i).Neighbor[j].Var=new int[Population.get(i).Var.length];
					Population.get(i).Neighbor[j].Var=Population.get(i).Var.clone();

				for (int j2 = 0; j2 < NeiborRandom*(Population.get(i).Var.length); j2++) {
					Random R1=new Random();
					int IND=R1.nextInt(Population.get(i).Var.length);
					if (IND%Replication>1) {
						int IND1=R1.nextInt(Task.AvailableNode.length);
						while (Task.AvailableNode[IND1]==Population.get(i).Var[IND]) {
							IND1=R1.nextInt(Task.AvailableNode.length);							
						}
						Population.get(i).Neighbor[j].Var[IND]=Task.AvailableNode[IND1];
					} else {
						if (IND%Replication==1) {
							int IND1=R1.nextInt(Task.StartNode.length);
							while (Task.StartNode[IND1]==Population.get(i).Var[IND]|Task.StartNode[IND1]==Population.get(i).Var[IND-1]) {
								IND1=R1.nextInt(Task.StartNode.length);							
							}
							Population.get(i).Neighbor[j].Var[IND]=Task.StartNode[IND1];
						} else {
							int IND1=R1.nextInt(Task.StartNode.length);
							while (Task.StartNode[IND1]==Population.get(i).Var[IND]|Task.StartNode[IND1]==Population.get(i).Var[IND+1]) {
								IND1=R1.nextInt(Task.StartNode.length);
						}
							Population.get(i).Neighbor[j].Var[IND]=Task.StartNode[IND1];

					}
				}
					
				}
			}
			Population.get(i).Neighbor=EvaluatePopulation(Population.get(i).Neighbor);
			for (int j = 0; j < Population.get(i).Neighbor.length; j++) {
				if (Population.get(i).Neighbor[j].obj[0]<IdealPoint[0]) {
					IdealPoint[0]=Population.get(i).Neighbor[j].obj[0];
				}
				if (Population.get(i).Neighbor[j].obj[1]<IdealPoint[1]) {
					IdealPoint[1]=Population.get(i).Neighbor[j].obj[1];
				}
			}
		}
		return Population;
	}*/
	Vector<HDFSBalanceIndividual> InitialNeighbor2(Vector<HDFSBalanceIndividual> Population){
		for (int i = 0; i < Population.size(); i++) {
			Population.get(i).Neighbor=new HDFSBalanceIndividual[NeighborSize];
			for (int j = 0; j < NeighborSize; j++) {
				Population.get(i).Neighbor[j]=new HDFSBalanceIndividual();
				Population.get(i).Neighbor[j].Var=new int[Population.get(i).Var.length];
				Population.get(i).Neighbor[j].Var=Population.get(i).Var.clone();
				for (int j2 = 0; j2 < NeiborRandom*BlockNum; j2++) {
					Random r=new Random();
					int r1=r.nextInt(BlockNum);
					int[] A=GenerateNewPipleline();
					for (int k = 0; k < Replication; k++) {
						Population.get(i).Neighbor[j].Var[Replication*r1+k]=A[k];
					}
				}
			}
			Population.get(i).Neighbor=EvaluatePopulation(Population.get(i).Neighbor);
			for (int j = 0; j < Population.get(i).Neighbor.length; j++) {
				if (Population.get(i).Neighbor[j].obj[0]<IdealPoint[0]) {
					IdealPoint[0]=Population.get(i).Neighbor[j].obj[0];
				}
				if (Population.get(i).Neighbor[j].obj[1]<IdealPoint[1]) {
					IdealPoint[1]=Population.get(i).Neighbor[j].obj[1];
				}
			}
		}
		return Population;
	}
	//void PLS(HDFSBalanceIndividual[] Population, Vector<HDFSBalanceIndividual> PE,Vector<HDFSBalanceIndividual> PP, int PLSMax){
	int NumNondomination(Vector<HDFSBalanceIndividual> A){
		int Num=0;
		for (int i = 0; i < A.size(); i++) {
			int indicator=0;
			for (int j = 0; j < A.size(); j++) {
				if (i!=j) {
					if (NondominationRelation(A.get(j), A.get(i))==1) {
						indicator=1;
						break;
					}
					
				}
				
			}
			if (indicator==0) {
				Num=Num+1;
				
			}
			
		}
		return Num;
	}
	int NondominationRelation(HDFSBalanceIndividual A, HDFSBalanceIndividual B){
		int Indicator=0;
		if (A.obj[0]<=B.obj[0]&A.obj[1]<=B.obj[1]) {
			if (A.obj[0]<B.obj[0]|A.obj[1]<B.obj[1]) {
				Indicator=1;
			}
		}
		return Indicator;
	}
	
	int[] GenerateNewSolution(){
		int[] NewSolution=new int[Replication*BlockNum];
		for (int i = 0; i < BlockNum; i++) {
			int[] A=GenerateNewPipleline();
			for (int j = 0; j < Replication; j++) {
				NewSolution[i*Replication+j]=A[j];
			}
		}	
		
		return NewSolution;
	}

	int[] GenerateNewPipleline(){
		int[] PartNew=new int[Replication];
		int[] Flag=new int[Tree.RackNum];//机架存了几副本
		int[] Flag1=new int[Tree.nodes.length];//每个节点存了几个副本
		
		Random r=new Random();
		int r1=r.nextInt(Tree.RackNum);//起始点的机架位置
		int r2=r.nextInt(Tree.Rack.get(r1).size());//起始点的精确位置
		Flag[r1]=Flag[r1]+1;
		Flag1[Tree.Rack.get(r1).get(r2)]=Flag1[Tree.Rack.get(r1).get(r2)]+1;
		PartNew[0]=Tree.Rack.get(r1).get(r2);

		
		
			int r3=r.nextInt(Tree.RackNum);
			while (r3==r1) {
				r3=r.nextInt(Tree.RackNum);			
			}
			Flag[r3]=Flag[r3]+1;
			
			int r31=r.nextInt(Tree.Rack.get(r3).size());//起始点的精确位置
			PartNew[1]=Tree.Rack.get(r3).get(r31);
			Flag1[Tree.Rack.get(r3).get(r31)]=Flag1[Tree.Rack.get(r3).get(r31)]+1;
			
			Flag[r3]=Flag[r3]+1;
			int r32=r.nextInt(Tree.Rack.get(r3).size());//起始点的精确位置
			while (r32==r31) {
				r32=r.nextInt(Tree.Rack.get(r3).size());			
			}
			PartNew[2]=Tree.Rack.get(r3).get(r32);
			Flag1[Tree.Rack.get(r3).get(r32)]=Flag1[Tree.Rack.get(r3).get(r32)]+1;
		if (Replication-3!=0) 
			{
			//int[] cnodes=new int[Replication-2];
			for (int i = 3; i < Replication; i++) {
				int r4=r.nextInt(Tree.RackNum);
				
				int r5=r.nextInt(Tree.Rack.get(r4).size());
				
			
				PartNew[i]=Tree.Rack.get(r4).get(r5);
			}
		}
		
		/*int[] CN=new int[Replication-1];	//路径长度
		int[] CN1=new int[Replication-1];  //位置信息
		for (int i = 2; i < Replication; i++) {
			int[] P=Tree.PathLink(Tree.nodes[PartNew[i]], Tree.nodes[PartNew[0]]);
			CN[i-2]=P.length;
			CN1[i-2]=PartNew[i];
		}
		
		for (int i = 0; i < CN1.length-1; i++) {
			for (int j = i+1; j < CN1.length; j++) {
				if (CN[j]<CN[i]) {
					int temp=CN[i];
					CN[i]=CN[j];
					CN[j]=temp;
					
					int temp1=CN1[i];
					CN1[i]=CN1[j];
					CN1[j]=temp1;					
				}
			}
		}
		
		for (int i = 0; i < Replication-2; i++) {
			PartNew[i+2]=CN1[i];
		}*/
		
		
		
		
		return PartNew;
	}


}


