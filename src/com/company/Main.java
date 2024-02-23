package com.company;

import com.company.cube.Cube;

public class Main {

    public static void main(String[] args) {
        Cube cube = new Cube(false);

        System.out.println(cube.solve());
    }
}