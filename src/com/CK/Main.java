package com.CK;

public class Main {

    public static void main(String[] args) {
        String s = "";
        String p = "c*c*";
        Solution2 solution = new Solution2();
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

//DP Bottom-up
class Solution2 {
    public boolean isMatch(String s, String p) {

        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == s.charAt(i)) {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else {
                        dp[i + 1][j + 1] = (dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j - 1]);
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}

//DP Top-bottom
class Solution3 {
    Boolean[][] memo;

    public boolean isMatch(String text, String pattern) {
        memo = new Boolean[text.length() + 1][pattern.length() + 1];
        return dp(0, 0, text, pattern);
    }

    public boolean dp(int i, int j, String text, String pattern) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        boolean ans;
        if (j == pattern.length()) {
            ans = i == text.length();
        } else {
            boolean first_match = (i < text.length() &&
                    (pattern.charAt(j) == text.charAt(i) ||
                            pattern.charAt(j) == '.'));

            if (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') {
                ans = (dp(i, j + 2, text, pattern) ||
                        first_match && dp(i + 1, j, text, pattern));
            } else {
                ans = first_match && dp(i + 1, j + 1, text, pattern);
            }
        }
        memo[i][j] = ans;
        return ans;
    }
}