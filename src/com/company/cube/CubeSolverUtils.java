package com.company.cube;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public final class CubeSolverUtils {
    public static final String RES_3_X_2_X_1_TABLE_PATH = "res/3x2x1Table";
    private static final String EDGE_ORIENTATION_TABLE_TXT_PATH = "res/edgeOrientationTable.txt";
    public static final String _2ND_3X2X1_TABLE_TXT_PATH = "res/2nd3x2x1Table.txt";
    public static final String ZBLL_ALGS_TXT_PATH = "res/ZBLLAlgs.txt";

    public static String solve3x2x1FromEO(String state) {
        String ubEdgeAlg = switch (state.substring(0, 12).indexOf(97)) {
            case 1 -> "U'";
            case 2 -> "U2";
            case 3 -> "U";
            case 4 -> "R U'";
            case 5 -> "L' U";
            case 6 -> "R' U'";
            case 7 -> "L U";
            case 8 -> "B2";
            case 9 -> "R2 U'";
            case 10 -> "D2 B2";
            case 11 -> "L2 U";
            default -> "";
        };

        String x3x2x1Orientations = stateTo3x2x1Positions(CubeAlgUtils.executeAlg(state, ubEdgeAlg));
        FileInputStream fis = getFIS(RES_3_X_2_X_1_TABLE_PATH + "/" + x3x2x1Orientations.substring(8, 12) + ".txt");
        Scanner scanner = new Scanner(fis);

        String orientationCase;
        do {
            if (!scanner.hasNextLine()) {
                return ubEdgeAlg;
            }

            orientationCase = scanner.nextLine();
        } while(!orientationCase.substring(0, 8).equals(x3x2x1Orientations.substring(0, 8)));

        scanner.close();
        closeFIS(fis);
        String alg = orientationCase.substring(9);
        return ubEdgeAlg + " " + alg;
    }

    public static String solveEO(String state) {
        FileInputStream fis = getFIS(EDGE_ORIENTATION_TABLE_TXT_PATH);
        Scanner scanner = new Scanner(fis);
        String edgeOrientations = stateToEdgeOrientations(state);

        String orientationCase;
        do {
            orientationCase = scanner.nextLine();
        } while(!orientationCase.substring(0, 12).equals(edgeOrientations));

        scanner.close();
        closeFIS(fis);
        return orientationCase.substring(13);
    }

    public static String solveF2LFrom3x2x1(String state) {
        StringBuilder alg = new StringBuilder();
        FileInputStream fis = getFIS(_2ND_3X2X1_TABLE_TXT_PATH);
        Scanner scanner = new Scanner(fis);
        String edgeOrientations = stateTo2nd3x2x1Positions(state);

        String orientationCase;
        do {
            if (!scanner.hasNextLine()) {
                return "";
            }

            orientationCase = scanner.nextLine();
        } while(!orientationCase.substring(0, 10).equals(edgeOrientations));

        scanner.close();
        closeFIS(fis);
        alg.append(orientationCase.substring(11));
        return alg.toString();
    }

    static String stateTo2nd3x2x1Positions(String state) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", state.indexOf(100)));
        sb.append(String.format("%02d", state.indexOf(104)));
        sb.append(String.format("%02d", state.indexOf(102)));
        String cornerState = state.substring(12, 20);
        if (cornerState.indexOf(97) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(97)));
        } else if (cornerState.indexOf(105) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(105) + 8));
        } else {
            sb.append(String.format("%02d", cornerState.indexOf(113) + 16));
        }

        if (cornerState.indexOf(100) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(100)));
        } else if (cornerState.indexOf(108) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(108) + 8));
        } else {
            sb.append(String.format("%02d", cornerState.indexOf(116) + 16));
        }

        return sb.toString();
    }

    public static String solveZBLLFromF2L(String state) {
        FileInputStream fis = getFIS(ZBLL_ALGS_TXT_PATH);
        Scanner scanner = new Scanner(fis);
        String var1 = CubeAlgUtils.executeMove(state, "D");
        String var2 = CubeAlgUtils.executeMove(state, "D'");
        String var3 = CubeAlgUtils.executeMove(state, "D2");

        String orientationCase;
        String stateCase;
        do {
            if (!scanner.hasNextLine()) {
                return "";
            }

            orientationCase = scanner.nextLine();
            stateCase = orientationCase.substring(0, 20);
            if (stateCase.equals(state)) {
                scanner.close();
                closeFIS(fis);
                return orientationCase.substring(21);
            }

            if (stateCase.equals(var1)) {
                scanner.close();
                closeFIS(fis);
                return CubeAlgUtils.algToRotationlessAlg("y' " + orientationCase.substring(21));
            }

            if (stateCase.equals(var2)) {
                scanner.close();
                closeFIS(fis);
                return CubeAlgUtils.algToRotationlessAlg("y " + orientationCase.substring(21));
            }
        } while(!stateCase.equals(var3));

        scanner.close();
        closeFIS(fis);
        return CubeAlgUtils.algToRotationlessAlg("y2 " + orientationCase.substring(21));
    }

    public static String stateTo3x2x1Positions(String state) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", state.indexOf(98)));
        sb.append(String.format("%02d", state.indexOf(99)));
        sb.append(String.format("%02d", state.indexOf(101)));
        sb.append(String.format("%02d", state.indexOf(103)));
        String cornerState = state.substring(12, 20);
        if (cornerState.indexOf(99) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(99)));
        } else if (cornerState.indexOf(107) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(107) + 8));
        } else {
            sb.append(String.format("%02d", cornerState.indexOf(115) + 16));
        }

        if (cornerState.indexOf(98) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(98)));
        } else if (cornerState.indexOf(106) != -1) {
            sb.append(String.format("%02d", cornerState.indexOf(106) + 8));
        } else {
            sb.append(String.format("%02d", cornerState.indexOf(114) + 16));
        }

        return sb.toString();
    }

    private static String stateToEdgeOrientations(String state) {
        StringBuilder sb = new StringBuilder();
        String edgeState = state.substring(0, 12);

        for(int i = 0; i < 12; ++i) {
            char c = edgeState.charAt(i);
            if (c != 'a' && c != 'b' && c != 'c' && c != 'd' && c != 'e' && c != 'f' && c != 'g' && c != 'h' && c != 'i' && c != 'j' && c != 'k' && c != 'l') {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }

        return sb.toString();
    }

    private static FileInputStream getFIS(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException var2) {
            throw new RuntimeException(var2);
        }
    }

    private static void closeFIS(FileInputStream fis) {
        try {
            fis.close();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
}
