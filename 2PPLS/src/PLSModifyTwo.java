
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;


public class PLSModifyTwo {

	int IID;
	int PLSMax;
	HDFSBalanceIndividual[] PL;
	Vector<HDFSBalanceIndividual> PE;
	Vector<HDFSBalanceIndividual> PP;
	double[] IdealPoint =new double[2];//参考点
	
	PLSModifyTwo(HDFSBalanceIndividual[] PL1,Vector<HDFSBalanceIndividual> PE1,Vector<HDFSBalanceIndividual> PP1,int PLSMax1,double[] idealPoint2) throws InterruptedException{
		int j=0;
		PLSMax=PLSMax1;
		for (int i = 0; i < IdealPoint.length; i++) {
			IdealPoint[i]=idealPoint2[i];
		}

		TPPLS H=new TPPLS(1);
			int crowd=5;
			Vector<HDFSBalanceIndividual> PPE=CrowdingJudge(PE1,crowd);
		
			for (int i = 0; i < PPE.size(); i++) {
				for (int kk = 0; kk < 1; kk++) {
				int flag=0;
				HDFSBalanceIndividual NewInd=new HDFSBalanceIndividual();
				int[] BasicGene=GenerateNewPipleline();
				//BasicGene[1]=18;
				//int[] BasicGene={10,22,18};
				NewInd.Var=PPE.get(i).Var.clone();
				int PNum=PPE.get(i).Var.length/H.Replication;
				Random R=new Random();
				
				for (int k = 0; k <H.BlockNum/10; k++) {
					if (flag>H.BlockNum) {
						//System.out.print("继续");
						/*System.out.print(BasicGene[0]);
						System.out.print(BasicGene[1]);
						System.out.print(BasicGene[2]);*/
						BasicGene=GenerateNewPipleline();
						flag=0;
					}
					
					
					int location=R.nextInt(PNum);
					for (int l = 0; l < H.Replication; l++) {
						NewInd.Var[location*H.Replication+l]= BasicGene[l];
					}
					HDFSBalanceIndividual[] NewInd1={NewInd};
					H.EvaluatePopulation(NewInd1);
					NewInd.obj=NewInd1[0].obj.clone();
					
					if (NewInd.obj[0]<IdealPoint[0]) {
						IdealPoint[0]=NewInd.obj[0];
					}
					if (NewInd.obj[1]<IdealPoint[1]) {
						IdealPoint[1]=NewInd.obj[1];
					}
					
					PL1=Updata1(NewInd,PL1);
						IID=0;						
						PE1=Update2(NewInd, PE1);						
						if (IID==0) {
							//break;
							flag=flag+1;
						}else {
							flag=0;
						}
						/*if (flag>H.BlockNum/10) {
							//Thread.sleep(1000);
							System.out.println("连续不出的次数啊啊啊啊啊啊啊啊啊啊");
							System.out.println(flag);
						
						}*/
							
				}
				}
				
			}
			
			//System.out.println();
			//System.out.println(PP1.size());
		
		PL=PL1;
		PE=PE1;
		PP=PP1;
		/*if (NumNondomination(PE)!=PE.size()) {
			System.out.print("PLS");
			System.exit(1);
		}*/
		
		
		
	}
	
	 Vector<HDFSBalanceIndividual> CrowdingJudge(
			Vector<HDFSBalanceIndividual> pE1, int crowd) {
		// TODO Auto-generated method stub
		 Vector<HDFSBalanceIndividual> ppE=new Vector<HDFSBalanceIndividual>();
		 Vector<HDFSBalanceIndividual> ppE1=new Vector<HDFSBalanceIndividual>();
		 double[] crowdingdistance=new double[pE1.size()];

		 for (int i = 0; i < crowdingdistance.length; i++) {
			HDFSBalanceIndividual NewInd=new HDFSBalanceIndividual();
			NewInd.Var=pE1.get(i).Var;
			NewInd.obj=pE1.get(i).obj;
			ppE1.add(NewInd);
		}
		 
		 for (int i = 0; i < crowdingdistance.length-1; i++) {
			 for (int j = i+1; j < crowdingdistance.length; j++) {
				 if (ppE1.get(i).obj[0]>ppE1.get(j).obj[0]) {
					 HDFSBalanceIndividual NewInd=new HDFSBalanceIndividual();
					 NewInd=ppE1.get(i);
					 ppE1.set(i, ppE1.get(j));
					 ppE1.set(j, NewInd);
				}
			}
			
		}
		 
		 for (int i = 0; i < crowdingdistance.length; i++) {
			if (i==0) {
				crowdingdistance[i]=100000000;
			}
			else if (i==crowdingdistance.length-1) {
				crowdingdistance[i]=100000000;
			}else {
				double a=Math.abs(ppE1.get(i+1).obj[0]-ppE1.get(i-1).obj[0])+Math.abs(ppE1.get(i+1).obj[1]-ppE1.get(i-1).obj[1]);
				crowdingdistance[i]=a;
			}
			
		}
		 
		 int[] px=new int[crowdingdistance.length];
		 for (int i = 0; i < crowdingdistance.length; i++) {
			px[i]=i;
		}
		 
		 for (int i = 0; i < crowdingdistance.length-1; i++) {
			 for (int j = i+1; j < crowdingdistance.length; j++) {
				 if (crowdingdistance[i]<crowdingdistance[j]) {
					 double aa=crowdingdistance[i];
					 crowdingdistance[i]=crowdingdistance[j];
					crowdingdistance[j]=aa;
					 int a=px[i];
					 px[i]=px[j];
					 px[j]=a;
				}
			}
			
		}
		 if (crowdingdistance.length>crowd) {
			 for (int i = 0; i < crowd; i++) {
					ppE.add(ppE1.get(px[i]));
				}
		}else {
			for (int i = 0; i < crowdingdistance.length; i++) {
				ppE.add(ppE1.get(i));
			}
		}
		 
		return ppE;
	}

