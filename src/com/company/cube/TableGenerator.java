//decompiled because original was lost
package com.company.cube;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TableGenerator {
    private void solveZBLLCases() throws IOException {
        FileInputStream casesFile = new FileInputStream("res/ZBLL/ZBLLCases.txt");
        FileInputStream algsFile = new FileInputStream("res/ZBLL/ZBLLAlgs.txt");
        Scanner caseScanner = new Scanner(casesFile);
        Scanner algsScanner = new Scanner(algsFile);
        FileWriter ZBLLsolutions = new FileWriter("res/ZBLLTable.txt");
        ArrayList<String> algs = new ArrayList();

        while(algsScanner.hasNextLine()) {
            algs.add(algsScanner.nextLine());
        }

        int count = 0;

        while(true) {
            while(caseScanner.hasNextLine()) {
                String state = this.ZBLLCaseToState(caseScanner.nextLine());
                String variation2 = CubeAlgUtils.executeMove(state, "D");
                String variation3 = CubeAlgUtils.executeMove(state, "D2");
                String variation4 = CubeAlgUtils.executeMove(state, "D'");
                ++count;
                System.out.println(count);
                Iterator var12 = algs.iterator();

                while(var12.hasNext()) {
                    String alg = (String)var12.next();
                    if (this.isAUF(CubeAlgUtils.executeAlg(state, "x2 " + alg))) {
                        ZBLLsolutions.write(state + ":" + CubeAlgUtils.algToRotationlessAlg("x2 " + alg) + "\n");
                        break;
                    }

                    if (this.isAUF(CubeAlgUtils.executeAlg(variation2, "x2 " + alg))) {
                        ZBLLsolutions.write(variation2 + ":" + CubeAlgUtils.algToRotationlessAlg("x2 " + alg) + "\n");
                        break;
                    }

                    if (this.isAUF(CubeAlgUtils.executeAlg(variation3, "x2 " + alg))) {
                        ZBLLsolutions.write(variation3 + ":" + CubeAlgUtils.algToRotationlessAlg("x2 " + alg) + "\n");
                        break;
                    }

                    if (this.isAUF(CubeAlgUtils.executeAlg(variation4, "x2 " + alg))) {
                        ZBLLsolutions.write(variation4 + ":" + CubeAlgUtils.algToRotationlessAlg("x2 " + alg) + "\n");
                        break;
                    }
                }
            }

            ZBLLsolutions.close();
            return;
        }
    }

    private boolean isAUF(String state) {
        return state.equals("abcdefghijklabcdefgh") || state.equals("abcdefghjkliabcdfheg") || state.equals("abcdefghlijkabcdgehf") || state.equals("abcdefghklijabcdhgfe");
    }

    private String ZBLLCaseToState(String ZBLLCase) {
        StringBuilder sb = new StringBuilder();
        sb.append("abcdefgh");
        sb.append(ZBLLCase, 0, 4);
        sb.append("abcd");
        sb.append(ZBLLCase.substring(4));
        return sb.toString();
    }

    private void createZBLLCases() throws IOException {
        FileWriter writer = new FileWriter("res/ZBLL/ZBLLCases.txt");
        FileInputStream fis = new FileInputStream("res/ZBLL/ZBLLTable.txt");
        Scanner scanner = new Scanner(fis);

        while(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String var10001 = nextLine.substring(8, 12);
            writer.write(var10001 + nextLine.substring(16, 20) + "\n");
        }

        writer.close();
    }

    private void formatZBLLTable() throws IOException {
        FileInputStream fis = new FileInputStream("res/ZBLL/zbll_scrambles.txt");
        Scanner scanner = new Scanner(fis);
        FileWriter writer = new FileWriter("res/ZBLL/ZBLLAlgs.txt");

        while(scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String var10001 = nextLine.substring(nextLine.indexOf(116) + 1);
            writer.write(var10001 + "\n");
        }

        writer.close();
    }

    private void create2nd3x2x1Table() throws IOException {
        ArrayList<String> allMoves = new ArrayList();
        allMoves.add("D");
        allMoves.add("D2");
        allMoves.add("D'");
        allMoves.add("L");
        allMoves.add("L2");
        allMoves.add("L'");
        FileWriter writer = new FileWriter("res/3x2x1.txt");
        HashSet<String> solvedCases = new HashSet();
        PriorityQueue<String> queue = new PriorityQueue();
        queue.add("00abcdefghijklabcdefgh");
        int count = 1;

        while(!queue.isEmpty()) {
            String current = (String)queue.poll();
            String currentMoves = "";

            try {
                currentMoves = current.substring(22);
            } catch (Exception var16) {
            }

            String currentState = current.substring(2, 22);
            String current3x2x1Positions = CubeSolverUtils.stateTo2nd3x2x1Positions(currentState);
            if (!solvedCases.contains(current3x2x1Positions)) {
                ++count;
                solvedCases.add(current3x2x1Positions);
                System.out.printf("%09d%n", count);

                try {
                    writer.write(current3x2x1Positions + ":" + this.reverseAlg(currentMoves.substring(1)) + "\n");
                } catch (Exception var15) {
                }

                Iterator var10 = allMoves.iterator();

                while(var10.hasNext()) {
                    String move = (String)var10.next();
                    String nextState = CubeAlgUtils.executeMove(currentState, move);
                    String nextCrossPositions = CubeSolverUtils.stateTo2nd3x2x1Positions(nextState);
                    if (!solvedCases.contains(nextCrossPositions)) {
                        String queueAdd = String.format("%02d", Integer.parseInt(current.substring(0, 2)) + 1) + nextState + currentMoves + " " + move;
                        queue.add(queueAdd);
                    }
                }
            }
        }

        writer.close();
    }

    private void format3x2x1Table() throws IOException {
        HashSet<String> formattedCases = new HashSet();
        int count = 0;

        while(formattedCases.size() != 504) {
            FileInputStream fis = new FileInputStream("res/3x2x1Table/3x2x1Table.txt");
            Scanner scanner = new Scanner(fis);
            String newCase = null;
            String caseNum = "";

            while(scanner.hasNextLine()) {
                newCase = scanner.nextLine();
                caseNum = newCase.substring(8, 12);
                if (!formattedCases.contains(caseNum)) {
                    break;
                }
            }

            ++count;
            System.out.println(504 - count + " left");
            formattedCases.add(caseNum);
            FileWriter writer = new FileWriter("res/3x2x1Table/" + caseNum + ".txt");

            assert newCase != null;

            String var10001 = newCase.substring(0, 8);
            writer.write(var10001 + newCase.substring(12) + "\n");

            while(scanner.hasNextLine()) {
                String nextCase = scanner.nextLine();
                if (caseNum.equals(nextCase.substring(8, 12))) {
                    var10001 = nextCase.substring(0, 8);
                    writer.write(var10001 + nextCase.substring(12) + "\n");
                }
            }

            writer.close();
        }

    }

    private void create3x2x1Table() throws IOException {
        ArrayList<String> allMoves = new ArrayList();
        allMoves.add("R");
        allMoves.add("R2");
        allMoves.add("R'");
        allMoves.add("D");
        allMoves.add("D2");
        allMoves.add("D'");
        allMoves.add("L");
        allMoves.add("L2");
        allMoves.add("L'");
        allMoves.add("F2");
        FileWriter writer = new FileWriter("res/3x2x1Table/3x2x1Table.txt");
        HashSet<String> solvedCases = new HashSet();
        PriorityQueue<String> queue = new PriorityQueue();
        queue.add("00abcdefghijklabcdefgh");
        int count = 1;

        while(!queue.isEmpty()) {
            String current = (String)queue.poll();
            String currentMoves = "";

            try {
                currentMoves = current.substring(22);
            } catch (Exception var16) {
            }

            String currentState = current.substring(2, 22);
            String current3x2x1Positions = CubeSolverUtils.stateTo3x2x1Positions(currentState);
            if (!solvedCases.contains(current3x2x1Positions)) {
                ++count;
                solvedCases.add(current3x2x1Positions);
                System.out.printf("%09d%n", count);

                try {
                    writer.write(current3x2x1Positions + ":" + this.reverseAlg(currentMoves.substring(1)) + "\n");
                } catch (Exception var15) {
                }

                Iterator var10 = allMoves.iterator();

                while(var10.hasNext()) {
                    String move = (String)var10.next();
                    String nextState = CubeAlgUtils.executeMove(currentState, move);
                    String nextCrossPositions = CubeSolverUtils.stateTo3x2x1Positions(nextState);
                    if (!solvedCases.contains(nextCrossPositions)) {
                        String queueAdd = String.format("%02d", Integer.parseInt(current.substring(0, 2)) + 1) + nextState + currentMoves + " " + move;
                        queue.add(queueAdd);
                    }
                }
            }
        }

        writer.close();
    }

    private String reverseAlg(String alg) {
        StringBuilder sb = new StringBuilder();
        String[] moves = alg.split(" ");

        for(int i = moves.length - 1; i != -1; --i) {
            sb.append(this.reverseMove(moves[i])).append(" ");
        }

        return sb.toString();
    }

    private String reverseMove(String move) {
        if (move.length() == 1) {
            return move + "'";
        } else {
            return move.substring(1).equals("'") ? move.substring(0, 1) : move;
        }
    }
}
