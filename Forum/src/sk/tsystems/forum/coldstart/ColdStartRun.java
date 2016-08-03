package sk.tsystems.forum.coldstart;

public class ColdStartRun {

	public static void main(String[] args) {
		System.out.println("**** COLD START script for Forum (TeAm J3DT) ****");
		try
		{
			ColdStart cold = new ColdStart();
			cold.run();
		}
		catch(Exception e)
		{
			System.err.println("Ann error occured during script execution: ");
			e.printStackTrace();
		}
		System.out.println("*************************************************");
	}

}
