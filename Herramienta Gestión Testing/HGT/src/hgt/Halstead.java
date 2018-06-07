/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

public class Halstead {
    private int n1, n2, N1, N2;
    
    public Halstead () {}
    public Halstead (int n1, int n2, int N1, int N2) {
        this.n1 = n1;
        this.n2 = n2;
        this.N1 = N1;
        this.N1 = N1;
    }
    
    public int getn1() {
        return n1;
    }

    public void setn1(int n1) {
        this.n1 = n1;
    }

    public int getn2() {
        return n2;
    }

    public void setn2(int n2) {
        this.n2 = n2;
    }

    public int getN1() {
        return N1;
    }

    public void setN1(int N1) {
        this.N1 = N1;
    }

    public int getN2() {
        return N2;
    }

    public void setN2(int N2) {
        this.N2 = N2;
    }
    
    public int getLong() {
        return N1 + N2;
    }
    
    public double getVol() {
        int N = N1 + N2;
        int n = n1 + n2;
        
        return n == 0 ? 0 : (N * (Math.log(n) / Math.log(2)));
    }
    
    @Override
    public String toString() {
        return "N1: " + N1 + " N2: " + N2 + " n1: " + n1 + " n2: " + n2;
    }
}
