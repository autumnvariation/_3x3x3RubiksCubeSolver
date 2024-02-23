package com.company;

import com.company.cube.Cube;

public class Main {

    public static void main(String[] args) {
        Cube cube = new Cube(false);
        cube.alg("F' R2 B' D2 F R2 F' L2 D2 L2 B2 L' F' D' B U2 L U2 L U' L'");
        System.out.println(cube.solve());
    }
}