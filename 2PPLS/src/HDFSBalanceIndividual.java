//import com.sun.javafx.scene.paint.GradientUtils.Point;


public class HDFSBalanceIndividual implements Cloneable {
	int[] Var;//�Ա�������ʽ
	double[] obj;//�����Ŀ�꺯��ֵ
	HDFSBalanceIndividual[] Neighbor;//������ھ�
	float[] weight;//Ȩ������
	double ValueOfAG;//�ۺϺ���ֵ
	
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
