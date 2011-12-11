package stats;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class StatsTest extends TestCase {
      //http://www.abbeyworkshop.com/howto/java/readFile/index.html
      public static ArrayList<String> findImports(String arg){
          ArrayList<String> bucket=new ArrayList<String>();
          try {
              FileReader input = new FileReader(arg);
              BufferedReader bufRead = new BufferedReader(input);
              String line;    // String that holds current file line
              line = bufRead.readLine();
              while (line != null){
                if (line.split("import").length > 1){
                  bucket.add(line);
                }
                  line = bufRead.readLine();
              }
              bufRead.close();
          }catch (ArrayIndexOutOfBoundsException e){
              System.out.println("Usage: findImports( filename ) \n");          
          }catch (IOException e){
              e.printStackTrace();
          } 
          return bucket;
      }// end getSpecs
  
  
      public static boolean almostEqualArrays(double[] a, double[] b){
        if (a.length != b.length){
          return false;
        }
        for(int i=0; i<a.length; i++){
          if(Math.abs(a[i]-b[i])>1E-13){
            return false;
          }
        }
        return true;
      }
      
      
      public static boolean almostEqualArraysP4(double[] a, double[] b){
        if (a.length != b.length){
          return false;
        }
        for(int i=0; i<a.length; i++){
          if(Math.abs(a[i]-b[i])>1E-4){
            return false;
          }
        }
        return true;
      }
      
      
  @Test
  public void test_mean(){
    assertTrue(Stats.mean(new double[] {-2,-3,3,2})==0.0);
    assertTrue(Stats.mean(new double[] {-1,-4,4,1,0})==0.0);
    assertTrue(Stats.mean(new double[] {10,11,12,9,8})==10);
  }
  
  @Test
  public void test_nonCollisionProbability(){
    assertTrue(Math.abs(Stats.nonCollisionProbability(20,20)-0.000000023202)< 1E-11);
    assertTrue(Stats.nonCollisionProbability(21,20)==0);
    assertTrue(Stats.nonCollisionProbability(1,20)==1);
    assertTrue(Stats.nonCollisionProbability(2,4)==0.75);
  }

  @Test
  public void test_binomialVector(){
    assertTrue(almostEqualArrays(Stats.binomialVector(0.5,2), new double[]{0.25,0.5,0.25}));
    assertTrue(almostEqualArrays(Stats.binomialVector(0.9,3), new double[]{0.001,0.027,0.243,0.729}));
    assertTrue(almostEqualArrays(Stats.binomialVector(0.6,4), new double[]{0.0256,0.1536,0.3456,0.3456,0.1296}));
    assertTrue(almostEqualArrays(Stats.binomialVector(0.8,5), new double[]{3.2E-4, 0.0064,0.0512,0.2048,0.4096,0.32768}));
  }
 
//  @Test
//  public void test_sieve(){
//    assertTrue( Arrays.equals(Stats.sieve(29), new boolean[]{ false, false, true, true, false, true, false, true, false, false, false, true, false, true, false, false, false, true, false, true, false, false, false, true, false, false, false, false, false, true  } ));
//  }
  
  @Test
  public void test_perm(){
   assertEquals(Stats.perm(5,5),120); 
   assertEquals(Stats.perm(5,2),20);
   assertEquals(Stats.perm(15,3),2730); 
  }
  
  @Test
  public void test_comb(){
   assertEquals(Stats.comb(1,0),1);
   assertEquals(Stats.comb(1,1),1);
   assertEquals(Stats.comb(2,0),1);
   assertEquals(Stats.comb(2,1),2);
   assertEquals(Stats.comb(2,2),1);
   assertEquals(Stats.comb(10,1),10);
   assertEquals(Stats.comb(6,2),15);
   assertEquals(Stats.comb(6,3),20);
   assertEquals(Stats.comb(6,4),15);
   assertEquals(Stats.comb(25,10),3268760);
  }
  
  @Test
  public void test_binomial(){
   double[] v = Stats.binomialVector(0.8,15);
   for(int i=0; i<=15; i++){
    assertTrue(Math.abs(Stats.binomial(0.8,i,15)- v[i]) < 1E-13); 
   }
  }
  
  @Test
  public void test_rab(){
    double[] x = {43,48,56,61,67,70};
    double[] y = {128,120,135,143,141,152};
    double[] v = Stats.rab(x,y);
    assertTrue(almostEqualArrays(v, new double[]{0.8966728145489881, 81.04808548530721, 0.9643811219946572}));
    double[] x2={6,2,15,9,12,5,8};
    double[] y2={82,86,43,74,58,90,78};
    double[] v2=Stats.rab(x2,y2);
    assertTrue(almostEqualArrays(v2, new double[]{ -0.9442151706879178, 102.49253731343283, -3.6218905472636815 }));
    double[] x3={3,0,2,5,8,5,10,2,1};
    double[] y3={48,8,32,64,10,32,56,72,48};
    double[] v3=Stats.rab(x3,y3);
    assertTrue(almostEqualArrays(v3, new double[]{ 0.06721120896834239, 39.292929292929294, 0.45454545454545453 }));
  }
  
  @Test
  public void test_VarStd(){
    double[] x = {11.2, 11.9, 12.0, 12.8, 13.4, 14.3};
    assertTrue(Math.abs(Stats.sampleStd(x)-1.1296016997154146) < 1E-13);
    assertTrue(Math.abs(Stats.sampleVar(x)-1.2759999999999536) < 1E-13);
    double[] x2={10,60,50,30,40,20};
    assertTrue(Math.abs(Stats.popStd(x2)- 17.07825127659933 ) < 1E-13);
    assertTrue(Math.abs(Stats.popVar(x2)- 291.6666666666667 ) < 1E-13);
  }
  
  @Test
  public void test_erf(){
   double[] k =  { 0.0, 0.0040000000000000036, 0.008000000000000007, 0.01200000000000001, 0.016000000000000014, 0.01990000000000003, 0.023900000000000032, 0.027900000000000036, 0.03190000000000004, 0.03590000000000004, 0.03980000000000006, 0.04380000000000006, 0.047800000000000065, 0.05170000000000008, 0.05569999999999997, 0.059599999999999986, 0.06359999999999999, 0.0675, 0.07140000000000002, 0.07530000000000003, 0.07930000000000004, 0.08320000000000005, 0.08710000000000007, 0.09100000000000008, 0.0948, 0.09870000000000001, 0.10260000000000002, 0.10640000000000005, 0.11030000000000006, 0.11409999999999998, 0.1179, 0.12170000000000003, 0.12550000000000006, 0.12930000000000008, 0.1331, 0.13680000000000003, 0.14060000000000006, 0.14429999999999998, 0.14800000000000002, 0.15170000000000006, 0.15539999999999998, 0.15910000000000002, 0.16280000000000006, 0.1664, 0.17000000000000004, 0.1736000000000001, 0.17720000000000002, 0.18080000000000007, 0.1844, 0.18790000000000007, 0.1915, 0.19500000000000006, 0.1985, 0.20190000000000008, 0.20540000000000003, 0.20879999999999999, 0.21230000000000004, 0.2157, 0.21900000000000008, 0.22240000000000004, 0.2257, 0.22910000000000008, 0.23240000000000005, 0.23570000000000002, 0.2389, 0.24220000000000008, 0.24540000000000006, 0.24860000000000004, 0.25170000000000003, 0.2549, 0.258, 0.2611, 0.2642, 0.2673, 0.2704000000000001, 0.2734000000000001, 0.2764000000000001, 0.2794000000000001, 0.2823, 0.2852, 0.2881, 0.29100000000000004, 0.29390000000000005, 0.2967000000000001, 0.2995, 0.3023, 0.30510000000000004, 0.3078000000000001, 0.3106, 0.3133, 0.31590000000000007, 0.3186, 0.32120000000000004, 0.3238000000000001, 0.3264, 0.3289000000000001, 0.3315, 0.3340000000000001, 0.3365, 0.3389000000000001 };
   double[] s = new double[100];
   for(int i=0;i<100; i++){s[i]=(Stats.erf(0.01*i)-0.5);}
   assertTrue(almostEqualArraysP4(k,s));
   for(int i=0; i<100; i+=7){
    assertEquals(1-Stats.erf(i*0.03), Stats.erf(-i*0.03)); 
   }
  }
  
  @Test
  public void test_geometric(){
   double[] k =  { 0.5, 0.25, 0.125, 0.0625, 0.03125, 0.015625, 0.0078125, 0.00390625, 0.001953125, 9.765625E-4, 4.8828125E-4, 2.44140625E-4, 1.220703125E-4, 6.103515625E-5, 3.0517578125E-5 };
   double[] k2=  { 0.9, 0.08999999999999998, 0.008999999999999996, 8.999999999999994E-4, 8.999999999999992E-5, 8.99999999999999E-6, 8.999999999999988E-7, 8.999999999999987E-8, 8.999999999999985E-9, 8.999999999999982E-10, 8.999999999999979E-11, 8.999999999999979E-12, 8.999999999999977E-13, 8.999999999999974E-14, 8.999999999999971E-15 };
   assertTrue(almostEqualArrays(k, Stats.geometric(0.5)));
   assertTrue(almostEqualArrays(k2, Stats.geometric(0.9)));
  }
  
  @Test
  public void test_raw2z(){
   assertEquals(Stats.raw2z(8,6,2),1.0); 
   assertEquals(Stats.raw2z(4,6,2),-1.0); 
   assertTrue(Math.abs(Stats.raw2z(3,2.3,1)-0.7)<1E-7); 
  }
  
  
  @Test
  public void test_z2raw(){
   assertEquals(Stats.z2raw(1.0,6,2),8.0); 
   assertEquals(Stats.z2raw(-1.0,6,2),4.0); 
   assertTrue(Math.abs(Stats.z2raw(0.7,2.3,1)-3)<1E-7);     
  }
  
  @Test
  public void test_hypTestA(){
   double[] v = Stats.hypTestA(43260,30,42000,5230);
   assertTrue(Math.abs(v[0]-1+Stats.erf(1.32))<1E-3);
   assertTrue(v[0]*2==v[1]);
   double[] v2 = Stats.hypTestA(75,36,80,19.2);
   assertTrue(Math.abs(v2[0]-1+Stats.erf(1.56))<1E-3);
   double[] v3 = Stats.hypTestA(25226,35,24672,3251);
   assertTrue(Math.abs(v3[0]-1+Stats.erf(1.01))<1E-3);
  }
  
  @Test
  public void test_hist2pdf(){
    double[][] d = {{1,2,3},{1,2,1}};
    double[][] d2 = {{1,2,3,4},{1,3,3,1}};
    double[][] d3 = {{1,2,3,4},{1,2,3,4}};
    double[][] a = Stats.hist2pdf(d);
    double[][] a2 = Stats.hist2pdf(d2);
    double[][] a3 = Stats.hist2pdf(d3);
    assertTrue(almostEqualArrays(a[1],new double[]{0.25,0.5,0.25}));
    assertTrue(almostEqualArrays(a2[1],new double[]{.125, .375, .375, .125}));
    assertTrue(almostEqualArrays(a3[1],new double[]{.1,.2,.3,.4}));
  }
  
  @Test
  public void test_pdfCompose2(){
     double[][] p = {{1,2,3},{0.3,0.3,0.4}};
     double[][] q = {{4,5},{0.9,0.1}};
     double[][] r = Stats.pdfCompose2(p,q);
     assertTrue(almostEqualArrays(r[0], new double[]{ 5.0, 6.0, 7.0, 8.0 } ));
     assertTrue(almostEqualArrays(r[1], new double[] { 0.27, 0.30000000000000004, 0.39, 0.04000000000000001 } ));
  }
  
  @Test
  public void test_pdfComposeN(){
     double[][] p = {{10,11},{0.5,0.5}};
     double[][] r = Stats.pdfComposeN(p,4);
     assertTrue(almostEqualArrays(r[0], new double[]{ 40.0, 41.0, 42.0, 43.0, 44.0} ));
     assertTrue(almostEqualArrays(r[1], new double[] {0.0625, 0.25, 0.375, 0.25, 0.0625 } ));
  }
  
  @Test
  public void test_findLambda(){
   assertEquals(0.5, Stats.findLambda(new double[]{1,4,5,7}) ); 
  }
  
  @Test
  public void test_getRandomWaitTime(){
   double sum = 0;
   for (int i=0; i<10000; i++){
     sum = sum + Stats.getRandomWaitTime(2); 
   }
   assertTrue(Math.abs(sum-5000) < 300);   
   sum = 0;
   double[] bin = new double[10];
   double hold;
   for (int i=0; i<10000; i++){
     hold = Stats.getRandomWaitTime(1);
     sum = sum + hold; 
     if (hold < 10){
      bin[(int)Math.floor(hold)] += 1; 
     }
   }
   assertTrue(Math.abs(sum-10000) < 300);
   //System.out.println(bin[0]+" "+bin[1]+" "+bin[2]);/////////////
   assertTrue(Math.abs(bin[0]-6321) < 150);
   assertTrue(Math.abs(bin[1]-2325) < 85);
   assertTrue(Math.abs(bin[2]-855) < 65);
  }
  
  
}//end of file
