import java.util.Arrays;
import java.util.Random;
import java.util.Vector;


public class PLS {
	int IID;
	int PLSMax;
	HDFSBalanceIndividual[] PL;
	Vector<HDFSBalanceIndividual> PE;
	Vector<HDFSBalanceIndividual> PP;
	double[] IdealPoint =new double[2];//参考点
	
	PLS(HDFSBalanceIndividual[] PL1,Vector<HDFSBalanceIndividual> PE1,Vector<HDFSBalanceIndividual> PP1,int PLSMax1,double[] idealPoint2){
		int j=0;
		PLSMax=PLSMax1;
		for (int i = 0; i < IdealPoint.length; i++) {
			IdealPoint[i]=idealPoint2[i];
		}
		while (PP1.size()!=0&j++<PLSMax1) {			
			Vector<HDFSBalanceIndividual> Pa=new Vector<HDFSBalanceIndividual>();
			int ij=0;
			for (int i = 0; i < PP1.size(); i++) {
				for (int k = 0; k < PP1.get(i).Neighbor.length; k++) {					
					PL1=Updata1(PP1.get(i).Neighbor[k],PL1);
					
					if (NondominationRelation(PP1.get(i).Neighbor[k], PP1.get(i))==1) {
						IID=0;						
						PE1=Update2(PP1.get(i).Neighbor[k], PE1);						
						if (IID==1) {
							IID=0;									
							Pa=Update2(PP1.get(i).Neighbor[k], Pa);							
							if (IID==1) {
								Pa.set(Pa.size()-1, GenerateNeibor2(Pa.get(Pa.size()-1)));
								//更新参考点
								for (int k2 = 0; k2 < Pa.get(Pa.size()-1).Neighbor.length; k2++) {
									if (Pa.get(Pa.size()-1).Neighbor[k2].obj[0]<IdealPoint[0]) {
										IdealPoint[0]=Pa.get(Pa.size()-1).Neighbor[k2].obj[0];
									}
									if (Pa.get(Pa.size()-1).Neighbor[k2].obj[1]<IdealPoint[1]) {
										IdealPoint[1]=Pa.get(Pa.size()-1).Neighbor[k2].obj[1];
									}
								}
								ij=ij+1;
							}
						}
						
					}
					
				}
				
			}
			PP1=Pa;
			//System.out.println();
			//System.out.println(PP1.size());
		}
		PL=PL1;
		PE=PE1;
		PP=PP1;
		/*if (NumNondomination(PE)!=PE.size()) {
			System.out.print("PLS");
			System.exit(1);
		}*/
		
		
		
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
