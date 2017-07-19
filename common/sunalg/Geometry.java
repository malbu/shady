//***********************************************************************
//***********************************************************************
//*					Geometry.java										*
//*          Position/Correction Algorithms                             *
//*                                                                     *
//*                                                                     *
//* Epoch is J2000.0 precessed to execution date                        *
//***********************************************************************
//***********************************************************************
package shady.common.sunalg;


public class Geometry {
	private static class GeometrySingleton {
		private static Geometry instance = new Geometry();
	}
	
	public static Geometry getInstance() {
		return GeometrySingleton.instance;
	}
	private Geometry() {
		// do nothing
	}
	
	
	
	private double day, year, month, hour, minute, second, jd, jdut, t, tut, L, B, R, O, FKO, beta, psi, psiut, e0, e0UT, e, eUT, lambda,
				   alpha, theta, Stime, alpha2, theta2, alpha3, theta3, azimuth, altitude, refalt, Latitude, Longitude, 
				   Timezone, Hangle, Hangle2, Hangle3, elevation, dst, dlambda, aberrcorr;
	
	
	//Importing System Time from DateUtils
	DateUtils myDateUtils = DateUtils.getInstance();
	Global myGlobal = Global.getInstance();
	

	
	
	        
	//***********************************************************************
	//* Name:    calcJD (TD)                                                *                              
	//* Purpose: Convert Julian Day Ephemeris from calendar day and time    *                                                                  
	//* Output:                                                             *                       
	//*   The Julian day (jd) corresponding to the date (JDE)               *             
	//***********************************************************************
	
	public double calcJD()
	{
		
			year = myDateUtils.getYear();
			month = myDateUtils.getMonth();
			day = myDateUtils.getDay();
			hour = myDateUtils.getHour();
			minute = myDateUtils.getMinute();
			second = myDateUtils.getSecond();
			Timezone = myGlobal.getTimezone();
			dst = myDateUtils.getDST();
			double yeardiff = (year-2000); //centuries measurement for ephemeris time conversion
			double deltaT = 62.92+0.32217*yeardiff+0.005589*Math.pow(yeardiff,2); //additive correction for UT :: JDE Correction
			double jday = day+(hour-(Timezone+dst)+minute/60.0+second/3600.0)/24.0;
			double Jyear = year;
			double Jmonth = month;
			
	    	if (month <= 2) 
	    	{
	    		Jyear = Jyear-1;
	    	    Jmonth = Jmonth+12;
	    	}
	    	
	    	double A = Math.floor(Jyear/100.0);
	    	double B = 2 - A + Math.floor(A/4.0);
	    	jd = Math.floor(365.25*(Jyear + 4716.0)) + Math.floor(30.6001*(Jmonth+1)) + jday + B - 1524.5+deltaT/86400;
	    	return jd;
	    	
	 }
	
	//**********************************************************************
	//* Name:    calcJDUT  (UT)                                            *                                   
	//* Purpose: Convert Julian Day from calendar day and time             *                                                         
	//* Output:                                                            *                        
	//*   The Julian day (jd) corresponding to the date (JD)               *              
	//**********************************************************************
	
	public double calcJDUT()
	{
		
			year = myDateUtils.getYear();
			month = myDateUtils.getMonth();
			day = myDateUtils.getDay();
			hour = myDateUtils.getHour();
			minute = myDateUtils.getMinute();
			second = myDateUtils.getSecond();
			Timezone = myGlobal.getTimezone();
			dst = myDateUtils.getDST(); 
			double jdayut = day+(hour-(Timezone+dst)+minute/60.0+second/3600.0)/24.0;
			double Jyearut = year;
			double Jmonthut = month;
	    	if (month <= 2) 
	    	{
	    		Jyearut = Jyearut-1;
	    	    Jmonthut = Jmonthut+12;
	    	}
	    	double Aut = Math.floor(Jyearut/100.0);
	    	double But = 2 - Aut + Math.floor(Aut/4.0);
	    	jdut = Math.floor(365.25*(Jyearut + 4716.0)) + Math.floor(30.6001*(Jmonthut+1)) + jdayut + But - 1524.5;
	    	return jdut;
	    	
	 }
	
	


