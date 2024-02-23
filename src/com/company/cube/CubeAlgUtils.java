package com.company.cube;

import java.util.HashMap;

public final class CubeAlgUtils {

    public static String genRandomState() {
        //TODO gen random state
        return null;
    }

    public static String formatAlg(String alg) {
        //TODO: format redundant repetitions in algs (ex: L L2)
        StringBuilder formattedAlg = new StringBuilder();
        String[] moves = alg.split(" ");
        for (String move : moves) {
            if (!move.isEmpty() && !move.isBlank()) {
                formattedAlg.append(move).append(" ");
            }
        }
        return formattedAlg.toString().trim();
    }

    public static String executeAlg(String state, String alg) {
        if (alg != null && !alg.isEmpty()) {
            alg = algToRotationlessAlg(alg);
            String[] moves = alg.split(" ");

            for (String move : moves) {
                if (!move.isEmpty()) {
                    state = executeMove(state, move);
                }
            }

        }
        return state;
    }

    public static String executeMove(String state, String move) {
        StringBuilder returnState = new StringBuilder();
        String cornerState = state.substring(12, 20);
        String edgeState = state.substring(0, 12);
        switch (move) {
            case "U":
                returnState.append(edgeState.charAt(3));
                returnState.append(edgeState.charAt(0));
                returnState.append(edgeState.charAt(1));
                returnState.append(edgeState.charAt(2));
                returnState.append(edgeState, 4, 12);
                returnState.append(cornerState.charAt(3));
                returnState.append(cornerState.charAt(0));
                returnState.append(cornerState.charAt(1));
                returnState.append(cornerState.charAt(2));
                returnState.append(cornerState, 4, 8);
                break;
            case "U'":
                return executeMove(executeMove(executeMove(state, "U"), "U"), "U");
            case "U2":
                return executeMove(executeMove(state, "U"), "U");
            case "R":
                returnState.append(edgeState.charAt(0));
                returnState.append(edgeState.charAt(4));
                returnState.append(edgeState, 2, 4);
                returnState.append(edgeState.charAt(9));
                returnState.append(edgeState.charAt(5));
                returnState.append(edgeState.charAt(1));
                returnState.append(edgeState, 7, 9);
                returnState.append(edgeState.charAt(6));
                returnState.append(edgeState, 10, 12);
                returnState.append(cornerState.charAt(0));
                returnState.append(rotateCorner(cornerState.charAt(2), true));
                returnState.append(rotateCorner(cornerState.charAt(7), false));
                returnState.append(cornerState, 3, 5);
                returnState.append(rotateCorner(cornerState.charAt(1), false));
                returnState.append(cornerState.charAt(6));
                returnState.append(rotateCorner(cornerState.charAt(5), true));
                break;
            case "R'":
                return executeMove(executeMove(executeMove(state, "R"), "R"), "R");
            case "R2":
                return executeMove(executeMove(state, "R"), "R");
            case "F":
                returnState.append(edgeState, 0, 2);
                returnState.append(flipEdge(edgeState.charAt(5)));
                returnState.append(edgeState.charAt(3));
                returnState.append(flipEdge(edgeState.charAt(2)));
                returnState.append(flipEdge(edgeState.charAt(10)));
                returnState.append(edgeState, 6, 10);
                returnState.append(flipEdge(edgeState.charAt(4)));
                returnState.append(edgeState.charAt(11));
                returnState.append(cornerState, 0, 2);
                returnState.append(rotateCorner(cornerState.charAt(3), true));
                returnState.append(rotateCorner(cornerState.charAt(6), false));
                returnState.append(cornerState, 4, 6);
                returnState.append(rotateCorner(cornerState.charAt(7), true));
                returnState.append(rotateCorner(cornerState.charAt(2), false));
                break;
            case "F'":
                return executeMove(executeMove(executeMove(state, "F"), "F"), "F");
            case "F2":
                return executeMove(executeMove(state, "F"), "F");
            case "D":
                returnState.append(edgeState, 0, 8);
                returnState.append(edgeState.charAt(9));
                returnState.append(edgeState.charAt(10));
                returnState.append(edgeState.charAt(11));
                returnState.append(edgeState.charAt(8));
                returnState.append(cornerState, 0, 4);
                returnState.append(cornerState.charAt(5));
                returnState.append(cornerState.charAt(7));
                returnState.append(cornerState.charAt(4));
                returnState.append(cornerState.charAt(6));
                break;
            case "D'":
                return executeMove(executeMove(executeMove(state, "D"), "D"), "D");
            case "D2":
                return executeMove(executeMove(state, "D"), "D");
            case "L":
                returnState.append(edgeState, 0, 3);
                returnState.append(edgeState.charAt(7));
                returnState.append(edgeState.charAt(4));
                returnState.append(edgeState.charAt(3));
                returnState.append(edgeState.charAt(6));
                returnState.append(edgeState.charAt(11));
                returnState.append(edgeState, 8, 11);
                returnState.append(edgeState.charAt(5));
                returnState.append(rotateCorner(cornerState.charAt(4), false));
                returnState.append(cornerState, 1, 3);
                returnState.append(rotateCorner(cornerState.charAt(0), true));
                returnState.append(rotateCorner(cornerState.charAt(6), true));
                returnState.append(cornerState.charAt(5));
                returnState.append(rotateCorner(cornerState.charAt(3), false));
                returnState.append(cornerState.charAt(7));
                break;
            case "L'":
                return executeMove(executeMove(executeMove(state, "L"), "L"), "L");
            case "L2":
                return executeMove(executeMove(state, "L"), "L");
            case "B":
                returnState.append(flipEdge(edgeState.charAt(6)));
                returnState.append(edgeState, 1, 6);
                returnState.append(flipEdge(edgeState.charAt(8)));
                returnState.append(flipEdge(edgeState.charAt(0)));
                returnState.append(flipEdge(edgeState.charAt(7)));
                returnState.append(edgeState, 9, 12);
                returnState.append(rotateCorner(cornerState.charAt(1), true));
                returnState.append(rotateCorner(cornerState.charAt(5), false));
                returnState.append(cornerState, 2, 4);
                returnState.append(rotateCorner(cornerState.charAt(0), false));
                returnState.append(rotateCorner(cornerState.charAt(4), true));
                returnState.append(cornerState, 6, 8);
                break;
            case "B'":
                return executeMove(executeMove(executeMove(state, "B"), "B"), "B");
            case "B2":
                return executeMove(executeMove(state, "B"), "B");
        }

        return returnState.toString();
    }

