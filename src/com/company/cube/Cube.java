package com.company.cube;

import java.util.HashMap;

import static com.company.cube.CubeAlgUtils.executeAlg;

public class Cube {
    private String state;

    public Cube(boolean scrambled) {
        if (!scrambled) {
            this.state = "abcdefghijklabcdefgh";
        } else {
            this.state = CubeAlgUtils.genRandomState();
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

}