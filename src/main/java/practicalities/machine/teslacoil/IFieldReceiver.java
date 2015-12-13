package practicalities.machine.teslacoil;

public interface IFieldReceiver {
	public int getDeposit();
	public int getDraw();
	public boolean canFitRF();
	public void reciveRF();
	public boolean isRepeater();
}
