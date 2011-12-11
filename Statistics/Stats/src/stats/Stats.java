package stats;
/*********************************************
 * Name: Zhou Tan
 * Date: 12/5/2011
 * University of Pennsylvania
 * CIT 592
 * Instructor: Donna Dietz
 *
 *
 * *******************************************/

import java.util.ArrayList;
/*This is the only import you are allowed!!! (Explicit or Implicit) */



/**
 * Statistic function realizations
 * @author Zhou Tan
 *
 */
public class Stats{ 

    /**
     * returns the mean of the entries in a
     * @param a input array
     * @return mean value of this array
     */
    public static double mean(double[] a){
        double sum = 0;
        for (double num : a)
        {
            sum += num;
        }
        return sum / a.length;
    }

    /**
     * returns the probability of no collisions occurring in a (randomly assigned) hashing 
     * function having slots slots and requiring the hashing of keys, or items. 
     * @param keys total number of keys
     * @param slots total number of slots
     * @return non collision probability
     */
    public static double nonCollisionProbability(double keys, double slots){
        double ret = 1;
        double factSlots = slots;
        if (keys > slots) {
            return 0;
        }
        if (keys - 1 < 1E-6) {
            return 1;
        }
        for (int i = 0; i < (int) keys; i++) {
            ret *= factSlots--;
        }
        ret /= Math.pow(slots, keys);
        return ret;
    }

