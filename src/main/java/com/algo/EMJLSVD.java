package com.algo;


import org.ejml.alg.dense.decomposition.svd.SvdImplicitQrDecompose_D64;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.SingularMatrixException;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

import java.util.Arrays;
import java.util.Random;


/**
 * Created by Ivan on 16.04.2017.
 */
public class EMJLSVD {
    private int Nums, Cols;
    private DenseMatrix64F Matrix;
    private SimpleSVD<SimpleMatrix> currSVD;
    private SimpleMatrix U, W, V;
    private double[] res;

    public EMJLSVD(int Cols, int Nums, double[] DataMatrix) {
        this.Nums = Nums; this.Cols = Cols;
        Matrix = new DenseMatrix64F(Nums,Cols,true, DataMatrix);
        currSVD = new SimpleSVD<>(Matrix, true);
        U = currSVD.getU();
        W = currSVD.getW();
        V = currSVD.getV();
    }

    public boolean isEmpty() {
        if (currSVD == null) return true;
        else return false;
    }

    public double[] getComparsion() {
        if (res == null) { res = new double[Cols-1]; }
        else {
            Arrays.fill(res, 0);
        }
        for(int i = 0; i < Cols-1; i++) {
            for(int j = 0; j < Cols; j++) {
                res[i] += Math.abs(U.get(j,i))*Math.abs(U.get(j,i+1));
            }
        }
        return res;
    }

    public double[] getComparsionAll(int currParagraphOrder) {
        if (res == null) { res = new double[Cols-1]; }
        else {
            Arrays.fill(res, 0);
        }
        for(int i = 0; i < Cols-1; i++) {
            for(int j = 0; j < Cols; j++) {
                res[i] += Math.abs(U.get(j,currParagraphOrder) * Math.abs(U.get(j, i)));
            }
        }

        return res;
    }

    public String toString(String name) {
        String result = "";
        switch (name.charAt(0)) {
            case 'V':   result = V.toString();
                        break;
            case 'U':   result = U.toString();
                        break;
            case 'W':   result = W.toString();
                        break;
            default:    result = V.toString();
                        break;
        }
        return result.replaceAll(",", ".");
    }


    public int getCols() { return Cols; }

    public void printMatrix() {
        Matrix.print();
    }

}