	 //*************************************************************************
	 //* Name:    calcTimeJulianCent (->JDE)                                   *                        
	 //* Purpose: Convert Julian Day Ephemeris to centuries since J2000.0.     *                                                            
	 //* Output: the value (t) corresponding to the Julian Day Ephemeris       *                              
	 //*************************************************************************
	
	 public void calcTimeJulianCent()
	 {
		 	
		 	 jd = calcJD();
	    	 double sett = (jd - 2451545.0)/36525.0;
	    	 myGlobal.setJulianTimeCent(sett);
	    	 
	 }
	 
	//*********************************************************************
	 //* Name:    calcTimeJulianCentUT (->JD)                            *                                     
	 //* Purpose: Convert Julian Day to centuries since J2000.0.          *                                                       
	 //* Output: the value (tut) corresponding to the Julian Day          *                           
	 //********************************************************************
	 
	 public void calcTimeJulianCentUT()
	 {
		 	
		 	 jdut = calcJDUT();
	    	 double settut = (jdut - 2451545.0)/36525.0;
	    	 myGlobal.setJulianTimeCentUT(settut);
	    	 
	 }
	 
	

	//**********************************************************************
	//* Name:    calcSunTrueLong (->JDE)                                   *                                        
	//* Purpose: Calculate the geocentric longitude of the sun             *               
	//* Output: Sun's true longitude (O) in degrees                        *
	//* Error:  <=.01" (see LBR)                                           *
	//**********************************************************************
	 
	public double calcSunTrueLong()
	{
		
	         L = myGlobal.getHelioCentricLong();
	         O = L + 180.0;
	         return O;   // in degrees
	         
	}


	//***********************************************************************
	//* Name:    calcCorrSunTrueLong (->JDE)                                *                                         
	//* Purpose: Converts sun's geocentric longitude to fk5 system          *               
	//* Output: Sun's corrected geocentric longitude (FKO) in degrees       *                                          
	//***********************************************************************
	
	public double calcCorrSunTrueLong()
	{
		
			 t = myGlobal.getJulianTimeCent();
	    	 O = calcSunTrueLong();
	    	 FKO = O+(-0.09033/3600.0);
	    	 return FKO; //in degrees
	    	 
	}


	//*************************************************************************************
	//* Name:    calcSunTrueLat (->JDE)                                                   *        
	//* Purpose: Calculate the geocentric latitude of the sun and converts to fk5 system  *                                                  
	//* Output: Sun's geocentric latitude (beta) in degrees                               *
	//* Error: <=.01" (see LBR)                                                           *
	//*************************************************************************************
	
	public double calcSunTrueLat()
	{
		
			t = myGlobal.getJulianTimeCent();
			O = calcSunTrueLong();
			B= myGlobal.getHelioCentricLat();
	        double fk = O-1.397*t-0.00031*Math.pow(t,2);
	        double deltaB=(0.03916/3600.0)*(Math.cos(Math.toRadians(fk)-Math.sin(Math.toRadians(fk)))); //correction to Latitude
	        beta=-B+deltaB;
	        return beta;  			//in degrees
	        
	}
	//***************************************************************
	//* Name:    calcNutCorrection (->JDE)                          *                                       
	//* Purpose: Calculate the nutation correction for longitude    *      
	//* Output: Corrected nutation (psi) in degrees                 * 
	//* Error: <=.5"                                                *
	//***************************************************************
	        
