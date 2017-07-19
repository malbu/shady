//***************************
//* Runtime & Debug         *
//***************************
package shady.common.sunalg;

public class Sunalg {
	public CatReader reader;
	public Geometry myGeometry;
	public DateUtils myDateUtils;
	public Global myGlobal;
	public LBR myLBR;

	  public Sunalg() {
			reader = CatReader.getInstance();
			reader.openFile();
			myGeometry = Geometry.getInstance();
			myDateUtils = DateUtils.getInstance();
			myGlobal = Global.getInstance();
			myDateUtils.setSystemTime();
			myGeometry.calcTimeJulianCent();
			myGeometry.calcTimeJulianCentUT();
			myLBR = LBR.getInstance();
			myLBR.calcheliocentriclong();
			myLBR.calcheliocentriclat();
			myLBR.calcSunRadVectorComp();
			myLBR.calcDeltaLambda();
	  }

	  public double[] calcXYWindow(double[] xy) {

	    if (xy == null)
	      xy = new double[2];

	    xy[0] = myGeometry.calcXwindow();
	    xy[1] = myGeometry.calcYwindow();

	    return xy;
	  }

	  public double[] calcXYWindow() {
	    return calcXYWindow(null);
	  }

	  public void dump() {
	
	
		/*CatReader reader = CatReader.getInstance();
		reader.openFile();
		Geometry myGeometry = Geometry.getInstance();
		DateUtils myDateUtils = DateUtils.getInstance();
		Global myGlobal = Global.getInstance();
		LBR myLBR = LBR.getInstance();
		myDateUtils.setSystemTime();
		myGeometry.calcTimeJulianCent();
		myLBR.calcheliocentriclong();
		myLBR.calcheliocentriclat();
		myLBR.calcSunRadVectorComp();*/
		
		
		System.out.println("Shady\n\n");
		System.out.printf("Today's Date: %f/%f/%f\n",myDateUtils.getMonth(), myDateUtils.getDay(), myDateUtils.getYear());
		System.out.printf("The Current Time: %f:%f:%f\n", myDateUtils.getHour(), myDateUtils.getMinute(), myDateUtils.getSecond());
		System.out.printf("DST Offset: %f\n\n",myDateUtils.getDST());
		System.out.println("For Debugging Global.java and CatReader.java:\n");
		System.out.printf("Coordinates: %f Lat. %f Long. %s\n", myGlobal.getLatitude(), myGlobal.getLongitude(),myGlobal.getPosition());
		System.out.printf("Timezone: %f\n", myGlobal.getTimezone());
		System.out.printf("Monitor: %f\n", myGlobal.getMonitor());
		System.out.printf("Angle: %f\n", myGlobal.getPlaneAngle());
		System.out.printf("Projection: %f (X) %f (Y)\n", myGlobal.getProjx(), myGlobal.getProjy());
		System.out.printf("Elevation (m): %f\n\n", myGlobal.getEL());
		System.out.println("For Debugging Geometry.java:\n");
		System.out.printf("Julian Day (JDE): %.6f\n", myGeometry.calcJD());
		System.out.printf("Julian Day (JD): %.6f\n\n", myGeometry.calcJDUT());
		System.out.printf("Julian Centuries since J2000 (JDE): %.6f\n", myGlobal.getJulianTimeCent());
		System.out.printf("Julian Centuries since J2000 (JD): %.6f\n", myGlobal.getJulianTimeCentUT());
		System.out.printf("Julian Centuries since J2000 (from lbr JDE): %.6f\n\n", myLBR.getj());
		System.out.printf("Heliocentric Longitude (deg): %.12f\n", myGlobal.getHelioCentricLong());
		System.out.printf("Heliocentric Latitude (deg): %.6f\n", myGlobal.getHelioCentricLat());
		System.out.printf("Solar Radius (AU): %.6f\n",myGlobal.getSunRadVector());
		System.out.printf("Daily Geocentric Long. Variation (deg): %.6f\n",myGlobal.getDeltaLambda());
		System.out.printf("Aberration Correction (deg): %.6f\n",myGeometry.calcAberrCorr());
		System.out.printf("True Solar Longitude (deg): %.6f\n", myGeometry.calcSunTrueLong());
		System.out.printf("True Solar Latitude (deg): %.6f\n", myGeometry.calcSunTrueLat());
		System.out.printf("Nutation Correction (deg): %.6f\n", myGeometry.calcNutCorrection());
		System.out.printf("Nutation Correction (UT) (deg): %.6f\n", myGeometry.calcNutCorrectionUT());
		System.out.printf("Mean Ecliptic Obliquity (deg): %.6f\n", myGeometry.calcMeanObliquityOfEcliptic());
		System.out.printf("Mean Ecliptic Obliquity (UT) (deg): %.6f\n", myGeometry.calcMeanObliquityOfEclipticUT());
		System.out.printf("Obliquity Correction (deg): %.6f\n", myGeometry.calcObliquityCorrection());
		System.out.printf("Obliquity Correction (UT) (deg): %.6f\n", myGeometry.calcObliquityCorrectionUT());
		System.out.printf("Apparent Solar Longitude (deg): %.6f\n\n", myGeometry.calcSunApparentLong());
		System.out.println("Here Come the Results:\n");
		System.out.printf("Sun's Right Ascension (deg): %.6f\n", myGeometry.calcSunRtAscension());
		System.out.printf("Sun's Declination (deg): %.6f\n\n", myGeometry.calcSunDeclination());
		System.out.printf("Local Sidereal Time (deg): %.6f\n\n", myGeometry.calclocalstime());
		System.out.printf("Precession Corrected RA (deg): %.6f\n", myGeometry.calcSunRtPrec());
		System.out.printf("Precession Corrected DEC (deg): %.6f\n", myGeometry.calcSunDecPrec());
		System.out.printf("Parallax Corrected RA (deg): %.6f\n", myGeometry.calcSunRtParallax());
		System.out.printf("Parallax Corrected DEC (deg): %.6f\n\n", myGeometry.calcSunDecParallax());
		System.out.println("Hour Angles:\n");
		System.out.printf("Hour Angle (deg): %.6f\n", myGeometry.calcLHA());
		System.out.printf("Precession Corrected Hour Angle (deg): %.6f\n", myGeometry.calcLHAC());
		System.out.printf("Precession & Parallax Corrected Hour Angle (deg): %.6f\n\n", myGeometry.calcLHACC());
		System.out.println("Horizon Conversion\n\n");
		System.out.printf("Azimuth (deg): %.6f\n", myGeometry.calcAzimuth());
		System.out.printf("Altitude (deg): %.6f\n", myGeometry.calcAlt());
		System.out.printf("Refraction Corrected Altitude (deg): %.6f\n\n", myGeometry.calccorrAlt());
		System.out.printf("Shady's X (m): %.6f\n", myGeometry.calcXwindow());
		System.out.printf("Shady's Y (m): %.6f\n\n", myGeometry.calcYwindow());
		System.out.println("End");
		
		}
	public static void main(String args[])

 	{	
    (new Sunalg()).dump();
  }
}

