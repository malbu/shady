//************************************
//* Global                           *
//************************************

package shady.common.sunalg;


public class Global {
	
	private static class GlobalSingleton {
		private static Global instance = new Global();
	}
	
	public static Global getInstance() {
		return GlobalSingleton.instance;
	}

	//Member Variables
	private double Longitude,Latitude,Timezone, planeangle, zposition, projx, projy, elevation, t, tut, L, B, R, dlambda;
	private String Position;

	private Global() {
		
	}
	
	//Longitude
	
	public void setLongitude(double x)
	{
	Longitude = x;	
	}
	
	public double getLongitude()
	{
		return Longitude;
	}
	
	
	
	//Latitude
	
	public void setLatitude(double x)
	{
		Latitude = x;
	}
	
	public double getLatitude()
	{
		return Latitude;
	}
	
	
	
	//Position
	
	public void setPosition(String name)
	{
		Position = name;
	}
	
	public String getPosition()
	{
		return Position;
	}
	
	
	
	//Timezone
	
	public void setTimezone(double x)
	{
		Timezone = x;
	}
	
	public double getTimezone()
	{
		return Timezone;
	}
	
	
	
	//Window Plane Angle
	
	public void setPlaneAngle(double x)
	{
		planeangle=x;
	}
	
	public double getPlaneAngle()
	{
		return planeangle;
	}
	
	
	
	//Monitor Z Distance
	
	public void setMonitor(double x)
	{
		zposition=x;
	}
	
	public double getMonitor()
	{
		return zposition;
	}
	
	
	
	//Stow Position from Object Being Shaded
	
	public void setProjx(double x)
	{
		projx=x;
	}
	
	public double getProjx()
	{
		return projx;
	}
	
	public void setProjy(double x)
	{
		projy=x;
	}
	
	public double getProjy()
	{
		return projy;
	}
	public void setEL(double x)
	{
		elevation = x;
	}
	public double getEL()
	{
		return elevation;
	}
	public void setJulianTimeCent(double x)
	{
		t = x;
	}
	public double getJulianTimeCent()
	{
		return t;
	}
	public void setJulianTimeCentUT(double x)
	{
		tut = x;
	}
	public double getJulianTimeCentUT()
	{
		return tut;
	}
	
	public void setHelioCentricLong(double x)
	{
		L=x;
	}
	public double getHelioCentricLong()
	{
		return L;
	}
	public void setHelioCentricLat(double x)
	{
		B=x;
	}
	public double getHelioCentricLat()
	{
		return B;
	}
	public void setSunRadVector(double x)
	{
		R = x;
	}
	public double getSunRadVector()
	{
		return R;
	}
	public void setDeltaLambda(double x)
	{
		dlambda=x;
	}
	public double getDeltaLambda()
	{
		return dlambda;
	}
	
} //end Global class