	public double calcNutCorrection()
	{
		
			t = myGlobal.getJulianTimeCent();
	        double Lcorr=280.4665+36000.7698*t; 
	        double L1corr=218.3165+481267.8813*t;
	        double omega = 125.04452 - 1934.136261*t + .0020708*Math.pow(t,2)+Math.pow(t,3)/450000.0;
	        psi = (-17.2/3600.0)*Math.sin(Math.toRadians(omega))-(1.32/3600.0)*Math.sin(Math.toRadians(2*Lcorr))-(0.23/3600.0)*Math.sin(Math.toRadians(2*L1corr))+(0.21/3600.0)*Math.sin(Math.toRadians(2*omega));
	        return psi;             //in degrees

	}
	
	 //*****************************************************************
	 //* Name:    calcNutCorrectionUT (->JD)                           *                                         
	 //* Purpose: Calculate the nutation correction for longitude (UT) *       
	 //* Output: Corrected nutation (psiut) in degrees                 * 
	 //* Error: <=.5"                                                  *
	 //*****************************************************************
	    	        
	 public double calcNutCorrectionUT()
	 {
		 
		 	  tut = myGlobal.getJulianTimeCentUT();
	    	  double Lcorrut=280.4665+36000.7698*tut; 
	    	  double L1corrut=218.3165+481267.8813*tut;
	    	  double omegaut = 125.04452 - 1934.136261*tut + .0020708*Math.pow(tut,2)+Math.pow(tut,3)/450000.0;
	    	  psiut = (-17.2/3600.0)*Math.sin(Math.toRadians(omegaut))-(1.32/3600.0)*Math.sin(Math.toRadians(2*Lcorrut))-(0.23/3600.0)*Math.sin(Math.toRadians(2*L1corrut))+(0.21/3600.0)*Math.sin(Math.toRadians(2*omegaut));
	    	  return psiut;             //in degrees

	 }
	 
	//****************************************************************
	//* Name:    calcMeanObliquityOfEcliptic (->JDE)                 *                                                    
	//* Purpose: Calculate the mean obliquity of the ecliptic        *                                           
	//* Output: Mean obliquity (e0) in degrees                       *
	//* Error: <=.01" for 1000<=year<=3000                           *
	//****************************************************************

	public double calcMeanObliquityOfEcliptic()
	{
		
	    	 t = myGlobal.getJulianTimeCent();
	         double seconds = (21.448 - 4680.93*(t/100)-1.55*Math.pow((t/100),2)+1999.25*Math.pow((t/100),3)-51.38*Math.pow((t/100),4)-249.67*Math.pow((t/100),5)-39.05*Math.pow((t/100),6)+7.12*Math.pow((t/100),7)+27.87*Math.pow((t/100),8)+5.79*Math.pow((t/100),9)+2.45*Math.pow((t/100),10));
	         e0 = 23.0 + 26.0/60.0 + seconds/3600.0;
	         return e0;              // in degrees
	         
	}
	
	//****************************************************************
	//* Name:    calcMeanObliquityOfEclipticUT (->JD)                *                                                    
	//* Purpose: Calculate the mean obliquity of the ecliptic (UT)   *                                           
	//* Output: Mean obliquity (e0UT) in degrees                     *
	//* Error: <=.01" for 1000<=year<=3000                           *
	//****************************************************************

	public double calcMeanObliquityOfEclipticUT()
	{
		
	    	 tut = myGlobal.getJulianTimeCentUT();
	         double secondsUT = (21.448 - 4680.93*(tut/100)-1.55*Math.pow((tut/100),2)+1999.25*Math.pow((tut/100),3)-51.38*Math.pow((tut/100),4)-249.67*Math.pow((tut/100),5)-39.05*Math.pow((tut/100),6)+7.12*Math.pow((tut/100),7)+27.87*Math.pow((tut/100),8)+5.79*Math.pow((tut/100),9)+2.45*Math.pow((tut/100),10));
	         e0UT = 23.0 + 26.0/60.0 + secondsUT/3600.0;
	         return e0UT;              // in degrees
	         
	}
	
	//************************************************************************
	//* Name:    calcObliquityCorrection (->JDE)                             *                          
	//* Purpose: Calculate the nutation corrected obliquity of the ecliptic  *                                
	//* Output: Corrected obliquity (e) in degrees                           *
	//* Error: <=.1"                                                         *
	//************************************************************************

