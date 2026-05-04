package kyc.comparateurs;

public class Levenshtein implements ComparateurChaine {

    public double comparerChaine(String l1, String l2) {

        int distance = distanceLevenshtein(l1, l2);
        int maxLength = Math.max(l1.length(), l2.length());

        if (maxLength == 0) {
            return 1.0;
        }

        return 1.0 - ((double) distance / maxLength);
    }

    private int distanceLevenshtein( String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
                dp[i][0] = i;
        }

        for (int j = 0; j <= s2.length(); j++) {
                dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {

                int cost;
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        cost = 0;
                } else {
                        cost = 1;
                }

                int delete = dp[i - 1][j] + 1;
                int insert = dp[i][j - 1] + 1;
                int replace = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(delete, Math.min(insert, replace));
            }
        }

            return dp[s1.length()][s2.length()];
        }


}
