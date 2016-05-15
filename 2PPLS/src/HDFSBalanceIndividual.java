//import com.sun.javafx.scene.paint.GradientUtils.Point;


public class HDFSBalanceIndividual implements Cloneable {
	int[] Var;//自变量的形式
	double[] obj;//个体的目标函数值
	HDFSBalanceIndividual[] Neighbor;//个体的邻居
	float[] weight;//权重向量
	double ValueOfAG;//聚合函数值
	
	public Object clone()
    {
		HDFSBalanceIndividual o = null;
        try
        {
        	o = (HDFSBalanceIndividual)super.clone();
        	o.Var=Var.clone();
        	o.obj=obj.clone();
        	o.weight=weight.clone();
        	//o.Neighbor=Neighbor.clone();
        }
        catch(CloneNotSupportedException e)
        {
            System.out.println(e.toString());
        }
        return o;
    }

}
