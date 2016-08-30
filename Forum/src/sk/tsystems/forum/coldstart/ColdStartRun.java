package sk.tsystems.forum.coldstart;

public class ColdStartRun {

	/**
	 * Main method for cold start. Essential to run when debug data are needed
	 * to be placed in database
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("**** COLD START script for Forum (TeAm J3DT) ****");
		long started = System.currentTimeMillis();
		try
		{
			ColdStart cold = new ColdStart();
			cold.run(System.out);
		}
		catch(Exception e)
		{
			System.err.println("Ann error occured during script execution: ");
			e.printStackTrace();
		}
		System.out.println("************************************************* total time: "+((System.currentTimeMillis()-started)/1000)+" seconds");
	}

}
