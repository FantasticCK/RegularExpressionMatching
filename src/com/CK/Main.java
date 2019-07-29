package com.CK;

public class Main {

    public static void main(String[] args) {
        String s = "";
        String p = "c*c*";
        Solution solution = new Solution();
        System.out.println(solution.isMatch(s, p));
    }
}

class Solution {
    public boolean isMatch(String s, String p) {
        p = removeStar(p);
        boolean[][] dp = new boolean[s.length()][p.length()];
        boolean[][] visited = new boolean[s.length()][p.length()];
        return helper(s, 0, p, 0, dp, visited);
    }

    private boolean helper(String s, int sItr, String p, int pItr, boolean[][] dp, boolean[][] visited) {
        if (pItr == p.length()) return sItr == s.length();

        if (sItr >= s.length()) {
            while (pItr < p.length() - 1 && p.charAt(pItr) != '*' && pItr != p.length() - 1 && p.charAt(pItr + 1) == '*')
                pItr += 2;
            if (pItr >= p.length()) return true;
            if (pItr == p.length() - 1 && p.charAt(pItr) == '*') return true;
            else return false;
        }

        if (visited[sItr][pItr]) return dp[sItr][pItr];

        if (p.charAt(pItr) == '*') return false;

        if (p.charAt(pItr) != '*' && pItr != p.length() - 1 && p.charAt(pItr + 1) == '*') {
            dp[sItr][pItr] = helper(s, sItr, p, pItr + 2, dp, visited)
                    || (isMatchSingleChar(s, sItr, p, pItr) && helper(s, sItr + 1, p, pItr, dp, visited));
        } else {
            dp[sItr][pItr] = isMatchSingleChar(s, sItr, p, pItr)
                    && helper(s, sItr + 1, p, pItr + 1, dp, visited);
        }

        visited[sItr][pItr] = true;
        return dp[sItr][pItr];
    }

    private boolean isMatchSingleChar(String s, int sItr, String p, int pItr) {
        return p.charAt(pItr) == '.'
                || p.charAt(pItr) == s.charAt(sItr);
    }

    private String removeStar(String p) {
        StringBuilder pSB = new StringBuilder(p);
        for (int i = 0; i < pSB.length(); i++) {
            while (pSB.charAt(i) == '*' && i != pSB.length() - 1 && pSB.charAt(i + 1) == '*') {
                pSB.deleteCharAt(i);
            }
        }
        return pSB.toString();
    }
}