	public double calcObliquityCorrection()
	{
		
	         t = myGlobal.getJulianTimeCent();
	         e0 = calcMeanObliquityOfEcliptic();
	         double L2corr=280.4665+36000.7698*t; 
	         double L3corr=218.3165+481267.8813*t;
	         double omega = 125.04452 - 1934.136261*t+.0020708*Math.pow(t,2)+Math.pow(t,3)/450000.0;
	         e = e0 + ((9.2/3600.0) * Math.cos(Math.toRadians(omega))+(0.57/3600.0)*Math.cos(Math.toRadians(2*L2corr))+(0.1/3600.0)*Math.cos(Math.toRadians(2*L3corr))-(0.09/3600.0)*Math.cos(Math.toRadians(2*omega)));
	         return e;               // in degrees
	         
	}
	
	//************************************************************************
	//* Name:    calcObliquityCorrectionUT (->JD)                            *                          
	//* Purpose: Calculate the nutation corrected obliquity of the ecliptic  *                                
	//* Return value: Corrected obliquity (eUT) in degrees                   *
	//* Error: <=.1"                                                         *
	//************************************************************************

	public double calcObliquityCorrectionUT()
	{
		
	         tut = myGlobal.getJulianTimeCentUT();
	         e0UT = calcMeanObliquityOfEclipticUT();
	         double L2corrUT=280.4665+36000.7698*tut; 
	         double L3corrUT=218.3165+481267.8813*tut;
	         double omegaUT = 125.04452 - 1934.136261*tut+.0020708*Math.pow(tut,2)+Math.pow(tut,3)/450000.0;
	         eUT = e0UT + ((9.2/3600.0) * Math.cos(Math.toRadians(omegaUT))+(0.57/3600.0)*Math.cos(Math.toRadians(2*L2corrUT))+(0.1/3600.0)*Math.cos(Math.toRadians(2*L3corrUT))-(0.09/3600.0)*Math.cos(Math.toRadians(2*omegaUT)));
	         return eUT;               // in degrees
	         
	}
	
	//*************************************************************
	//* Name:    calcAberrCorr (->JDE)                            *         
	//* Purpose: Corrects for Aberration                          *
	//* Output: Aberration correction (aberrcorr) in degrees      *																	  
	//* Error: <= .001"                                           *
	//*************************************************************
	public double calcAberrCorr()
	{
		
		R=myGlobal.getSunRadVector();
		dlambda = myGlobal.getDeltaLambda();
		aberrcorr = -.005775518*R*dlambda;
		return aberrcorr;
		
	}
	

	//*************************************************************************************
	//* Name:    calcSunApparentLong (->JDE)                                              *         
	//* Purpose: Calculate the apparent longitude of the sun                              *                                        
	//* Output: Sun's apparent longitude (lambda) in degrees                              *
	//* Error: <=.01"																	  *                                        
	//*************************************************************************************

	public double calcSunApparentLong()
	{
		
		     t = myGlobal.getJulianTimeCent();
	         R = myGlobal.getSunRadVector();
		     FKO = calcCorrSunTrueLong();
		     psi= calcNutCorrection();
		     aberrcorr= calcAberrCorr();
		     lambda=FKO+psi+aberrcorr;  //aberration correction
		     return lambda;          // in degrees
		     
	}
		
	//*************************************************************
	//* Name:    calcSunRtAscension                               *                                         
	//* Purpose: Calculate the right ascension of the sun         *                 
	//* Output: Sun's right ascension (alpha) in degrees          *                                     
	//*************************************************************

	public double calcSunRtAscension()
	{
		
	         e = calcObliquityCorrection();
	         lambda = calcSunApparentLong();
	         beta = calcSunTrueLat();
	         double tananum = ((Math.sin(Math.toRadians(lambda))*Math.cos(Math.toRadians(e)))-(Math.tan(Math.toRadians(beta))*Math.sin(Math.toRadians(e))));
	         double tanadenom = (Math.cos(Math.toRadians(lambda)));
	         alpha = Math.toDegrees(Math.atan2(tananum,tanadenom));
	         
	         if (alpha<0.0)
	         {
	        	 alpha=alpha+360.0; 
	         }
	         
	         return alpha;  // in degrees
	         
	}

