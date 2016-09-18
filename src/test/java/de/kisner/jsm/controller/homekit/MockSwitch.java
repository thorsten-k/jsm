package de.kisner.jsm.controller.homekit;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Lightbulb;

public class MockSwitch implements Lightbulb
{
	private int id;
	private boolean powerState = false;
	private HomekitCharacteristicChangeCallback subscribeCallback = null;

	private String label;
	@Override public String getLabel() {return label;}
	
	public MockSwitch(int id, String label)
	{
		this.id=id;
		this.label = label;
	}
	
	@Override public int getId() {return id;}

	@Override public void identify() {System.out.println("Identifying light");}

	@Override public String getSerialNumber() {return "none";}

	@Override public String getModel() {return "none";}

	@Override
	public String getManufacturer() {
		return "none";
	}

	@Override
	public CompletableFuture<Boolean> getLightbulbPowerState() {return CompletableFuture.completedFuture(powerState);}

	@Override
	public CompletableFuture<Void> setLightbulbPowerState(boolean powerState) throws Exception
	{
		this.powerState = powerState;
		if (subscribeCallback != null)
		{
			subscribeCallback.changed();
		}
		System.out.println("The lightbulb is now "+(powerState ? "on" : "off"));
		return CompletableFuture.completedFuture(null);
	}

	@Override public void subscribeLightbulbPowerState(HomekitCharacteristicChangeCallback callback)
	{
		this.subscribeCallback = callback;
	}

	@Override public void unsubscribeLightbulbPowerState() {this.subscribeCallback = null;}
}