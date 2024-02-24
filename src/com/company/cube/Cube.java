package com.company.cube;

import java.util.HashMap;
import java.util.Random;

import static com.company.cube.CubeAlgUtils.*;

public class Cube {
    private String state;
    //UB, UR, UF, UL, FR, FL, BR, BL, DB, DR, DF, DL
    //00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11
    //WB, WR, WG, WO, GR, GO, BR, BO, YB, YR, YG, YO
    // a,  b,  c,  d,  e,  f,  g,  h,  i,  j,  k,  l //oriented
    // m,  n,  o,  p,  q,  r,  s,  t,  u,  v,  w,  x //unoriented

    //UBL, UBR, UFR, UFL, DBL, DBR, DFL, DFR
    //12,  13,  14,  15,  16,  17,  18,  19
    //WBO, WBR, WGR, WGO, YBO, YBR, YGO, YGR
    //  a,   b,   c,   d,   e,   f,   g,   h //oriented
    //  i,   j,   k,   l,   m,   n,   o,   p //clockwise
    //  q,   r,   s,   t,   u,   v,   w,   x //counter-clockwise
    public Cube(boolean scrambled) {
        if (!scrambled) {
            this.state = "abcdefghijklabcdefgh";
        } else {
            setRandomState();
        }
    }

    public Cube(String scrambleAlg) {
        this.state = executeAlg("abcdefghijklabcdefgh", scrambleAlg);
    }

    public String solve() {
        StringBuilder solution = new StringBuilder();
        String scrambledToEOAlg = CubeSolverUtils.solveEO(this.state);
        solution.append(scrambledToEOAlg);

        String EOSolved = executeAlg(this.state, scrambledToEOAlg);
        String EOTo3x2x2Alg = CubeSolverUtils.solve3x2x1FromEO(EOSolved);
        solution.append(" ").append(EOTo3x2x2Alg);

        String x3x2x2Solved = executeAlg(EOSolved, EOTo3x2x2Alg);
        String x3x2x2ToF2LAlg = CubeSolverUtils.solveF2LFrom3x2x1(x3x2x2Solved);
        solution.append(" ").append(x3x2x2ToF2LAlg);

        String F2LSolved = executeAlg(x3x2x2Solved, x3x2x2ToF2LAlg);
        String F2LToZBLLAlg = CubeSolverUtils.solveZBLLFromF2L(F2LSolved);
        solution.append(" ").append(F2LToZBLLAlg);

        String ZBLLSolved = executeAlg(F2LSolved, F2LToZBLLAlg);

        HashMap<String, String> AUFMap = new HashMap<>();
        AUFMap.put("abcdefghijklabcdefgh", "");
        AUFMap.put("abcdefghjkliabcdfheg", "D'");
        AUFMap.put("abcdefghlijkabcdgehf", "D");
        AUFMap.put("abcdefghklijabcdhgfe", "D2");
        solution.append(AUFMap.get(ZBLLSolved));
        String alg = AUFMap.get(ZBLLSolved);
        String s = executeAlg(ZBLLSolved, alg);
        return !s.equals("abcdefghijklabcdefgh") ? null : CubeAlgUtils.formatAlg(solution.toString());
    }
    public void alg(String alg){
        state = executeAlg(state, alg);
    }
    public void setRandomState() {
        StringBuilder randomState = new StringBuilder();
        StringBuilder edges = new StringBuilder("abcdefghijkl");
        Random rand = new Random();
        int flippedEdgeCount = 0;
        for (int i = 0; i < 11; i++) {
            int randChar = rand.nextInt(edges.length());
            boolean flipEdge = rand.nextBoolean();
            if (flipEdge){
                randomState.append(edges.charAt(randChar));
            }
            else{
                randomState.append(flipEdge(edges.charAt(randChar)));
                flippedEdgeCount++;
            }
            edges.deleteCharAt(randChar);
        }
        //checks if even or odd
        if ( (flippedEdgeCount & 1) == 0  ){
            randomState.append(edges.charAt(0));
        }
        else {
            randomState.append(flipEdge(edges.charAt(0)));
        }
        //corners
        StringBuilder corners = new StringBuilder("abcdefgh");
        int clockwiseCornerCount = 0;
        int counterClockwiseCornerCount = 0;
        for (int i = 0; i < 7; i++) {
            int randChar = rand.nextInt(corners.length());
            int cornerOrientation = rand.nextInt(3);
            switch (cornerOrientation){
                case 0 ->{
                    randomState.append(corners.charAt(randChar));
                }
                case 1 ->{
                    randomState.append(rotateCorner(corners.charAt(randChar), true));
                    counterClockwiseCornerCount++;
                }
                case 2 ->{
                    randomState.append(rotateCorner(corners.charAt(randChar), false));
                    clockwiseCornerCount++;
                }
            }
            corners.deleteCharAt(randChar);
        }
        int twistDiff = clockwiseCornerCount - counterClockwiseCornerCount;
        if (twistDiff % 3 == 0){
            randomState.append(corners.charAt(0));
        }
        else if ((twistDiff + 1) % 3 == 0){
            randomState.append(rotateCorner(corners.charAt(0), false));
        }
        else {
            randomState.append(rotateCorner(corners.charAt(0), true));
        }
        //check if swap parity
        state = randomState.toString();
        if (solve() == null){
            boolean swapEdge = rand.nextBoolean();
            if (swapEdge){
                int indexEdge1 = rand.nextInt(12);
                int indexEdge2 = rand.nextInt(12);
                if (indexEdge2 == indexEdge1){
                    if (indexEdge2 == 11){
                        indexEdge2--;
                    }
                    else{
                        indexEdge2++;
                    }
                }
                char edge1 = randomState.charAt(indexEdge1);
                randomState.setCharAt(indexEdge1, randomState.charAt(indexEdge2));
                randomState.setCharAt(indexEdge2, edge1);
            }
            else {
                int indexCorner1 = rand.nextInt(8);
                int indexCorner2 = rand.nextInt(8);
                if (indexCorner2 == indexCorner1){
                    if (indexCorner2 == 7){
                        indexCorner2--;
                    }
                    else{
                        indexCorner2++;
                    }
                }
                char corner1 = randomState.substring(12).charAt(indexCorner1);
                char corner2 = randomState.substring(12).charAt(indexCorner2);
                int orientation1 = cornerToOrientation(corner1);
                randomState.setCharAt(indexCorner1 + 12, orientCorner(corner2, orientation1));
                int orientation2 = cornerToOrientation(corner2);
                randomState.setCharAt(indexCorner2 + 12, orientCorner(corner1, orientation2));
            }
        }
        state = randomState.toString();
    }
}