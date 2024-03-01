package com.company.cube;

import java.util.HashMap;


public final class CubeAlgUtils {
    public static final HashmapLoader hashmapLoader = new HashmapLoader();
    public static String formatAlg(String alg) {
        System.out.println(alg);
        alg = alg.trim().replaceAll("\\s+"," ");
        String[] moves = alg.split(" ");

        int previousCount;
        String formatted;
        do {
            previousCount = moves.length;
            formatted = format(moves);
            moves = formatted.split(" ");

        } while (previousCount != moves.length);
        return formatted;
    }

    private static String format(String[] moves) {
        StringBuilder formattedAlg = new StringBuilder();
        boolean streak = false;
        int orientationCounter = 0;
        for (int i = 0; i < moves.length; i++) {
            String currentMove = moves[i];
            char absCurrentMove = currentMove.charAt(0);
            String nextMove;
            char absNextMove = 0;
            if (i != moves.length - 1){
                nextMove = moves[i + 1];
                absNextMove = nextMove.charAt(0);
            }
            //no streak
            if (absCurrentMove != absNextMove && !streak){
                formattedAlg.append(currentMove).append(" ");
                continue;
            }
            if (currentMove.length() == 1){
                orientationCounter++;
            }
            else if (currentMove.charAt(1) == '2'){
                orientationCounter += 2;
            }
            else {
                orientationCounter += 3;
            }
            //start or middle of streak
            if (!streak){
                streak = true;

            }
            //end of streak
            else {
                int mod = orientationCounter % 4;

                switch (mod) {
                    case 0 -> {
                        //do nothing
                    }
                    case 1 -> {
                        formattedAlg.append(absCurrentMove).append(" ");
                    }
                    case 2 -> {
                        formattedAlg.append(absCurrentMove).append("2").append(" ");
                    }
                    case 3 -> {
                        formattedAlg.append(absCurrentMove).append("'").append(" ");
                    }
                }
                orientationCounter = 0;
                streak = false;
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
    public static char orientCorner(char corner, int orientation){
        switch (orientation){
            case 0 -> {
                if ("abcdefgh".indexOf(corner) != -1){
                    return corner;
                } else if ("ijklmnop".indexOf(corner) != -1) {
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) - 8);
                }
                else {
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) - 16);
                }
            }
            case 1 -> {
                if ("abcdefgh".indexOf(corner) != -1){
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) + 8);
                } else if ("ijklmnop".indexOf(corner) != -1) {
                    return corner;
                }
                else {
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) - 8);
                }
            }
            case 2 -> {
                if ("abcdefgh".indexOf(corner) != -1){
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) + 16);
                }
                else if ("ijklmnop".indexOf(corner) != -1) {
                    return "abcdefghijklmnopqrstuvwx".charAt("abcdefghijklmnopqrstuvwx".indexOf(corner) + 8);
                }
                else {
                    return corner;
                }
            }
        }
        return 0;
    }
    public static int cornerToOrientation(char corner){
        if ("abcdefgh".indexOf(corner) != -1){
            return 0;
        } else if ("ijklmnop".indexOf(corner) != -1) {
            return 1;
        }
        return 2;
    }

    public static char flipEdge(char edge) {
        String abc = "abcdefghijklmnopqrstuvwx".repeat(2);
        return abc.charAt(abc.indexOf(edge) + 12);
    }

    public static char rotateCorner(char corner, boolean clockwise) {
        String abc = "abcdefghijklmnopqrstuvwx".repeat(2);
        return clockwise ? abc.charAt(abc.indexOf(corner) + 8) : abc.charAt(abc.indexOf(corner) + 16);
    }

    public static String algToRotationlessAlg(String alg) {
        alg = alg.trim();
        String[] moves = alg.split(" ");

        StringBuilder lowerCaseMovesToXYRotationAlg = new StringBuilder();

        for (String move : moves) {
            lowerCaseMovesToXYRotationAlg.append(hashmapLoader.getLowerCaseMovesToXYRotation().getOrDefault(move, move)).append(" ");
        }

        String XYRotationAlg = lowerCaseMovesToXYRotationAlg.toString().trim();

        StringBuilder rotationlessAlg = new StringBuilder();
        String[] XYRotationMoves = XYRotationAlg.split(" ");

        for(int i = XYRotationMoves.length - 1; i >= 0; --i) {
            String currentMove = XYRotationMoves[i];
            if (hashmapLoader.getRotationConversion().containsKey(currentMove)) {
                String[] movesInFront = rotationlessAlg.toString().trim().split(" ");
                rotationlessAlg.setLength(0);

                for (String move : movesInFront) {
                    if (!move.isEmpty()) {
                        String absMove = String.valueOf(move.charAt(0));
                        String extraMove = "";
                        if (move.length() == 2) {
                            extraMove = String.valueOf(move.charAt(1));
                        }

                        rotationlessAlg.append((String) ((HashMap<?, ?>) hashmapLoader.getRotationConversion().get(currentMove)).get(absMove)).append(extraMove).append(" ");
                    }
                }
            } else {
                rotationlessAlg.insert(0, currentMove + " ");
            }
        }

        return rotationlessAlg.toString();
    }
}
