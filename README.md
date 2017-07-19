# shady
Solar Position Calculator

Returns alt-az and ra-dec coordinates. Based off VSOP087. J2000 epoch precessed to runtime. Uses algorithms from Jean Meeus "Astronomical Algorithms". Observer location can be fixed in Shady.txt.

The calls that result in these outputs are:
	myGeometry.calcSunRtAscension());
	myGeometry.calcSunDeclination())
	myGeometry.calcAzimuth());
	myGeometry.calcAlt());
     
The output of other functions be seen in Sunalg.java, but these are the most important ones.

This program was originally written to give "window" coordinates to a truss climbing robot to enable it to shade a desired area. 
https://www.csail.mit.edu/videoarchive/research/robo/shady