	//********************************************************
	//* Name:    calcSunDeclination                          *                                                    
	//* Purpose: Calculate the declination of the sun        *                                                     
	//* Output: Sun's declination (theta) in degrees         *                                                   
	//********************************************************

	public double calcSunDeclination()
	{
		
	         e = calcObliquityCorrection();
	         lambda = calcSunApparentLong();
	         beta = calcSunTrueLat();
	         double sint = Math.sin(Math.toRadians(beta))*Math.cos(Math.toRadians(e))+Math.cos(Math.toRadians(beta))*Math.sin(Math.toRadians(e))*Math.sin(Math.toRadians(lambda));
	         theta = Math.toDegrees(Math.asin(sint));
	         return theta;           // in degrees
	         
	 }
	       
	 //**********************************************************************
	 //* Name:    calclocalstime  (->JD)                                    *                          
	 //* Purpose: Calculates Local Sidereal Time (based on Longitude)       *                                                      
	 //* Output: Local sidereal time (Stime) in degrees                     *                
	 //**********************************************************************

	 public double calclocalstime()
	 {
	   			
		     jdut = calcJDUT();
		     tut =myGlobal.getJulianTimeCentUT();
	   		 year = myDateUtils.getYear();
	   	     Longitude = myGlobal.getLongitude();
	   	     double meansid = 280.46061837+360.98564736629*(jdut-2451545.0)+0.000387933*Math.pow(tut,2)-((Math.pow(tut,3)/38710000.0));
	   	     eUT = calcObliquityCorrectionUT();
	   	     psiut = calcNutCorrectionUT();
	   		 double stimecorr = psiut*Math.cos(Math.toRadians(eUT));
	   		 Stime = meansid+stimecorr-Longitude;
	   		 
	   		 while(Stime > 360.0)
	   		 {
	   			 Stime=Stime-360.0;	
	   	     }
	   		 
	   		 while(Stime<0.0)
	   		 {
	   			 Stime=Stime+360.0;
	   		 }
	   		 
	   		 return Stime;  //in degrees
	   		 
	}
	    		
	//*************************************************************
	//* Name:    calcLHA                                          *                               
	//* Purpose: Calculates Local Hour angle                      *                                        
	//* Output: Local Hour angle (Hangle) in degrees              *                       
	//*************************************************************
	 
	public double calcLHA()
	{
		
		   Stime = calclocalstime();
		   alpha = calcSunRtAscension();
		   Hangle = Stime-alpha; 
			while (Hangle <0.0)
			{
				Hangle = Hangle+360.0;
			}
			while (Hangle>360.0)
			{
				Hangle = Hangle-360.0;
			} 
		    return Hangle;			//in degrees
		    
	 }
	   
	 //***********************************************************************
	 //* Name:    calcSunRtPrecession (->JDE)                                *                            
	 //* Purpose: Correction for precession in right ascension               *                                         
	 //* Output: Sun's corrected right ascension (alpha2) in degrees         *
	 //***********************************************************************	
	
	 public double calcSunRtPrec()
     {
		 
			 t = myGlobal.getJulianTimeCent();
			 theta = calcSunDeclination();
			 alpha = calcSunRtAscension();
			 double xi = (2306.2181/3600.0)*t+(0.30188/3600.0)*Math.pow(t,2)+ (0.017998/3600.0)*Math.pow(t,3);
			 double zed = (2306.2181/3600.0)*t+(1.09468/3600.0)*Math.pow(t,2)+(0.018203/3600.0)*Math.pow(t,3);
			 double thee = (2004.3109/3600.0)*t-(0.42655/3600.0)*Math.pow(t,2)-(0.041833/3600.0)*Math.pow(t,3);
			 double A0 = Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(alpha+xi));
			 double B00 = Math.cos(Math.toRadians(thee))*Math.cos(Math.toRadians(theta))*Math.cos(Math.toRadians(alpha+xi))-Math.sin(Math.toRadians(thee))*Math.sin(Math.toRadians(theta));
			 double alp3 = Math.toDegrees(Math.atan2(A0,B00));
			 
