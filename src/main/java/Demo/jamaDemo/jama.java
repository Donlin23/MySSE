package Demo.jamaDemo;

import Jama.EigenvalueDecomposition;
import Jama.LUDecomposition;
import Jama.Matrix;
import Jama.QRDecomposition;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @Author: Donlin
 * @Date: Created in 15:46 2019/1/17
 * @Version: 1.0
 * @Description:
 */
public class jama {
    public static Matrix magic(int var0) {
        double[][] var1 = new double[var0][var0];
        int var2;
        int var3;
        int var5;
        if (var0 % 2 == 1) {
            var2 = (var0 + 1) / 2;
            var3 = var0 + 1;

            for(int var4 = 0; var4 < var0; ++var4) {
                for(var5 = 0; var5 < var0; ++var5) {
                    var1[var5][var4] = (double)(var0 * ((var5 + var4 + var2) % var0) + (var5 + 2 * var4 + var3) % var0 + 1);
                }
            }
        } else if (var0 % 4 == 0) {
            for(var2 = 0; var2 < var0; ++var2) {
                for(var3 = 0; var3 < var0; ++var3) {
                    if ((var3 + 1) / 2 % 2 == (var2 + 1) / 2 % 2) {
                        var1[var3][var2] = (double)(var0 * var0 - var0 * var3 - var2);
                    } else {
                        var1[var3][var2] = (double)(var0 * var3 + var2 + 1);
                    }
                }
            }
        } else {
            var2 = var0 / 2;
            var3 = (var0 - 2) / 4;
            Matrix var9 = magic(var2);

            int var6;
            double var7;
            for(var5 = 0; var5 < var2; ++var5) {
                for(var6 = 0; var6 < var2; ++var6) {
                    var7 = var9.get(var6, var5);
                    var1[var6][var5] = var7;
                    var1[var6][var5 + var2] = var7 + (double)(2 * var2 * var2);
                    var1[var6 + var2][var5] = var7 + (double)(3 * var2 * var2);
                    var1[var6 + var2][var5 + var2] = var7 + (double)(var2 * var2);
                }
            }

            for(var5 = 0; var5 < var2; ++var5) {
                for(var6 = 0; var6 < var3; ++var6) {
                    var7 = var1[var5][var6];
                    var1[var5][var6] = var1[var5 + var2][var6];
                    var1[var5 + var2][var6] = var7;
                }

                for(var6 = var0 - var3 + 1; var6 < var0; ++var6) {
                    var7 = var1[var5][var6];
                    var1[var5][var6] = var1[var5 + var2][var6];
                    var1[var5 + var2][var6] = var7;
                }
            }

            double var10 = var1[var3][0];
            var1[var3][0] = var1[var3 + var2][0];
            var1[var3 + var2][0] = var10;
            var10 = var1[var3][var3];
            var1[var3][var3] = var1[var3 + var2][var3];
            var1[var3 + var2][var3] = var10;
        }

        return new Matrix(var1);
    }

    private static void print(String var0) {
        System.out.print(var0);
    }

    public static String fixedWidthDoubletoString(double var0, int var2, int var3) {
        DecimalFormat var4 = new DecimalFormat();
        var4.setMaximumFractionDigits(var3);
        var4.setMinimumFractionDigits(var3);
        var4.setGroupingUsed(false);

        String var5;
        for(var5 = var4.format(var0); var5.length() < var2; var5 = " " + var5) {
            ;
        }

        return var5;
    }

    public static String fixedWidthIntegertoString(int var0, int var1) {
        String var2;
        for(var2 = Integer.toString(var0); var2.length() < var1; var2 = " " + var2) {
            ;
        }

        return var2;
    }

    public static void main(String[] var0) {
        print("\n    Test of Matrix Class, using magic squares.\n");
        print("    See MagicSquareExample.main() for an explanation.\n");
        print("\n      n     trace       max_eig   rank        cond      lu_res      qr_res\n\n");
        Date var1 = new Date();
        double var2 = Math.pow(2.0D, -52.0D);

        for(int var4 = 3; var4 <= 32; ++var4) {
            print(fixedWidthIntegertoString(var4, 7));
            Matrix var5 = magic(var4);
            int var6 = (int)var5.trace();
            print(fixedWidthIntegertoString(var6, 10));
            EigenvalueDecomposition var7 = new EigenvalueDecomposition(var5.plus(var5.transpose()).times(0.5D));
            double[] var8 = var7.getRealEigenvalues();
            print(fixedWidthDoubletoString(var8[var4 - 1], 14, 3));
            int var9 = var5.rank();
            print(fixedWidthIntegertoString(var9, 7));
            double var10 = var5.cond();
            print(var10 < 1.0D / var2 ? fixedWidthDoubletoString(var10, 12, 3) : "         Inf");
            LUDecomposition var12 = new LUDecomposition(var5);
            Matrix var13 = var12.getL();
            Matrix var14 = var12.getU();
            int[] var15 = var12.getPivot();
            Matrix var16 = var13.times(var14).minus(var5.getMatrix(var15, 0, var4 - 1));
            double var17 = var16.norm1() / ((double)var4 * var2);
            print(fixedWidthDoubletoString(var17, 12, 3));
            QRDecomposition var19 = new QRDecomposition(var5);
            Matrix var20 = var19.getQ();
            var16 = var19.getR();
            var16 = var20.times(var16).minus(var5);
            var17 = var16.norm1() / ((double)var4 * var2);
            print(fixedWidthDoubletoString(var17, 12, 3));
            print("\n");
        }

        Date var21 = new Date();
        double var22 = (double)(var21.getTime() - var1.getTime()) / 1000.0D;
        print("\nElapsed Time = " + fixedWidthDoubletoString(var22, 12, 3) + " seconds\n");
        print("Adios\n");
    }

}
