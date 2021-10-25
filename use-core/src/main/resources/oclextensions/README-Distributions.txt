File "Distributions.xml" contains an extension for USE Real numbers 

Given x, m, s: Real, then:

x.pdf01() returns the distribution function PDF(x) of a Gaussian Distribution with mean=0 and sigma=1, N(0,1) 

x.pdf(m,s) returns the distribution function PDF(x) of a N(m,s)

x.cdf01() returns the CUMMULATIVE distribution function CDF(x) of a Gaussian 
   Distribution with mean=0 and sigma=1, N(0,1) 

x.cdf(m,s) returns the CUMMULATIVE distribution function CDF(x) of a N(m,s)

x.normalDistr(y) returns a value of a Normal (Gaussian) distribution of mean x and sigma y, N(x,y). 
 For example 0.normalDistr(1) returns the value of N(0,1)

Finally, x.expDistr() returns a value of an exponential distribution of mean x (or 0.0 if x==0.0)