	HDFSBalanceIndividual[] Updata1(HDFSBalanceIndividual A,HDFSBalanceIndividual[] P){
		int indictor=0;//代表false
		int[] Flag=new int[P.length];
		Vector<Integer> Ind=new Vector<Integer>();
		
		for (int i = 0; i < Flag.length; i++) {
			if (Flag[i]==0) {
				Ind.add(i);
			}
		}
		

		while (Ind.size()!=0&indictor==0) {
			Random r1= new Random();
			int r=r1.nextInt(Ind.size());
			double Aa=AggregateValue( A, P[Ind.get(r)],IdealPoint);
			if (Aa<AggregateValue( P[Ind.get(r)], P[Ind.get(r)],IdealPoint)) {
				indictor=1;
				P[Ind.get(r)].obj=A.obj.clone();
				P[Ind.get(r)].Var=A.Var.clone();
				P[Ind.get(r)].ValueOfAG=Aa;
				//P[r]= GenerateNeibor(P[r]);		
				indictor=1;
			}else {
				Flag[Ind.get(r)]=1;
				Ind.remove(r);
			}
			
			
		}
		
	
		return P;
		
	}
	Vector<HDFSBalanceIndividual> Update2(HDFSBalanceIndividual A, Vector<HDFSBalanceIndividual> P){
		if (P.size()==0) {
			HDFSBalanceIndividual AAA=new HDFSBalanceIndividual();
			AAA.obj=A.obj.clone();
			AAA.Var=A.Var.clone();
			P.add(AAA);	
			IID=1;
		} else {
			int indicator=0;//没有能支配A的
			for (int i = 0; i < P.size(); i++) {
			     if (NondominationRelation(P.get(i),A)==1) {
					indicator=1;
					break;
				}
			}
			if (indicator==0) {
				Vector<HDFSBalanceIndividual> indd=new Vector<HDFSBalanceIndividual>();
				for (int i = 0; i < P.size(); i++) {
					if (NondominationRelation(A,P.get(i))==1) {
						indd.add(P.get(i));
					}
				}
				
				for (int i = 0; i < indd.size(); i++) {
					P.remove(indd.get(i));
				}
				//A=GenerateNeibor(A);
				HDFSBalanceIndividual AAAAA=new HDFSBalanceIndividual();
				AAAAA.Var=A.Var.clone();
				AAAAA.obj=A.obj.clone();
				P.add(AAAAA);				
				IID=1;
			}

		}
		

		return P;
		
		
	}
	/**
	 * 非支配关系，A支配B为1，否则为0;
	 * @param A
	 * @param B
	 * @return
	 */
	int NondominationRelation(HDFSBalanceIndividual A, HDFSBalanceIndividual B){
		int Indicator=0;
		if (A.obj[0]<=B.obj[0]&A.obj[1]<=B.obj[1]) {
			if (A.obj[0]<B.obj[0]|A.obj[1]<B.obj[1]) {
				Indicator=1;
			}
		}
		return Indicator;
	}

	double AggregateValue(HDFSBalanceIndividual A1, HDFSBalanceIndividual B,double[] idealPoint2){
		double[] values=new double[B.obj.length];
		for (int j = 0; j < B.obj.length; j++) {
			values[j]=B.weight[j]*Math.abs(A1.obj[j]-idealPoint2[j]);
		}
		Arrays.sort(values);
		return values[1];
	}