			 if (alp3<0.0)
			 {
				 double alp4= alp3+360.0;
				 alpha2 = alp4 + zed;
			 }
			 
			 else if (alp3>360.0)
		     {
				 double alp5 = alp3-360.0;
			 	alpha2 = alp5+zed;
			 }
			 else 
			 {
				alpha2 = alp3+zed;
			 }
				
				return alpha2;		//in degrees
				
	 }
		
		//***********************************************************************
		//* Name:    calcSunDecPrec (->JDE)                                     *                      
		//* Purpose: Correction for precession in declination                   *           
		//* Output: Sun's corrected declination (theta3) in degrees             *  
		//***********************************************************************	
			
	    public double calcSunDecPrec()
	    
		{
				t = myGlobal.getJulianTimeCent();
				theta = calcSunDeclination();
				alpha = calcSunRtAscension();
				double xi1 = (2306.2181/3600.0)*t+(0.30188/3600.0)*Math.pow(t,2)+ (0.017998/3600.0)*Math.pow(t,3);
				double thee1 = (2004.3109/3600.0)*t-(0.42655/3600.0)*Math.pow(t,2)-(0.041833/3600.0)*Math.pow(t,3);
				double C0 = Math.sin(Math.toRadians(thee1))*Math.cos(Math.toRadians(theta))*Math.cos(Math.toRadians(alpha+xi1))+Math.cos(Math.toRadians(thee1))*Math.sin(Math.toRadians(theta));
				theta2 = Math.toDegrees(Math.asin(C0));
				return theta2;		//in degrees
		}
		
		//**********************************************************************
		//* Name:    calcLHAC                                                  *                 
		//* Purpose: Calculates Precession Corrected Local Hour angle          *                                                    
		//* Output: Corrected Local Hour angle (Hangle2) in degrees            *                         
		//**********************************************************************
	    
	    public double calcLHAC()
	    {
	    	
				Stime = calclocalstime();
				alpha2 = calcSunRtPrec();
				Hangle2 = Stime-alpha2; 
				while (Hangle2 <0.0)
				{
				Hangle2 = Hangle2+360.0;
				}
				while (Hangle2>360.0)
				{
				Hangle2 = Hangle2-360.0;
				} 
				return Hangle2;		//in degrees
				
		}
	    
	//***********************************************************************
	//* Name:    calcSunRtParallax                                          *                     
	//* Purpose: Correction for parallax in right ascension                 *                                    
	//* Output: Sun's corrected right ascension (alpha3) in degrees         *                                       
	//***********************************************************************
	    
    public double calcSunRtParallax()
    {
    	
			Stime = calclocalstime();
			alpha2 = calcSunRtPrec();
			R = myGlobal.getSunRadVector();
			theta2 = calcSunDecPrec();
			Latitude = myGlobal.getLatitude();
			elevation = myGlobal.getEL();
			Hangle2 = calcLHAC();
			double parallax = Math.sin(Math.toRadians(8.794/3600.0))/R;
			double rtu = Math.toDegrees(Math.atan((6356.755/6378.14)*Math.tan(Math.toRadians(Latitude)))) ; //North vs. Equatorial Radius Correction
			double pcoslat = Math.cos(Math.toRadians(rtu))+(elevation/6378140.0)*Math.cos(Math.toRadians(Latitude));
			double tanacn = -(pcoslat*parallax*Math.sin(Math.toRadians(Hangle2)));
			double tanadn = Math.cos(Math.toRadians(theta2))-pcoslat*parallax*Math.cos(Math.toRadians(Hangle2));
			double ac = Math.toDegrees(Math.atan2(tanacn,tanadn));
			alpha3=alpha2+ac;
			return alpha3;		//in degrees
			
	}

	//***********************************************************************
	//* Name:    calcSunDecParallax                                         *                      
	//* Purpose: correction for parallax in declination                     *                    
	//* Output:                                                             *                        
	//*   sun's corrected declination (theta3) in degrees                   *                                   
	//***********************************************************************
    
	public double calcSunDecParallax()
	{
		
			Stime = calclocalstime();
			alpha2 = calcSunRtAscension();
			alpha3 = calcSunRtParallax();
	        R = myGlobal.getSunRadVector();
	        theta2 = calcSunDecPrec();
	        Latitude = myGlobal.getLatitude();
	        elevation = myGlobal.getEL();
	        Hangle2 = calcLHAC();
	        double parallax = Math.sin(Math.toRadians(8.794/3600.0))/R;
	        double dtu = Math.toDegrees(Math.atan((6356.755/6378.14)*Math.tan(Math.toRadians(Latitude)))) ; //North vs. Equatorial Radius Correction
	        double pcoslat1 = Math.cos(Math.toRadians(dtu))+(elevation/6378140.0)*Math.cos(Math.toRadians(Latitude));
	        double psinlat = (6356.755/6378.14)*Math.sin(Math.toRadians(dtu))+(elevation/6378140.0)*Math.sin(Math.toRadians(Latitude));
	        double tana2cn = (Math.sin(Math.toRadians(theta2))-psinlat*parallax)*(Math.cos(Math.toRadians(alpha3-alpha2)));
	        double tana2dn = Math.cos(Math.toRadians(theta2))-pcoslat1*parallax*Math.cos(Math.toRadians(Hangle2));
	        theta3 = Math.toDegrees(Math.atan2(tana2cn,tana2dn)); 
			return theta3;		//in degrees
			
	}
	
	//*************************************************************************
	//* Name:    calcLHACC                                                    *           
	//* Purpose: Calculates Precession & Parallax Corrected Local Hour angle  *                                                            
	//* Output:                                                               *                      
	//*   CorrectedLocal Hour angle (Hangle3) in degrees                      *         
	//*************************************************************************
	
	public double calcLHACC()
	{
		
			Stime = calclocalstime();
			alpha3 = calcSunRtParallax();
			Hangle3 = Stime-alpha3;
			
			while (Hangle3 <0.0)
			{
				Hangle3 = Hangle3+360.0;
			}
			
			while (Hangle3>360.0)
			{
				Hangle3 = Hangle3-360.0;
			}
			
				return Hangle3;		//in degrees
				
	}

	//***********************************************************************
	//* Name:    calcAzimuth                                                *                                                     
	//* Purpose: Calculates the Azimuth from RA/Dec                         *          
	//* Output: Azimuth from North in degrees                               *                  
	//***********************************************************************
		
	public double calcAzimuth()
	{		
		
			Stime = calclocalstime();
			alpha3 = calcSunRtParallax();
			theta3 = calcSunDecParallax();
			Latitude = myGlobal.getLatitude();
			Hangle3 = calcLHACC(); 
			double tanAn = Math.sin(Math.toRadians(Hangle3)); 
			double tanAdn = Math.cos(Math.toRadians(Hangle3))*Math.sin(Math.toRadians(Latitude))-Math.tan(Math.toRadians(theta3))*Math.cos(Math.toRadians(Latitude));
			azimuth = Math.toDegrees(Math.atan2(tanAn,tanAdn));
			azimuth = azimuth+180.0;    //switching to 0-360 range
			while (azimuth<0.0)                 //atan2 correction for negative angles
			{
				azimuth = 360.0+azimuth;
			}
			
			while (azimuth > 360.0)
			{
			azimuth = azimuth-360.0;
			}
			
			
			return azimuth; //in degrees
			
	}

	//***********************************************************************
	//* Name:    calcAlt                                                    *                                                      
	//* Purpose: Calculates the Altitude from RA/Dec                        *                
	//* Output: Altitude in degrees                                         *        
	//***********************************************************************
	
	public double calcAlt()
	{
		
			alpha3 = calcSunRtParallax();
	        theta3 = calcSunDecParallax();
	        Latitude = myGlobal.getLatitude();
	        Hangle3 = calcLHACC();
			double sinalt = Math.sin(Math.toRadians(Latitude))*Math.sin(Math.toRadians(theta3))+Math.cos(Math.toRadians(Latitude))*Math.cos(Math.toRadians(theta3))*Math.cos(Math.toRadians(Hangle3)); 
			double truealt = Math.toDegrees(Math.asin(sinalt));
			altitude = truealt;
			return altitude;

	}
		
	//***********************************************************************
	//* Name:    calcCorrAlt                                                *              
	//* Purpose: Calculates the refraction corrected Altitude               *                         
	//* Output: Corrected Altitude in degrees                               *                  
	//***********************************************************************
	
	public double calccorrAlt()
	{
		
		    altitude = calcAlt();
			double refractioncorr = 1.02/(Math.tan(Math.toRadians(altitude+10.3/(altitude+5.11))))+0.0019279;
			refalt = altitude+(refractioncorr/60.0);
			return refalt;
			
	}

	//*********************************************************************************
	//* Name:    calcXwindow                                                          *    
	//* Purpose: Calculates the X Component of the Window Plane from Alt/Az           *                            
	//* Output: X value (realx) from with relation to robots starting position        *                                         
	//*********************************************************************************
	
	private double zposition, planeangle,realx, projx;
	public double calcXwindow()
	{
		
			azimuth=calcAzimuth();
			zposition = myGlobal.getMonitor();
			planeangle = myGlobal.getPlaneAngle();
			projx= myGlobal.getProjx();
			
			if (azimuth>planeangle && azimuth<(planeangle+90.0))				//triangle inequality
			{
				double mposit1 =zposition*(Math.tan(Math.toRadians(azimuth-planeangle))); //zpositionz is the "z-axis" distance of the object being shaded from the Window plane
				realx=projx+mposit1;												      //mposition on the x axis of Window plane
			}                                                                             //projx is the distance along the Window plane from the robots starting position
			
			else if (azimuth<planeangle && (planeangle-azimuth)<90.0)
			{
				double mposit2 =zposition*(Math.tan(Math.toRadians(planeangle-azimuth)));
				realx=projx-mposit2;
			}
			
			else if (azimuth == planeangle)
			{
				realx = projx;
			}
			
			else
			{
				realx = -99.0; //projection error
			}
											     
			return realx;
			
	}

	//************************************************************************
	//* Name:    calcYwindow                                                 *                                                           
	//* Purpose: Calculates the Y Component of the Window Plane from Alt/Az  *                                    
	//* Output: Y value from with relation to robots starting position       *                                          
	//************************************************************************

	private double projy, realy;
	public double calcYwindow()
	{
		
			projy = myGlobal.getProjy();
			//altitude=calcAlt();
			refalt=calccorrAlt();
			zposition = myGlobal.getMonitor();
			
			double eleangle = Math.toDegrees(Math.atan(projy/zposition));
			
			if (refalt<90.0 && refalt>0.0 && refalt<eleangle) //unlikely that it will hit 90 or 0
			{
				double mposit3=zposition*Math.tan(Math.toRadians(refalt)); //mposition1 is on the y axis of Window plane
				realy=-(projy-mposit3);
			}
			
			else if (refalt<90.0 && refalt>0.0 && refalt>eleangle)
			{
				double mposit4=zposition*Math.tan(Math.toRadians(refalt));
				realy=mposit4-projy;
			}
			else if (refalt == 0.0)
			{
				realy = projy;
			}
			
			else if (refalt<0.0)
			{
				realy= -99.0;
			}
			
			return realy;
			
	}
	

} //end class Geometry





