package com.company.cube;

import java.util.HashMap;

public final class HashmapLoader {
    private final HashMap<String, String> lowerCaseMovesToXYRotation = new HashMap<>();

    public HashMap<String, String> getLowerCaseMovesToXYRotation() {
        return lowerCaseMovesToXYRotation;
    }

    public HashMap<String, HashMap<String, String>> getRotationConversion() {
        return rotationConversion;
    }

    private final HashMap<String, HashMap<String, String>> rotationConversion = new HashMap<>();
    public HashmapLoader() {
        //load hashmaps
        loadLowerCaseMovesToXYRotation();
        loadRotationConversion();
    }

    private void loadRotationConversion() {
        HashMap<String, String> xMoves = new HashMap<>();
        xMoves.put("R", "R");
        xMoves.put("L", "L");
        xMoves.put("F", "D");
        xMoves.put("D", "B");
        xMoves.put("B", "U");
        xMoves.put("U", "F");
        rotationConversion.put("x", xMoves);
        HashMap<String, String> x2Moves = new HashMap<>();
        x2Moves.put("R", "R");
        x2Moves.put("L", "L");
        x2Moves.put("F", "B");
        x2Moves.put("B", "F");
        x2Moves.put("U", "D");
        x2Moves.put("D", "U");
        rotationConversion.put("x2", x2Moves);
        HashMap<String, String> xPrMoves = new HashMap<>();
        xPrMoves.put("R", "R");
        xPrMoves.put("L", "L");
        xPrMoves.put("F", "U");
        xPrMoves.put("U", "B");
        xPrMoves.put("B", "D");
        xPrMoves.put("D", "F");
        rotationConversion.put("x'", xPrMoves);
        HashMap<String, String> yMoves = new HashMap<>();
        yMoves.put("D", "D");
        yMoves.put("U", "U");
        yMoves.put("F", "R");
        yMoves.put("R", "B");
        yMoves.put("B", "L");
        yMoves.put("L", "F");
        rotationConversion.put("y", yMoves);
        HashMap<String, String> yPrMoves = new HashMap<>();
        yPrMoves.put("D", "D");
        yPrMoves.put("U", "U");
        yPrMoves.put("F", "L");
        yPrMoves.put("L", "B");
        yPrMoves.put("B", "R");
        yPrMoves.put("R", "F");
        rotationConversion.put("y'", yPrMoves);
        HashMap<String, String> y2Moves = new HashMap<>();
        y2Moves.put("D", "D");
        y2Moves.put("U", "U");
        y2Moves.put("F", "B");
        y2Moves.put("B", "F");
        y2Moves.put("R", "L");
        y2Moves.put("L", "R");
        rotationConversion.put("y2", y2Moves);
        HashMap<String, String> zMoves = new HashMap<>();
        zMoves.put("F", "F");
        zMoves.put("B", "B");
        zMoves.put("U", "L");
        zMoves.put("L", "D");
        zMoves.put("D", "R");
        zMoves.put("R", "U");
        rotationConversion.put("z", zMoves);
        HashMap<String, String> zPrMoves = new HashMap<>();
        zPrMoves.put("F", "F");
        zPrMoves.put("B", "B");
        zPrMoves.put("U", "R");
        zPrMoves.put("R", "D");
        zPrMoves.put("D", "L");
        zPrMoves.put("L", "U");
        rotationConversion.put("z'", zPrMoves);
        HashMap<String, String> z2Moves = new HashMap<>();
        z2Moves.put("F", "F");
        z2Moves.put("B", "B");
        z2Moves.put("U", "D");
        z2Moves.put("D", "U");
        z2Moves.put("L", "R");
        z2Moves.put("R", "L");
        rotationConversion.put("z2", z2Moves);
    }

    private void loadLowerCaseMovesToXYRotation() {
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
    }
}