    private static char flipEdge(char edge) {
        String abc = "abcdefghijklmnopqrstuvwx".repeat(2);
        return abc.charAt(abc.indexOf(edge) + 12);
    }

    private static char rotateCorner(char corner, boolean clockwise) {
        String abc = "abcdefghijklmnopqrstuvwx".repeat(2);
        return clockwise ? abc.charAt(abc.indexOf(corner) + 8) : abc.charAt(abc.indexOf(corner) + 16);
    }

    public static String algToRotationlessAlg(String alg) {
        alg = alg.trim();
        String[] moves = alg.split(" ");
        HashMap<String, String> lowerCaseMovesToXYRotation = new HashMap<>();
        lowerCaseMovesToXYRotation.put("r", "x L");
        lowerCaseMovesToXYRotation.put("r'", "x' L'");
        lowerCaseMovesToXYRotation.put("r2", "x2 L2");
        lowerCaseMovesToXYRotation.put("l", "x' R");
        lowerCaseMovesToXYRotation.put("l'", "x R'");
        lowerCaseMovesToXYRotation.put("l2", "x2 R2");
        lowerCaseMovesToXYRotation.put("u", "y D");
        lowerCaseMovesToXYRotation.put("u'", "y' D'");
        lowerCaseMovesToXYRotation.put("u2", "y2 D2");
        lowerCaseMovesToXYRotation.put("f", "z B");
        lowerCaseMovesToXYRotation.put("f'", "z' B'");
        lowerCaseMovesToXYRotation.put("f2", "z2 B2");
        lowerCaseMovesToXYRotation.put("d", "y' U");
        lowerCaseMovesToXYRotation.put("d'", "y U'");
        lowerCaseMovesToXYRotation.put("d2", "y2 U2");
        lowerCaseMovesToXYRotation.put("b", "z' F");
        lowerCaseMovesToXYRotation.put("b'", "z F'");
        lowerCaseMovesToXYRotation.put("b2", "z2 F2");
        lowerCaseMovesToXYRotation.put("M", "x' R L'");
        lowerCaseMovesToXYRotation.put("M'", "x R' L");
        lowerCaseMovesToXYRotation.put("M2", "x2 R2 L2");
        lowerCaseMovesToXYRotation.put("E", "y' U D'");
        lowerCaseMovesToXYRotation.put("E'", "y U' D");
        lowerCaseMovesToXYRotation.put("E2", "y2 U2 D2");
        lowerCaseMovesToXYRotation.put("S", "z F' B");
        lowerCaseMovesToXYRotation.put("S'", "z' F B'");
        lowerCaseMovesToXYRotation.put("S2", "z2 B2 F2");
        StringBuilder lowerCaseMovesToXYRotationAlg = new StringBuilder();

        for (String move : moves) {
            lowerCaseMovesToXYRotationAlg.append(lowerCaseMovesToXYRotation.getOrDefault(move, move)).append(" ");
        }

        String XYRotationAlg = lowerCaseMovesToXYRotationAlg.toString().trim();
        HashMap<String, HashMap<String, String>> rotationConversion = new HashMap();
        HashMap<String, String> xMoves = new HashMap();
        xMoves.put("R", "R");
        xMoves.put("L", "L");
        xMoves.put("F", "D");
        xMoves.put("D", "B");
        xMoves.put("B", "U");
        xMoves.put("U", "F");
        rotationConversion.put("x", xMoves);
        HashMap<String, String> x2Moves = new HashMap();
        x2Moves.put("R", "R");
        x2Moves.put("L", "L");
        x2Moves.put("F", "B");
        x2Moves.put("B", "F");
        x2Moves.put("U", "D");
        x2Moves.put("D", "U");
        rotationConversion.put("x2", x2Moves);
        HashMap<String, String> xPrMoves = new HashMap();
        xPrMoves.put("R", "R");
        xPrMoves.put("L", "L");
        xPrMoves.put("F", "U");
        xPrMoves.put("U", "B");
        xPrMoves.put("B", "D");
        xPrMoves.put("D", "F");
        rotationConversion.put("x'", xPrMoves);
        HashMap<String, String> yMoves = new HashMap();
        yMoves.put("D", "D");
        yMoves.put("U", "U");
        yMoves.put("F", "R");
        yMoves.put("R", "B");
        yMoves.put("B", "L");
        yMoves.put("L", "F");
        rotationConversion.put("y", yMoves);
        HashMap<String, String> yPrMoves = new HashMap();
        yPrMoves.put("D", "D");
        yPrMoves.put("U", "U");
        yPrMoves.put("F", "L");
        yPrMoves.put("L", "B");
        yPrMoves.put("B", "R");
        yPrMoves.put("R", "F");
        rotationConversion.put("y'", yPrMoves);
        HashMap<String, String> y2Moves = new HashMap();
        y2Moves.put("D", "D");
        y2Moves.put("U", "U");
        y2Moves.put("F", "B");
        y2Moves.put("B", "F");
        y2Moves.put("R", "L");
        y2Moves.put("L", "R");
        rotationConversion.put("y2", y2Moves);
        HashMap<String, String> zMoves = new HashMap();
        zMoves.put("F", "F");
        zMoves.put("B", "B");
        zMoves.put("U", "L");
        zMoves.put("L", "D");
        zMoves.put("D", "R");
        zMoves.put("R", "U");
        rotationConversion.put("z", zMoves);
        HashMap<String, String> zPrMoves = new HashMap();
        zPrMoves.put("F", "F");
        zPrMoves.put("B", "B");
        zPrMoves.put("U", "R");
        zPrMoves.put("R", "D");
        zPrMoves.put("D", "L");
        zPrMoves.put("L", "U");
        rotationConversion.put("z'", zPrMoves);
        HashMap<String, String> z2Moves = new HashMap();
        z2Moves.put("F", "F");
        z2Moves.put("B", "B");
        z2Moves.put("U", "D");
        z2Moves.put("D", "U");
        z2Moves.put("L", "R");
        z2Moves.put("R", "L");
        rotationConversion.put("z2", z2Moves);
        StringBuilder rotationlessAlg = new StringBuilder();
        String[] XYRotationMoves = XYRotationAlg.split(" ");

        for(int i = XYRotationMoves.length - 1; i >= 0; --i) {
            String currentMove = XYRotationMoves[i];
            if (rotationConversion.containsKey(currentMove)) {
                String[] movesInFront = rotationlessAlg.toString().trim().split(" ");
                rotationlessAlg.setLength(0);

                for (String move : movesInFront) {
                    if (!move.isEmpty()) {
                        String absMove = String.valueOf(move.charAt(0));
                        String extraMove = "";
                        if (move.length() == 2) {
                            extraMove = String.valueOf(move.charAt(1));
                        }

                        rotationlessAlg.append((String) ((HashMap) rotationConversion.get(currentMove)).get(absMove)).append(extraMove).append(" ");
                    }
                }
            } else {
                rotationlessAlg.insert(0, currentMove + " ");
            }
        }

        return rotationlessAlg.toString();
    }
}