	/**
	 * 为某个解产生邻域；
	 * @param A
	 * @return
	 */
	/*HDFSBalanceIndividual GenerateNeibor(HDFSBalanceIndividual A){
		Task1 TaskN=new Task1();
		HDFSBalance H=new HDFSBalance(1);
		
				A.Neighbor=new HDFSBalanceIndividual[H.NeighborSize];
				for (int j = 0; j < H.NeighborSize; j++) {
					A.Neighbor[j]=new HDFSBalanceIndividual();
					A.Neighbor[j].Var=new int[A.Var.length];
					
							A.Neighbor[j].Var=A.Var.clone();
						
					for (int j2 = 0; j2 < H.NeiborRandom*(A.Var.length); j2++) {
						Random R1=new Random();
						int IND=R1.nextInt(A.Var.length);
						if (IND%H.Replication>1) {
							int IND1=R1.nextInt(TaskN.AvailableNode.length);
							while (TaskN.AvailableNode[IND1]==A.Var[IND]) {
								IND1=R1.nextInt(TaskN.AvailableNode.length);
							}
							A.Neighbor[j].Var[IND]=TaskN.AvailableNode[IND1];
						} else {
							if (IND%H.Replication==1) {
								int IND1=R1.nextInt(TaskN.StartNode.length);
								while (TaskN.StartNode[IND1]==A.Var[IND]|TaskN.StartNode[IND1]==A.Var[IND-1]) {
									//System.out.println(99);
									IND1=R1.nextInt(TaskN.StartNode.length);							
								}
								A.Neighbor[j].Var[IND]=TaskN.StartNode[IND1];
							} else {
								int IND1=R1.nextInt(TaskN.StartNode.length);
								while (TaskN.StartNode[IND1]==A.Var[IND]|TaskN.StartNode[IND1]==A.Var[IND+1]) {
									IND1=R1.nextInt(TaskN.StartNode.length);
							}
								A.Neighbor[j].Var[IND]=TaskN.StartNode[IND1];

						}
					}
						
					}
				}
				A.Neighbor=H.EvaluatePopulation(A.Neighbor);
			
		
		return A;
		
	}*/

	HDFSBalanceIndividual GenerateNeibor2(HDFSBalanceIndividual A){
		TPPLS H=new TPPLS(1);
		A.Neighbor=new HDFSBalanceIndividual[H.NeighborSize];
		for (int j = 0; j < H.NeighborSize; j++) {
			A.Neighbor[j]=new HDFSBalanceIndividual();
			A.Neighbor[j].Var=new int[A.Var.length];
			A.Neighbor[j].Var=A.Var.clone();
			for (int j2 = 0; j2 < H.NeiborRandom*H.BlockNum; j2++) {
				Random r=new Random();
				int r1=r.nextInt(H.BlockNum);
				int[] AA=GenerateNewPipleline();
				for (int k = 0; k < H.Replication; k++) {
					A.Neighbor[j].Var[H.Replication*r1+k]=AA[k];
				}
			}
		}
		
		A.Neighbor=H.EvaluatePopulation(A.Neighbor);
		for (int i = 0; i < A.Neighbor.length; i++) {
			if (A.Neighbor[i].obj[0]<IdealPoint[0]) {
				IdealPoint[0]=A.Neighbor[i].obj[0];
			}
			if (A.Neighbor[i].obj[1]<IdealPoint[1]) {
				IdealPoint[1]=A.Neighbor[i].obj[1];
			}
		}
		return A;
		
	}
	int NumNondomination(Vector<HDFSBalanceIndividual> A){
		int Num=0;
		for (int i = 0; i < A.size(); i++) {
			int indicator=0;
			for (int j = 0; j < A.size(); j++) {
				
				if (i!=j) {
					if (NondominationRelation(A.get(j), A.get(i))==1) {
						indicator=1;
					}
					
				}
				
			}
			if (indicator==0) {
				Num=Num+1;
				
			}
			
		}
		return Num;
	}
	int[] GenerateNewPipleline(){
		TPPLS H=new TPPLS(1);
		TreeOne Tree=new TreeOne();
		int Replication=H.Replication;
		int[] PartNew=new int[Replication];
		int[] Flag=new int[Tree.RackNum];//机架存了几副本
		int[] Flag1=new int[Tree.nodes.length];//每个节点存了几个副本
		
		Random r=new Random();
		int r1=r.nextInt(Tree.RackNum);//起始点的机架位置
		r1=0;
		int r2=r.nextInt(Tree.Rack.get(r1).size());//起始点的精确位置
		r2=0;
		Flag[r1]=Flag[r1]+1;
		Flag1[Tree.Rack.get(r1).get(r2)]=Flag1[Tree.Rack.get(r1).get(r2)]+1;
		PartNew[0]=Tree.Rack.get(r1).get(r2);
		

		
		
			int r3=r.nextInt(Tree.RackNum);
			while (r3==r1) {
				r3=r.nextInt(Tree.RackNum);		
			}
			//Flag[r4]=Flag[r4]+1;
			int r4=r.nextInt(Tree.Rack.get(r3).size());
			PartNew[1]=Tree.Rack.get(r3).get(r4);
			
			int r5=r.nextInt(Tree.Rack.get(r3).size());
			while (r5==r4) {
				r5=r.nextInt(Tree.Rack.get(r3).size());	
			}
			PartNew[2]=Tree.Rack.get(r3).get(r5);
			
		if(Replication-3!=0) {
			//int[] cnodes=new int[Replication-2];
			for (int i = 3; i < Replication; i++) {
				int r6=r.nextInt(Tree.RackNum);
				int r7=r.nextInt(Tree.Rack.get(r6).size());
			
				PartNew[i]=Tree.Rack.get(r6).get(r7);
			}
		}
		
		/*int[] CN=new int[Replication-2];	//路径长度
		int[] CN1=new int[Replication-2];  //位置信息
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

