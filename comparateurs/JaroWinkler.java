package kyc.comparateurs;

public class JaroWinkler implements ComparateurChaine {
    public double comparerChaine(String l1, String l2) {
        return jaroWinkler(l1, l2);
    }

    private double jaroWinkler(String s1, String s2) {
        double jaro = jaroSimilarity(s1, s2);

        int prefixLength = commonPrefixLength(s1, s2);

        int maxPrefix = Math.min(4, prefixLength);

        double scalingFactor = 0.1;

        return jaro + (maxPrefix * scalingFactor * (1 - jaro));
    }

    private double jaroSimilarity(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1.0;
        }

        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0 || len2 == 0) {
            return 0.0;
        }

        int matchDistance = Math.max(len1, len2) / 2 - 1;

        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];

        int matches = 0;

        for (int i = 0; i < len1; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, len2);

            for (int j = start; j < end; j++) {
                if (s2Matches[j]) {
                    continue;
                }

                if (s1.charAt(i) != s2.charAt(j)) {
                    continue;
                }

                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) {
            return 0.0;
        }

        int k = 0;
        int transpositions = 0;

        // calculating transpositions

        for (int i = 0; i < len1; i++) {
            if (!s1Matches[i]) {
                continue;
            }

            while (!s2Matches[k]) {
                k++;
            }

            if (s1.charAt(i) != s2.charAt(k)) {
                transpositions++;
            }
            k++;
        }

        double m = matches;
        double t = transpositions / 2.0;

        return ((m / len1) + (m / len2) + ((m - t) / m)) / 3.0;
    }

    private int commonPrefixLength(String s1, String s2) {
        int max = Math.min(4, Math.min(s1.length(), s2.length()));

        int count = 0;

        for (int i = 0; i < max; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }
}
