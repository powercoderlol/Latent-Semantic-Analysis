package com.algo;


import org.ejml.alg.dense.decomposition.svd.SvdImplicitQrDecompose_D64;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

import java.util.Random;


/**
 * Created by Ivan on 16.04.2017.
 */
public class LSA {
    private int Nums, Cols;
    private DenseMatrix64F Matrix;
    private SimpleSVD<SimpleMatrix> currSVD;
    private SimpleMatrix U, W, V;

    public LSA(int Cols, int Nums, double[] DataMatrix) {
        this.Nums = Nums; this.Cols = Cols;
        Matrix = new DenseMatrix64F(Nums,Cols,true, DataMatrix);
        currSVD = new SimpleSVD<>(Matrix, false);
        U = currSVD.getU();
        W = currSVD.getW();
        V = currSVD.getV();
    }

    public void printMatrix2XSVD() {
        System.out.println("U MATRIX: ");
        for(int i = 0; i < Nums; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(U.get(i,j)+" ");
            }
            System.out.println();
        }
        System.out.println("W MATRIX: ");
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(W.get(i,j)+" ");
            }
            System.out.println();
        }
        System.out.println("V MATRIX: ");
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < Cols; j++) {
                System.out.print(V.get(i,j)+" ");
            }
            System.out.println();
        }
    }

    public void printW() {
        System.out.println("W MATRIX: ");
        for(int i = 0; i < Nums; i++) {
            for(int j = 0; j < Cols; j++) {
                System.out.print(W.get(i,j)+" ");
            }
            System.out.println();
        }
    }

}
