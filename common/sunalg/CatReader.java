//**************************
//* Catalog Reader         *
//**************************

package shady.common.sunalg;
import java.io.*;
import java.util.*;



public class CatReader {
	
	private static class CatReaderSingleton {
		private static CatReader instance = new CatReader();
	}
	
	public static CatReader getInstance() {
		return CatReaderSingleton.instance;
	}
	
	private CatReader() {
		
	}

	Global myGlobal = Global.getInstance(); //setting up global object
	 
	public void openFile()
	{
	    try
	    {
	    	//BufferedReader in = new BufferedReader(new FileReader(new File("./Shady.txt")));
	    	BufferedReader in =
	            new BufferedReader(
	              new InputStreamReader(
	                CatReader.class.getResourceAsStream("Shady.txt")));
	    	int i;
	    	String str,
	    	str1,
	    	str2;
	    	StringTokenizer parser;
	    		while ((str1 = in.readLine()) != null)
	    		{
	    			if (!str1.startsWith("*"))
	    			{
	    				i = 0;
	    				str = "";
	    				while (i < str1.length() && str1.charAt(i) != '/')
	    				{
	    					str += str1.charAt(i);
	    					i++;
	    				}
	 if (str.indexOf("COORDINATES") != -1)
          try
      	  {
        	  parser = new StringTokenizer(str);
        	  str2 = parser.nextToken();
        	  str2 = parser.nextToken();
        	  //setting Latitude
        	  myGlobal.setLatitude(Double.valueOf(str2).doubleValue());
        	  str2 = parser.nextToken();
        	  //Setting Longitude
        	  myGlobal.setLongitude(Double.valueOf(str2).doubleValue());
        	  str2 = parser.nextToken();
        	  //setting Position
        	  myGlobal.setPosition(str2);
      	   }
      	  catch(Exception e)
      	  {
      		  System.err.println("YOU FOOL: Coordinate Error");
      		  System.exit(1);
      	  }
     
    //Timezone

     if(str.indexOf("TIMEZONE") != -1)
    	 try
     	 {
    		parser = new StringTokenizer(str);
    		str2 = parser.nextToken();
    		str2 = parser.nextToken();
    		//setting Timezone
    		myGlobal.setTimezone(Double.valueOf(str2).doubleValue());
    	 }
     	 catch(Exception e)
     	 {
     		System.err.println("YOU FOOL: Timezone Error");
    		System.exit(1);
     	 }
     
     //Monitor
     	if(str.indexOf("MONITOR") != -1)
       	 try
         {
       		parser = new StringTokenizer(str);
       		str2 = parser.nextToken();
       		str2 = parser.nextToken();
       		//setting zposition (m)
       		myGlobal.setMonitor(Double.valueOf(str2).doubleValue());
         }
         catch(Exception e)
         {
        	System.err.println("YOU FOOL: Monitor Error");
        	System.exit(1);
         }
       
       //Angle
      	if(str.indexOf("ANGLE") != -1)
         try
         {
        	parser = new StringTokenizer(str);
        	str2 = parser.nextToken();
        	str2 = parser.nextToken();
        	//setting planeangle (deg)
        	myGlobal.setPlaneAngle(Double.valueOf(str2).doubleValue());
          }
          catch(Exception e)
          {
         	System.err.println("YOU FOOL: Angle Error");
         	System.exit(1);
          }
          
         //Projection 
          if (str.indexOf("PROJECTION") != -1)
           try
           {
        	   parser = new StringTokenizer(str);
        	   str2 = parser.nextToken();
        	   str2 = parser.nextToken();
        	   //setting projx
        	   myGlobal.setProjx(Double.valueOf(str2).doubleValue());
        	   str2 = parser.nextToken();
        	   //setting projy
        	   myGlobal.setProjy(Double.valueOf(str2).doubleValue());
           }
           catch(Exception e)
      	   {
      		  System.err.println("YOU FOOL: Projection Error");
      		  System.exit(1);
      	   }
           //Elevation
           if (str.indexOf("ELEVATION") != -1)
               try
               {
            	   parser = new StringTokenizer(str);
            	   str2 = parser.nextToken();
            	   str2 = parser.nextToken();
            	   //setting elevation
            	   myGlobal.setEL(Double.valueOf(str2).doubleValue());
               }
               catch(Exception e)
          	   {
          		  System.err.println("YOU FOOL: Elevation Error");
          		  System.exit(1);
          	   }
           	 
	    			} //if (!str1.startsWith("*"))
	    		} // while ((str1 = in.readLine()) != null)
	    		in.close();
	    } // end first try
    catch(NumberFormatException e)
    {
      System.err.println("Catalog Error");
      System.exit(1);
    }
    catch(FileNotFoundException e)
    {
      System.err.println("Catalog file Shady.txt not found");
      System.exit(1);
      
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
	    
	    
	    } //end openFile 	    
} //end class CatReader
	    