    /**
     * returns a vector for the probabilities of 0 through n successes of a binomial experiment, 
     * given that the chance of a single success is success and there will be n trials.
     * @param success probability of success
     * @param number of trials
     * @return binomial vectors
     */
    public static double[] binomialVector(double success, int n){
        double[] retList = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            retList[i] = comb(n, i);
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    retList[i] *= success;
                } else {
                    retList[i] *= 1 - success;
                }
            }
        }
        return retList;
    }

    /**
     * returns the distinct permutations of n things taken m at a time
     * @param n total number of things
     * @param m total number of things taken
     * @return total number of distinct permutations
     */
    public static long perm(long n, long m){
        //permutations of n things taken m at a time
        if (m > n) {
            m = n;
        }
        int retPerm = 1;
        for (int i = 0; i < m; i++) {
            retPerm *= n--; 
        }
        return retPerm;
    }

    /**
     * returns distinct combinations of n things taken m at a time
     * @param n total number of things
     * @param m total number of things taken
     * @return total number of distinct combinations
     */
    public static long comb(long n, long m){
        if (m > n) {
            m = n;
        }
        double retComb = 1;
        long arithM = m;
        for (int i = 0; i < m; i++) {
            retComb *= n--; 
            retComb /= arithM--;
        }
        return (long) retComb;
    }  


    /**
     * returns the probability of exactly numSuccesses successes in numTrials trials 
     * for a binomial experiment having a single success probability of successProb.
     * @param successProb probability of success
     * @param numSuccess total number of success
     * @param numTrials number of trials
     * @return binomial experiment probability
     */
    public static double binomial(double successProb, int numSuccess, int numTrials){
        double retBinomial = comb(numTrials, numSuccess);
        for (int i = 0; i < numTrials; i++)
        {
            if (i < numSuccess)
            {
                retBinomial *= successProb;
            }
            else {
                retBinomial *= 1 - successProb;
            }
        }
        return retBinomial;
    }

    /**
     * takes in a collection of points in two dimensions and returns the correlation 
     * as well as the slope and intercept for the line of best fit.
     * @param x first collection of dots
     * @param y second collection of dots
     * @return r, a, b
     */
    public static double[] rab(double[] x, double[] y){
        double sigmaX = 0;
        double sigmaY = 0;
        double sigmaXY = 0;
        double sigmaXSquare = 0;
        double sigmaYSquare = 0;
        int size = x.length;
        double[] xy = new double[size];
        double[] xSquare = new double[size];
        double[] ySquare = new double[size];
        double beta, alpha, gamma;

        for (int i = 0; i < size; i++) {
            xy[i] = x[i] * y[i];
            xSquare[i] = x[i] * x[i];
            ySquare[i] = y[i] * y[i];
            sigmaX += x[i];
            sigmaY += y[i];
            sigmaXY += xy[i];
            sigmaXSquare += xSquare[i];
            sigmaYSquare += ySquare[i];
        }
        beta = (size * sigmaXY - sigmaX * sigmaY) / (size * sigmaXSquare - Math.pow(sigmaX, 2));
        alpha = (sigmaY * sigmaXSquare - sigmaX * sigmaXY) / (size * sigmaXSquare - Math.pow(sigmaX, 2));
        gamma = (size * sigmaXY - sigmaX * sigmaY) / Math.sqrt((size * sigmaXSquare - Math.pow(sigmaX, 2)) *
                (size * sigmaYSquare - Math.pow(sigmaY, 2)));
        double[] retList = new double[3];
        retList[0] = gamma;
        retList[1] = alpha;
        retList[2] = beta;
        return retList; 
    }


    /**
     * returns variance of population
     * @param input vector
     * @return variance of population
     */
    public static double popVar(double[] x){
        double meanVal = mean(x);
        double popVar = 0;
        for (int i = 0; i < x.length; i++) {
            popVar += Math.pow(x[i] - meanVal, 2);
        }
        return popVar / x.length;
    }

    /**
     * returns standard deviation of population
     * @param x input vector
     * @return standard deviation of population
     */
    public static double popStd(double[] x){
        return Math.sqrt(popVar(x));
    }

    /**
     * returns sample variance
     * @param x input vector
     * @return sample variance
     */
    public static double sampleVar(double[] x){
        int length = x.length;
        double meanVal = mean(x);
        double[] xSquare = new double[length];
        double sampleVar = 0;
        for (int i = 0; i < length; i++) {
            xSquare[i] = x[i] * x[i];
        }
        sampleVar = (mean(xSquare) - Math.pow(meanVal, 2)) * length / (length - 1);
        return sampleVar;
    }

    /**
     * returns sample standard deviation
     * @param x input vector
     * @return sample standard deviation
     */
    public static double sampleStd(double[] x){
        return Math.sqrt(sampleVar(x));
    }

    /**
     * returns the area under the normal curve to the left of the given z-value. 
     * @param z z-value
     * @return area on the left side of z-value
     */
    public static double erf(double z){
        double step = 1E-4;
        double area = 0;
        double newZ = z;
        double oldArea = Double.NEGATIVE_INFINITY;
        if (z < 0)
            return 1 - erf(-z);
        while (area - oldArea > 1E-8) {
            oldArea = area;
            area += 1.0 / Math.sqrt(2 * Math.PI) * Math.pow(Math.E, -0.5 * newZ * newZ) * step;
            newZ = newZ - step;
        }
        int trunc = (int) Math.round(area * 1E5);
        return (double) trunc / 1E5;
    }

    /**
     * returns a vector in geometric distribution
     * @param success probability
     * @return vector in geometric distribution
     */
    public static double[] geometric(double r){
        int size = 15;
        double prob = Math.pow(1 - r, 14) * r;
        while (prob > 1E-3) {
            size++;
            prob *= 1 - r;
            if (size == 1000) {
                break;
            }
        }

        double[] geoArray = new double[size];
        prob = r;
        for (int i = 0; i < size; i++) {
            geoArray[i] = prob;
            prob *= 1 - r;
        }
        return geoArray;
    }

    /**
     * returns an estimate of combinations of n things taken m at a time
     * using error function
     * @param n total number of things
     * @param m total number of things taken
     * @return estimation of C(n, m)
     */
    public static double estComb(long n, long m){
        double xHigh = m + 0.5;
        double xLow = m - 0.5;
        double mu = n * 1.0 / 2;
        double sigma = Math.sqrt((double) n * 1.0 / 4);
        double zHigh = raw2z(xHigh, mu, sigma);
        double zLow = raw2z(xLow, mu, sigma);
        double area = erf(zHigh) - erf(zLow);

        return area * Math.pow(2, n);
    }  

    /**
     * convert a raw score to z-score
     * @param score raw score
     * @param mu expected value
     * @param std standard deviation
     * @return z-score
     */
    public static double raw2z(double score, double mu, double std){
        return (score - mu) / std;
    }

    /**
     * convert a z-score to raw score
     * @param z z-score
     * @param mu expected value
     * @param std standard deviation
     * @return raw score
     */
    public static double z2raw(double z, double mu, double std){
        return z * std + mu;
    }


    /**
     * calculates the 1-tail and 2-tail probabilities that an arbitrary sample mean 
     * is at least as extreme as the one suggested
     * @param xb sample mean
     * @param n sample number
     * @param mu expected value
     * @param std standard deviation
     * @return 1-tail and 2-tail probabilities
     */
    public static double[] hypTestA(double xb, int n, double mu, double std){
        double z = Math.abs((xb - mu) / std * Math.sqrt(n));
        double oneTail = 1 - erf(z);
        double[] retArray = new double[2];
        retArray[0] = oneTail;
        retArray[1] = oneTail * 2;
        return retArray;
    }

    /**
     * converts a frequency histogram to a discrete probability density function
     * @param d frequency histogram vector
     * @return pdf
     */
    public static double[][] hist2pdf(double[][] d){
        double sum = 0;
        double[][] retArray = new double[d[0].length][d[1].length];
        for (int i = 0; i < d[1].length; i++) {
            sum+= d[1][i];
        }
        retArray[0] = d[0];
        for (int i = 0; i < d[1].length; i++) {
            retArray[1][i] = d[1][i] / sum;
        }
        return retArray;
    }

    /**
     * calculates the discrete probability density function 
     * based on two original probability density functions
     * @param p first pdf vector
     * @param q second pdf vector
     * @return the composed pdf vector
     */
    public static double[][] pdfCompose2(double[][] p, double[][] q){
        // assume indices are n...m in obvious fashion- for p and q
        ArrayList<Double> sum = new ArrayList<Double>();
        ArrayList<Double> prob = new ArrayList<Double>();
        for (int i = 0; i < p[0].length; i++) {
            for (int j = 0; j < q[0].length; j++) {
                Double e = new Double(p[0][i] + q[0][j]);
                if (!sum.contains(e)) {
                    sum.add(e);
                    Double combProb = new Double(p[1][i] * q[1][j]);
                    prob.add(combProb);
                }
                else {
                    int index = sum.indexOf(e);
                    prob.set(index, new Double(p[1][i] * q[1][j] + prob.get(index).doubleValue()));
                }
            }
        }
        double[][] retArray = new double[2][sum.size()];
        for (int i = 0; i < sum.size(); i++) {
            retArray[0][i] = sum.get(i).doubleValue();
            retArray[1][i] = prob.get(i).doubleValue();
        }
        return retArray; 
    }

    /**
     * compose p with itself n times
     * @param p input pdf vector
     * @param times p composes with itself
     * @return composed pdf vector
     */
    public static double[][] pdfComposeN(double[][] p, int n){
        double[][] compose = p;
        for (int i = 0; i < n - 1; i++) {
            compose = pdfCompose2(compose, p);
        }
        return compose;
    }

    /**
     * takes in a double array indicating the arrival times of network packets
     * @param v input array
     * @return lambda
     */
    public static double findLambda(double[] v){
        //find lambda for the exponential distribution,
        //presuming the following packet arrival times are 
        //exponentially distributed
        int sum = 0;
        for (int i = 0; i < v.length - 1; i++) {
            sum += v[i + 1] - v[i];
        }
        double lambda = (v.length - 1) * 1.0 / sum;
        return lambda;
    }

    /**
     * takes in the lambda parameter for an exponential distribution
     * return a value that will satisfy exponential distribution
     * @param lambda the lambda parameter
     * @return a random value that satisfies exponential distribution
     */
    public static double getRandomWaitTime(double lambda){
        //figure out a random wait time, based on the exponential distribution with lambda
        double rand = Math.random();
        double ret = Math.log(1 - rand) / (-lambda);
        return ret;
    }

    /* If the unittest for this fails only sometimes, that's ok. It should pass "usually".
     * See instructor if there are concerns.
     */


}//end of class Stats


